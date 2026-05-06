package com.hei.federation_api.Service;

import com.hei.federation_api.Config.DataSource;
import com.hei.federation_api.Entity.*;
import com.hei.federation_api.Repository.CollectivityRepository;
import com.hei.federation_api.Repository.StatisticRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticService {

    private final StatisticRepository repository;
    private final CollectivityRepository collectivityRepository;

    public StatisticService(DataSource dataSource) {
        this.repository = new StatisticRepository(dataSource);
        this.collectivityRepository = new CollectivityRepository(dataSource);
    }

    // v0.0.5 - GET /collectivites/{id}/statistics
    public List<CollectivityLocalStatistics> getLocalStatistics(String collectivityId, String from, String to) {
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new RuntimeException("Collectivity not found");
        }
        if (from == null || to == null) {
            throw new RuntimeException("Query parameters 'from' and 'to' are required");
        }
        List<CollectivityLocalStatistics> stats = repository.getMemberStatistics(collectivityId, from, to);

        return stats;
    }

    // v0.0.5 - GET /collectivites/statistics
    public List<CollectivityOverallStatistics> getOverallStatistics(String from, String to) {
        if (from == null || to == null) {
            throw new RuntimeException("Query parameters 'from' and 'to' are required");
        }
        List<CollectivityOverallStatistics> stats = repository.getOverallStatistics(from, to);

        return stats;
    }
}