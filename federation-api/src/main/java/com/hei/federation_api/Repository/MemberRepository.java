package com.hei.federation_api.Repository;

import com.hei.federation_api.Config.DataSource;
import com.hei.federation_api.Entity.Member;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class MemberRepository {

    private final DataSource dataSource;

    public MemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean existsById(String id) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT COUNT(*) FROM members WHERE id = ?
            """);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(Member m) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                INSERT INTO members(id, first_name, last_name, email, collectivity_id)
                VALUES (?, ?, ?, ?, ?)
            """);
            ps.setString(1, m.id);
            ps.setString(2, m.firstName);
            ps.setString(3, m.lastName);
            ps.setString(4, m.email);
            ps.setString(5, m.collectivityId);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Member findById(String id) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT id, first_name, last_name, birth_date, gender,
                       address, profession, phone_number, email, occupation, collectivity_id
                FROM members WHERE id = ?
            """);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapMember(rs);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Member mapMember(ResultSet rs) throws SQLException {
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
        return m;
    }
}