package com.hei.federation_api.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

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
}