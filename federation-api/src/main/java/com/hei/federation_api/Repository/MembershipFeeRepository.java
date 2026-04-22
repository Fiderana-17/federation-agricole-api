package com.hei.federation_api.Repository;

import com.hei.federation_api.Config.DataSource;
import com.hei.federation_api.Entity.MembershipFee;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MembershipFeeRepository {

    private final DataSource dataSource;

    public MembershipFeeRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<MembershipFee> findByCollectivityId(String collectivityId) {
        List<MembershipFee> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT id, eligible_from, frequency, amount, label, status, collectivity_id
                FROM membership_fees
                WHERE collectivity_id = ?
            """);
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MembershipFee fee = new MembershipFee();
                fee.id = rs.getString("id");
                fee.eligibleFrom = rs.getString("eligible_from");
                fee.frequency = rs.getString("frequency");
                fee.amount = rs.getDouble("amount");
                fee.label = rs.getString("label");
                fee.status = rs.getString("status");
                fee.collectivityId = rs.getString("collectivity_id");
                list.add(fee);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MembershipFee insert(MembershipFee fee) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                INSERT INTO membership_fees(id, eligible_from, frequency, amount, label, status, collectivity_id)
                VALUES (?, ?, ?, ?, ?, ?, ?)
            """);
            ps.setString(1, fee.id);
            ps.setString(2, fee.eligibleFrom);
            ps.setString(3, fee.frequency);
            ps.setDouble(4, fee.amount);
            ps.setString(5, fee.label);
            ps.setString(6, fee.status);
            ps.setString(7, fee.collectivityId);
            ps.executeUpdate();
            return fee;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsById(String id) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT COUNT(*) FROM membership_fees WHERE id = ?
            """);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}