package com.tcore.storage;

import com.tcore.dto.Transaction;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class InMemoryTransactionStorage implements TransactionStorage {
    private Map<Long, Transaction> storage = new HashMap<>();

    public void create(Long id, Transaction transaction) {
        storage.put(id, transaction);
    }

    public Transaction find(Long id) {
        return storage.get(id);
    }

    public Set<Map.Entry<Long, Transaction>> findByType(String type) {
        return findBy(e -> e.getValue().getType().equals(type));
    }

    public Set<Map.Entry<Long, Transaction>> findByParentId(Long id) {
        return findBy(e -> e.getValue().getParentId() != null && e.getValue().getParentId().equals(id));
    }

    public Set<Map.Entry<Long, Transaction>> findBy(Predicate<Map.Entry<Long, Transaction>> predicate) {
        return storage.entrySet().stream()
                .filter(predicate)
                .collect(Collectors.toSet());
    }

}
