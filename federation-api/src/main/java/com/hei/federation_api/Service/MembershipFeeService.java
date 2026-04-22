package com.hei.federation_api.Service;

import com.hei.federation_api.Config.DataSource;
import com.hei.federation_api.Entity.CreateMembershipFee;
import com.hei.federation_api.Entity.MembershipFee;
import com.hei.federation_api.Repository.CollectivityRepository;
import com.hei.federation_api.Repository.MembershipFeeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MembershipFeeService {

    private final MembershipFeeRepository repository;
    private final CollectivityRepository collectivityRepository;

    public MembershipFeeService(DataSource dataSource) {
        this.repository = new MembershipFeeRepository(dataSource);
        this.collectivityRepository = new CollectivityRepository(dataSource);
    }

    public List<MembershipFee> getByCollectivityId(String collectivityId) {
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new RuntimeException("Collectivity not found");
        }
        return repository.findByCollectivityId(collectivityId);
    }

    public List<MembershipFee> create(String collectivityId, List<CreateMembershipFee> requests) {
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new RuntimeException("Collectivity not found");
        }

        List<MembershipFee> result = new ArrayList<>();

        for (CreateMembershipFee req : requests) {
            if (req.amount == null || req.amount < 0) {
                throw new RuntimeException("Amount must be greater than 0");
            }
            if (req.frequency == null) {
                throw new RuntimeException("Frequency is required");
            }

            MembershipFee fee = new MembershipFee();
            fee.id = UUID.randomUUID().toString();
            fee.eligibleFrom = req.eligibleFrom;
            fee.frequency = req.frequency;
            fee.amount = req.amount;
            fee.label = req.label;
            fee.status = "ACTIVE";
            fee.collectivityId = collectivityId;

            result.add(repository.insert(fee));
        }

        return result;
    }
}