package com.hei.federation_api.Entity;

public class FinancialAccount {

    public String id;
    public String type; // CASH, MOBILE_BANKING, BANK
    public Double amount;
    public String collectivityId;

    // Mobile banking
    public String holderName;
    public String mobileBankingService;
    public Long mobileNumber;

    // Bank
    public String bankName;
    public Integer bankCode;
    public Integer bankBranchCode;
    public Integer bankAccountNumber;
    public Integer bankAccountKey;

    public FinancialAccount() {}
}