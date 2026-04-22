package com.hei.federation_api.Repository;

import com.hei.federation_api.Config.DataSource;
import com.hei.federation_api.Entity.CollectivityTransaction;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CollectivityTransactionRepository {

    private final DataSource dataSource;

    public CollectivityTransactionRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<CollectivityTransaction> findByCollectivityIdAndPeriod(String collectivityId, String from, String to) {
        List<CollectivityTransaction> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT id, creation_date, amount, payment_mode, account_credited_id, member_debited_id
                FROM collectivity_transactions
                WHERE collectivity_id = ?
                AND creation_date BETWEEN ?::date AND ?::date
            """);
            ps.setString(1, collectivityId);
            ps.setString(2, from);
            ps.setString(3, to);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CollectivityTransaction tx = new CollectivityTransaction();
                tx.id = rs.getString("id");
                tx.creationDate = rs.getString("creation_date");
                tx.amount = rs.getDouble("amount");
                tx.paymentMode = rs.getString("payment_mode");
                tx.accountCreditedId = rs.getString("account_credited_id");
                tx.memberDebitedId = rs.getString("member_debited_id");
                list.add(tx);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(CollectivityTransaction tx) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                INSERT INTO collectivity_transactions(id, creation_date, amount, payment_mode, account_credited_id, member_debited_id, collectivity_id)
                VALUES (?, ?::date, ?, ?, ?, ?, ?)
            """);
            ps.setString(1, tx.id);
            ps.setString(2, tx.creationDate);
            ps.setDouble(3, tx.amount);
            ps.setString(4, tx.paymentMode);
            ps.setString(5, tx.accountCreditedId);
            ps.setString(6, tx.memberDebitedId);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}