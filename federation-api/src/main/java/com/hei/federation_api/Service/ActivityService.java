package com.hei.federation_api.Service;

import com.hei.federation_api.Config.DataSource;
import com.hei.federation_api.Entity.*;
import com.hei.federation_api.Repository.ActivityRepository;
import com.hei.federation_api.Repository.CollectivityRepository;
import com.hei.federation_api.Repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    private final ActivityRepository repository;
    private final CollectivityRepository collectivityRepository;
    private final MemberRepository memberRepository;

    public ActivityService(DataSource dataSource) {
        this.repository = new ActivityRepository(dataSource);
        this.collectivityRepository = new CollectivityRepository(dataSource);
        this.memberRepository = new MemberRepository(dataSource);
    }

    public List<CollectivityActivity> create(String collectivityId, List<CreateCollectivityActivity> requests) {
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new RuntimeException("Collectivity not found");
        }
        List<CollectivityActivity> result = new ArrayList<>();
        for (CreateCollectivityActivity req : requests) {
            if (req.recurrenceRule != null && req.executiveDate != null) {
                throw new RuntimeException("Cannot provide both recurrenceRule and executiveDate");
            }
            CollectivityActivity a = new CollectivityActivity();
            a.id = UUID.randomUUID().toString();
            a.label = req.label;
            a.activityType = req.activityType;
            a.memberOccupationConcerned = req.memberOccupationConcerned;
            a.recurrenceRule = req.recurrenceRule;
            a.executiveDate = req.executiveDate;
            a.collectivityId = collectivityId;
            result.add(repository.insert(a));
        }
        return result;
    }

    public List<CollectivityActivity> getAll(String collectivityId) {
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new RuntimeException("Collectivity not found");
        }
        return repository.findByCollectivityId(collectivityId);
    }

    // occurrenceDate est maintenant obligatoire dans le body ou en query param
    public List<ActivityMemberAttendance> createAttendance(
            String collectivityId,
            String activityId,
            String occurrenceDate,
            List<CreateActivityMemberAttendance> requests
    ) {
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new RuntimeException("Collectivity not found");
        }
        if (!repository.existsById(activityId)) {
            throw new RuntimeException("Activity not found");
        }

        List<ActivityMemberAttendance> result = new ArrayList<>();
        for (CreateActivityMemberAttendance req : requests) {
            String existing = repository.getAttendanceStatus(activityId, req.memberIdentifier, occurrenceDate);
            // MISSING ou ATTENDED ne peuvent plus être modifiés une fois enregistrés
            if (existing != null && !existing.equals("UNDEFINED")) {
                throw new RuntimeException("Attendance already confirmed for member: " + req.memberIdentifier);
            }
            ActivityMemberAttendance att = repository.insertAttendance(
                    activityId, req.memberIdentifier, req.attendanceStatus, occurrenceDate);

            Member m = memberRepository.findById(req.memberIdentifier);
            if (m != null) {
                MemberDescription desc = new MemberDescription();
                desc.id = m.id;
                desc.firstName = m.firstName;
                desc.lastName = m.lastName;
                desc.email = m.email;
                desc.occupation = m.occupation;
                att.memberDescription = desc;
            }
            result.add(att);
        }
        return result;
    }

    public List<ActivityMemberAttendance> getAttendance(String collectivityId, String activityId) {
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new RuntimeException("Collectivity not found");
        }
        if (!repository.existsById(activityId)) {
            throw new RuntimeException("Activity not found");
        }
        return repository.findAttendanceByActivityId(activityId);
    }
}