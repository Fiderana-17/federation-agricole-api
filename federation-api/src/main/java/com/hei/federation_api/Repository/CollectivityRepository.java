package com.hei.federation_api.Repository;

import com.hei.federation_api.Config.DataSource;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class CollectivityRepository {

    private final DataSource dataSource;

    public CollectivityRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(String id, String location, boolean approval) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                INSERT INTO collectivities(id, location, federation_approval)
                VALUES (?, ?, ?)
            """);
            ps.setString(1, id);
            ps.setString(2, location);
            ps.setBoolean(3, approval);
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
}