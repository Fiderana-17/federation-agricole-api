package com.hei.federation_api.Service;

import com.hei.federation_api.Config.DataSource;
import com.hei.federation_api.Entity.FinancialAccount;
import com.hei.federation_api.Repository.CollectivityRepository;
import com.hei.federation_api.Repository.FinancialAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinancialAccountService {

    private final FinancialAccountRepository repository;
    private final CollectivityRepository collectivityRepository;

    public FinancialAccountService(DataSource dataSource) {
        this.repository = new FinancialAccountRepository(dataSource);
        this.collectivityRepository = new CollectivityRepository(dataSource);
    }

    public List<FinancialAccount> getByCollectivityIdAt(String collectivityId, String at) {
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new RuntimeException("Collectivity not found");
        }
        if (at == null) {
            throw new RuntimeException("Query parameter 'at' is required");
        }
        return repository.findByCollectivityIdAt(collectivityId, at);
    }
}