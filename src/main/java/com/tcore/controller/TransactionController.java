package com.tcore.controller;

import com.tcore.dto.StatusResponseDTO;
import com.tcore.dto.SumResponseDTO;
import com.tcore.dto.Transaction;
import com.tcore.exception.ResourceNotFoundException;
import com.tcore.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/transactionservice/transaction1/{transactionId}", method = RequestMethod.PUT)
    public StatusResponseDTO create(@PathVariable("transactionId") Long transactionId, @RequestBody Transaction transaction) {
        transactionService.create(transactionId, transaction);

        return new StatusResponseDTO("ok");
    }

    @RequestMapping(value = "/transactionservice/transaction/{transactionId}", method = RequestMethod.GET)
    public Transaction get(@PathVariable("transactionId") Long transactionId) {
        Transaction transaction = transactionService.find(transactionId);
        if (null == transaction) {
            throw new ResourceNotFoundException();
        }

        return transaction;
    }

    @RequestMapping(value = "/transactionservice/types/{type}", method = RequestMethod.GET)
    public Set<Long> getTypes(@PathVariable("type") String type) {
        return transactionService.findByType(type);
    }

    @RequestMapping(value = "/transactionservice/sum/{transactionId}", method = RequestMethod.GET)
    public SumResponseDTO sum(@PathVariable("transactionId") Long transactionId) {
        return new SumResponseDTO(transactionService.sumAmountByParentId(transactionId));
    }
}
