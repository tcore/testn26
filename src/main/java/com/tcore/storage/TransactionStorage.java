package com.tcore.storage;

import com.tcore.dto.Transaction;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public interface TransactionStorage {
    void create(Long id, Transaction transaction);

    Transaction find(Long id);

    Set<Map.Entry<Long, Transaction>> findByType(String type);

    Set<Map.Entry<Long, Transaction>> findByParentId(Long id);

    Set<Map.Entry<Long, Transaction>> findBy(Predicate<Map.Entry<Long, Transaction>> predicate);
}
