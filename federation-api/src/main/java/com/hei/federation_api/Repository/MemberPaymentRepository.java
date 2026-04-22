package com.hei.federation_api.Repository;

import com.hei.federation_api.Config.DataSource;
import com.hei.federation_api.Entity.MemberPayment;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberPaymentRepository {

    private final DataSource dataSource;

    public MemberPaymentRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public MemberPayment insert(MemberPayment payment) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                INSERT INTO member_payments(id, amount, payment_mode, account_credited_id, creation_date, member_id, membership_fee_id)
                VALUES (?, ?, ?, ?, ?, ?, ?)
            """);
            ps.setString(1, payment.id);
            ps.setDouble(2, payment.amount);
            ps.setString(3, payment.paymentMode);
            ps.setString(4, payment.accountCreditedId);
            ps.setString(5, payment.creationDate);
            ps.setString(6, payment.memberId);
            ps.setString(7, payment.membershipFeeId);
            ps.executeUpdate();
            return payment;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}