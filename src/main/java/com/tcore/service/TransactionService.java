package com.tcore.service;

import com.tcore.dto.Transaction;
import com.tcore.storage.TransactionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private TransactionStorage transactionStorage;

    public void create(Long id, Transaction transaction) {
        transactionStorage.create(id, transaction);
    }

    public Transaction find(Long id) {
        return transactionStorage.find(id);
    }

    public Set<Long> findByType(String type) {
        return transactionStorage.findByType(type).stream().map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    public Double sumAmountByParentId(Long parentId) {
        return transactionStorage.findByParentId(parentId).stream()
                .map(t -> t.getValue().getAmount() + sumAmountByParentId(t.getKey()))
                .reduce(0d, (a, b) -> a + b);
    }
}
