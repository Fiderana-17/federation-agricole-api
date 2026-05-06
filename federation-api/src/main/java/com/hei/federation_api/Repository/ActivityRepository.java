package com.hei.federation_api.Repository;

import com.hei.federation_api.Config.DataSource;
import com.hei.federation_api.Entity.*;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class ActivityRepository {

    private final DataSource dataSource;

    public ActivityRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public CollectivityActivity insert(CollectivityActivity activity) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                INSERT INTO activities(id, label, activity_type, member_occupation_concerned,
                                      recurrence_week_ordinal, recurrence_day_of_week,
                                      executive_date, collectivity_id)
                VALUES (?, ?, ?, ?, ?, ?, ?::date, ?)
            """);
            ps.setString(1, activity.id);
            ps.setString(2, activity.label);
            ps.setString(3, activity.activityType);
            ps.setString(4, activity.memberOccupationConcerned != null
                    ? String.join(",", activity.memberOccupationConcerned) : null);
            if (activity.recurrenceRule != null) {
                ps.setInt(5, activity.recurrenceRule.weekOrdinal);
                ps.setString(6, activity.recurrenceRule.dayOfWeek);
            } else {
                ps.setNull(5, Types.INTEGER);
                ps.setNull(6, Types.VARCHAR);
            }
            ps.setString(7, activity.executiveDate);
            ps.setString(8, activity.collectivityId);
            ps.executeUpdate();
            return activity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<CollectivityActivity> findByCollectivityId(String collectivityId) {
        List<CollectivityActivity> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT id, label, activity_type, member_occupation_concerned,
                       recurrence_week_ordinal, recurrence_day_of_week,
                       executive_date, collectivity_id
                FROM activities
                WHERE collectivity_id = ?
                ORDER BY executive_date
            """);
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapActivity(rs));
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsById(String id) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) FROM activities WHERE id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getAttendanceStatus(String activityId, String memberId) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT attendance_status FROM attendance
                WHERE activity_id = ? AND member_id = ?
            """);
            ps.setString(1, activityId);
            ps.setString(2, memberId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("attendance_status");
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ActivityMemberAttendance insertAttendance(String activityId, String memberId, String status) {
        String id = UUID.randomUUID().toString();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                INSERT INTO attendance(id, activity_id, member_id, attendance_status)
                VALUES (?, ?, ?, ?)
            """);
            ps.setString(1, id);
            ps.setString(2, activityId);
            ps.setString(3, memberId);
            ps.setString(4, status);
            ps.executeUpdate();

            ActivityMemberAttendance att = new ActivityMemberAttendance();
            att.id = id;
            att.attendanceStatus = status;
            return att;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<ActivityMemberAttendance> findAttendanceByActivityId(String activityId) {
        List<ActivityMemberAttendance> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT
                    att.id,
                    att.attendance_status,
                    m.id AS member_id,
                    m.first_name,
                    m.last_name,
                    m.email,
                    m.occupation
                FROM attendance att
                JOIN members m ON m.id = att.member_id
                WHERE att.activity_id = ?
            """);
            ps.setString(1, activityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MemberDescription desc = new MemberDescription();
                desc.id = rs.getString("member_id");
                desc.firstName = rs.getString("first_name");
                desc.lastName = rs.getString("last_name");
                desc.email = rs.getString("email");
                desc.occupation = rs.getString("occupation");

                ActivityMemberAttendance att = new ActivityMemberAttendance();
                att.id = rs.getString("id");
                att.memberDescription = desc;
                att.attendanceStatus = rs.getString("attendance_status");
                list.add(att);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Bonus 2 : taux d'assiduité d'un membre — calcul 100% en SQL
    public Double getAttendanceRate(String memberId, String collectivityId, String from, String to) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT
                    CASE
                        WHEN COUNT(DISTINCT a.id) = 0 THEN 0
                        ELSE ROUND(
                            100.0 * COUNT(DISTINCT CASE
                                WHEN att.attendance_status = 'ATTENDED' THEN a.id
                            END) / NULLIF(COUNT(DISTINCT a.id), 0),
                        2)
                    END AS attendance_rate
                FROM activities a
                LEFT JOIN attendance att
                    ON att.activity_id = a.id
                    AND att.member_id = ?
                WHERE a.collectivity_id = ?
                AND a.executive_date BETWEEN ?::date AND ?::date
            """);
            ps.setString(1, memberId);
            ps.setString(2, collectivityId);
            ps.setString(3, from);
            ps.setString(4, to);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble("attendance_rate");
            return 0.0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Bonus 2 : taux d'assiduité global d'une collectivité — calcul 100% en SQL
    public Double getCollectivityAttendanceRate(String collectivityId, String from, String to) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT
                    CASE
                        WHEN COUNT(DISTINCT a.id) * COUNT(DISTINCT m.id) = 0 THEN 0
                        ELSE ROUND(
                            100.0 * COUNT(DISTINCT CASE
                                WHEN att.attendance_status = 'ATTENDED'
                                THEN att.member_id || '-' || att.activity_id
                            END) / NULLIF(
                                COUNT(DISTINCT a.id) * COUNT(DISTINCT m.id),
                                0
                            ),
                        2)
                    END AS attendance_rate
                FROM activities a
                CROSS JOIN members m
                LEFT JOIN attendance att
                    ON att.activity_id = a.id
                    AND att.member_id = m.id
                WHERE a.collectivity_id = ?
                AND m.collectivity_id = ?
                AND a.executive_date BETWEEN ?::date AND ?::date
            """);
            ps.setString(1, collectivityId);
            ps.setString(2, collectivityId);
            ps.setString(3, from);
            ps.setString(4, to);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble("attendance_rate");
            return 0.0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private CollectivityActivity mapActivity(ResultSet rs) throws SQLException {
        CollectivityActivity a = new CollectivityActivity();
        a.id = rs.getString("id");
        a.label = rs.getString("label");
        a.activityType = rs.getString("activity_type");
        a.collectivityId = rs.getString("collectivity_id");
        a.executiveDate = rs.getString("executive_date");

        String occupations = rs.getString("member_occupation_concerned");
        if (occupations != null && !occupations.isEmpty()) {
            a.memberOccupationConcerned = List.of(occupations.split(","));
        }

        int weekOrdinal = rs.getInt("recurrence_week_ordinal");
        String dayOfWeek = rs.getString("recurrence_day_of_week");
        if (!rs.wasNull() && dayOfWeek != null) {
            MonthlyRecurrenceRule rule = new MonthlyRecurrenceRule();
            rule.weekOrdinal = weekOrdinal;
            rule.dayOfWeek = dayOfWeek;
            a.recurrenceRule = rule;
        }
        return a;
    }
}