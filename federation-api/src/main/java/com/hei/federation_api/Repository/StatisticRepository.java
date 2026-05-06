package com.hei.federation_api.Repository;

import com.hei.federation_api.Config.DataSource;
import com.hei.federation_api.Entity.*;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StatisticRepository {

    private final DataSource dataSource;

    public StatisticRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // v0.0.5 : GET /collectivites/{id}/statistics
    // Tout le calcul est fait en SQL (push down processing)
    public List<CollectivityLocalStatistics> getMemberStatistics(String collectivityId, String from, String to) {
        List<CollectivityLocalStatistics> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT
                    m.id,
                    m.first_name,
                    m.last_name,
                    m.email,
                    m.occupation,
                    COALESCE(SUM(mp.amount), 0) AS earned_amount,
                    GREATEST(
                        (
                            SELECT COALESCE(SUM(mf.amount), 0)
                            FROM membership_fees mf
                            WHERE mf.collectivity_id = ?
                            AND mf.status = 'ACTIVE'
                            AND mf.eligible_from BETWEEN ?::date AND ?::date
                        ) - COALESCE(SUM(mp.amount), 0),
                        0
                    ) AS unpaid_amount
                FROM members m
                LEFT JOIN member_payments mp
                    ON mp.member_id = m.id
                    AND mp.creation_date BETWEEN ?::date AND ?::date
                WHERE m.collectivity_id = ?
                GROUP BY m.id, m.first_name, m.last_name, m.email, m.occupation
            """);
            ps.setString(1, collectivityId);
            ps.setString(2, from);
            ps.setString(3, to);
            ps.setString(4, from);
            ps.setString(5, to);
            ps.setString(6, collectivityId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MemberDescription desc = new MemberDescription();
                desc.id = rs.getString("id");
                desc.firstName = rs.getString("first_name");
                desc.lastName = rs.getString("last_name");
                desc.email = rs.getString("email");
                desc.occupation = rs.getString("occupation");

                CollectivityLocalStatistics stat = new CollectivityLocalStatistics();
                stat.memberDescription = desc;
                stat.earnedAmount = rs.getDouble("earned_amount");
                stat.unpaidAmount = rs.getDouble("unpaid_amount");

                list.add(stat);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // v0.0.5 : GET /collectivites/statistics
    // Tout le calcul est fait en SQL (push down processing)
    public List<CollectivityOverallStatistics> getOverallStatistics(String from, String to) {
        List<CollectivityOverallStatistics> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT
                    c.id AS collectivity_id,
                    c.name,
                    c.number,
                    COUNT(DISTINCT m.id) AS total_members,
                    COUNT(DISTINCT CASE
                        WHEN m.membership_date BETWEEN ?::date AND ?::date
                        THEN m.id
                    END) AS new_members_number,
                    CASE
                        WHEN COUNT(DISTINCT m.id) = 0 THEN 0
                        ELSE ROUND(
                            100.0 * COUNT(DISTINCT CASE
                                WHEN (
                                    SELECT COALESCE(SUM(mp.amount), 0)
                                    FROM member_payments mp
                                    WHERE mp.member_id = m.id
                                    AND mp.creation_date BETWEEN ?::date AND ?::date
                                ) >= (
                                    SELECT COALESCE(SUM(mf.amount), 0)
                                    FROM membership_fees mf
                                    WHERE mf.collectivity_id = c.id
                                    AND mf.status = 'ACTIVE'
                                    AND mf.eligible_from BETWEEN ?::date AND ?::date
                                )
                                THEN m.id
                            END) / NULLIF(COUNT(DISTINCT m.id), 0),
                        2)
                    END AS up_to_date_percentage
                FROM collectivities c
                LEFT JOIN members m ON m.collectivity_id = c.id
                GROUP BY c.id, c.name, c.number
            """);
            ps.setString(1, from);
            ps.setString(2, to);
            ps.setString(3, from);
            ps.setString(4, to);
            ps.setString(5, from);
            ps.setString(6, to);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CollectivityInformation info = new CollectivityInformation();
                info.name = rs.getString("name");
                info.number = rs.getString("number");

                CollectivityOverallStatistics stat = new CollectivityOverallStatistics();
                stat.collectivityId = rs.getString("collectivity_id");
                stat.collectivityInformation = info;
                stat.newMembersNumber = rs.getInt("new_members_number");
                stat.overallMemberCurrentDuePercentage = rs.getDouble("up_to_date_percentage");

                list.add(stat);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}