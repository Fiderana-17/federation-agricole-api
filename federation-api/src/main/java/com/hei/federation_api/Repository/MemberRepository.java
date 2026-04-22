package com.hei.federation_api.Repository;

import javax.sql.DataSource;
import java.sql.*;

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

    public void insert(com.hei.federation_api.Entity.Member m) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                INSERT INTO members(id, first_name, last_name, email)
                VALUES (?, ?, ?, ?)
            """);
            ps.setString(1, m.id);
            ps.setString(2, m.firstName);
            ps.setString(3, m.lastName);
            ps.setString(4, m.email);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}