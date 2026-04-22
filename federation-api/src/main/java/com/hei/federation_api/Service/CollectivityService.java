package com.hei.federation_api.Service;

import com.hei.federation_api.Entity.CreateCollectivity;
import com.hei.federation_api.Repository.CollectivityRepository;
import com.hei.federation_api.Repository.MemberRepository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CollectivityService {

    private final CollectivityRepository repository;
    private final MemberRepository memberRepository;

    public CollectivityService(DataSource dataSource) {
        this.repository = new CollectivityRepository(dataSource);
        this.memberRepository = new MemberRepository(dataSource);
    }

    public List<String> create(List<CreateCollectivity> requests) {

        List<String> ids = new ArrayList<>();

        for (CreateCollectivity req : requests) {

            if (!req.federationApproval) {
                throw new RuntimeException("Federation approval required");
            }

            if (req.structure == null) {
                throw new RuntimeException("Structure required");
            }

            for (String memberId : req.members) {
                if (!memberRepository.existsById(memberId)) {
                    throw new RuntimeException("Member not found");
                }
            }

            String id = UUID.randomUUID().toString();

            repository.insert(id, req.location, req.federationApproval);

            ids.add(id);
        }

        return ids;
    }

    public void assignIdentity(String id, String name, String number) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Collectivity not found");
        }

        if (repository.alreadyAssigned(id)) {
            throw new RuntimeException("Identity already assigned");
        }

        if (repository.nameExists(name)) {
            throw new RuntimeException("Name already exists");
        }

        if (repository.numberExists(number)) {
            throw new RuntimeException("Number already exists");
        }

        repository.assignIdentity(id, name, number);
    }
}