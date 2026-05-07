package com.hei.federation_api.Service;

import com.hei.federation_api.Config.DataSource;
import com.hei.federation_api.Entity.CreateMember;
import com.hei.federation_api.Entity.Member;
import com.hei.federation_api.Repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MemberService {

    private final MemberRepository repository;

    public MemberService(DataSource dataSource) {
        this.repository = new MemberRepository(dataSource);
    }

    public List<Member> create(List<CreateMember> requests) {

        List<Member> result = new ArrayList<>();

        for (CreateMember req : requests) {

            if (req.referees == null || req.referees.size() < 2) {
                throw new RuntimeException("At least 2 referees required");
            }

            if (!req.registrationFeePaid || !req.membershipDuesPaid) {
                throw new RuntimeException("Payment required");
            }

            for (String ref : req.referees) {
                if (!repository.existsById(ref)) {
                    throw new RuntimeException("Referee not found: " + ref);
                }
            }

            Member m = new Member();
            m.id = UUID.randomUUID().toString();
            m.firstName = req.firstName;
            m.lastName = req.lastName;
            m.email = req.email;

            repository.insert(m);
            result.add(m);
        }

        return result;
    }
}