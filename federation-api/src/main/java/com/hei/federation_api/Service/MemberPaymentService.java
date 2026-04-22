package com.hei.federation_api.Service;

import com.hei.federation_api.Config.DataSource;
import com.hei.federation_api.Entity.CollectivityTransaction;
import com.hei.federation_api.Entity.CreateMemberPayment;
import com.hei.federation_api.Entity.MemberPayment;
import com.hei.federation_api.Repository.CollectivityTransactionRepository;
import com.hei.federation_api.Repository.MemberPaymentRepository;
import com.hei.federation_api.Repository.MemberRepository;
import com.hei.federation_api.Repository.MembershipFeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MemberPaymentService {

    private final MemberPaymentRepository repository;
    private final MemberRepository memberRepository;
    private final MembershipFeeRepository membershipFeeRepository;
    private final CollectivityTransactionRepository transactionRepository;

    public MemberPaymentService(DataSource dataSource) {
        this.repository = new MemberPaymentRepository(dataSource);
        this.memberRepository = new MemberRepository(dataSource);
        this.membershipFeeRepository = new MembershipFeeRepository(dataSource);
        this.transactionRepository = new CollectivityTransactionRepository(dataSource);
    }

    public List<MemberPayment> create(String memberId, List<CreateMemberPayment> requests) {
        if (!memberRepository.existsById(memberId)) {
            throw new RuntimeException("Member not found");
        }

        List<MemberPayment> result = new ArrayList<>();

        for (CreateMemberPayment req : requests) {
            if (!membershipFeeRepository.existsById(req.membershipFeeIdentifier)) {
                throw new RuntimeException("Membership fee not found");
            }

            MemberPayment payment = new MemberPayment();
            payment.id = UUID.randomUUID().toString();
            payment.amount = req.amount;
            payment.paymentMode = req.paymentMode;
            payment.accountCreditedId = req.accountCreditedIdentifier;
            payment.creationDate = LocalDate.now().toString();
            payment.memberId = memberId;
            payment.membershipFeeId = req.membershipFeeIdentifier;

            repository.insert(payment);

            // créer automatiquement la transaction
            CollectivityTransaction tx = new CollectivityTransaction();
            tx.id = UUID.randomUUID().toString();
            tx.creationDate = payment.creationDate;
            tx.amount = payment.amount;
            tx.paymentMode = payment.paymentMode;
            tx.accountCreditedId = payment.accountCreditedId;
            tx.memberDebitedId = memberId;
            transactionRepository.insert(tx);

            result.add(payment);
        }

        return result;
    }
}