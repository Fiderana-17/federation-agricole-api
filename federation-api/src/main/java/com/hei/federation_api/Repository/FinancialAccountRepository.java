package com.hei.federation_api.Repository;

import com.hei.federation_api.Config.DataSource;
import com.hei.federation_api.Entity.FinancialAccount;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FinancialAccountRepository {

    private final DataSource dataSource;

    public FinancialAccountRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<FinancialAccount> findByCollectivityIdAt(String collectivityId, String at) {
        List<FinancialAccount> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT fa.id, fa.type, fa.holder_name, fa.mobile_banking_service,
                       fa.mobile_number, fa.bank_name, fa.bank_code, fa.bank_branch_code,
                       fa.bank_account_number, fa.bank_account_key, fa.collectivity_id,
                       COALESCE(SUM(ct.amount), 0) as balance
                FROM financial_accounts fa
                LEFT JOIN collectivity_transactions ct
                    ON ct.account_credited_id = fa.id
                    AND ct.creation_date <= ?::date
                WHERE fa.collectivity_id = ?
                GROUP BY fa.id, fa.type, fa.holder_name, fa.mobile_banking_service,
                         fa.mobile_number, fa.bank_name, fa.bank_code, fa.bank_branch_code,
                         fa.bank_account_number, fa.bank_account_key, fa.collectivity_id
            """);
            ps.setString(1, at);
            ps.setString(2, collectivityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FinancialAccount fa = new FinancialAccount();
                fa.id = rs.getString("id");
                fa.type = rs.getString("type");
                fa.holderName = rs.getString("holder_name");
                fa.mobileBankingService = rs.getString("mobile_banking_service");
                fa.mobileNumber = rs.getLong("mobile_number");
                fa.bankName = rs.getString("bank_name");
                fa.bankCode = rs.getInt("bank_code");
                fa.bankBranchCode = rs.getInt("bank_branch_code");
                fa.bankAccountNumber = rs.getInt("bank_account_number");
                fa.bankAccountKey = rs.getInt("bank_account_key");
                fa.collectivityId = rs.getString("collectivity_id");
                fa.amount = rs.getDouble("balance");
                list.add(fa);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}