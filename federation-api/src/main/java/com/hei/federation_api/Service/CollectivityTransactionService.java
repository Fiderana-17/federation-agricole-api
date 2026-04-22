package com.hei.federation_api.Service;

import com.hei.federation_api.Config.DataSource;
import com.hei.federation_api.Entity.CollectivityTransaction;
import com.hei.federation_api.Repository.CollectivityRepository;
import com.hei.federation_api.Repository.CollectivityTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectivityTransactionService {

    private final CollectivityTransactionRepository repository;
    private final CollectivityRepository collectivityRepository;

    public CollectivityTransactionService(DataSource dataSource) {
        this.repository = new CollectivityTransactionRepository(dataSource);
        this.collectivityRepository = new CollectivityRepository(dataSource);
    }

    public List<CollectivityTransaction> getByPeriod(String collectivityId, String from, String to) {
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new RuntimeException("Collectivity not found");
        }
        if (from == null || to == null) {
            throw new RuntimeException("from and to are required");
        }
        return repository.findByCollectivityIdAndPeriod(collectivityId, from, to);
    }
}