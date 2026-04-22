package com.hei.federation_api.Service;

import com.hei.federation_api.Entity.CreateCollectivity;
import com.hei.federation_api.Repository.CollectivityRepository;
import com.hei.federation_api.Repository.MemberRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CollectivityService {

    private final CollectivityRepository repository = new CollectivityRepository();
    private final MemberRepository memberRepository = new MemberRepository();

    public List<String> create(List<CreateCollectivity> requests) throws SQLException {

        List<String> ids = new ArrayList<>();

        for (CreateCollectivity req : requests) {

            // 🔴 VALIDATION A
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
}
