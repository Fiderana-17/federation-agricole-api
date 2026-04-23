package com.hei.federation_api.Repository;

import com.hei.federation_api.Config.DataSource;
import com.hei.federation_api.Entity.Collectivity;
import com.hei.federation_api.Entity.Member;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CollectivityRepository {

    private final DataSource dataSource;

    public CollectivityRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(String id, String location, String specialization) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                INSERT INTO collectivities(id, location, specialization)
                VALUES (?, ?, ?)
            """);
            ps.setString(1, id);
            ps.setString(2, location);
            ps.setString(3, specialization);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsById(String id) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT COUNT(*) FROM collectivities WHERE id = ?
            """);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean nameExists(String name) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT COUNT(*) FROM collectivities WHERE name = ?
            """);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean numberExists(String number) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT COUNT(*) FROM collectivities WHERE number = ?
            """);
            ps.setString(1, number);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean alreadyAssigned(String id) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT name, number FROM collectivities WHERE id = ?
            """);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("name") != null || rs.getString("number") != null;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void assignIdentity(String id, String name, String number) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                UPDATE collectivities SET name = ?, number = ? WHERE id = ?
            """);
            ps.setString(1, name);
            ps.setString(2, number);
            ps.setString(3, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Collectivity findById(String id) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT id, name, number, location, specialization
                FROM collectivities WHERE id = ?
            """);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Collectivity c = new Collectivity();
                c.id = rs.getString("id");
                c.name = rs.getString("name");
                c.number = rs.getString("number");
                c.location = rs.getString("location");
                c.specialization = rs.getString("specialization");
                c.members = findMembersByCollectivityId(conn, id);
                return c;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Member> findMembersByCollectivityId(Connection conn, String collectivityId) {
        List<Member> list = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT m.id, m.first_name, m.last_name, m.birth_date, m.gender,
                       m.address, m.profession, m.phone_number, m.email, m.occupation, m.collectivity_id
                FROM members m
                WHERE m.collectivity_id = ?
            """);
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Member m = new Member();
                m.id = rs.getString("id");
                m.firstName = rs.getString("first_name");
                m.lastName = rs.getString("last_name");
                m.birthDate = rs.getString("birth_date");
                m.gender = rs.getString("gender");
                m.address = rs.getString("address");
                m.profession = rs.getString("profession");
                m.phoneNumber = rs.getLong("phone_number");
                m.email = rs.getString("email");
                m.occupation = rs.getString("occupation");
                m.collectivityId = rs.getString("collectivity_id");
                list.add(m);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}