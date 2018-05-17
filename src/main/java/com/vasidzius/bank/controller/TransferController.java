package com.vasidzius.bank.controller;

import com.vasidzius.bank.model.Transfer;
import com.vasidzius.bank.repositories.jparepository.TransferJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/transfers")
@AllArgsConstructor
public class TransferController {

    private final TransferJpaRepository transferJpaRepository;

    @GetMapping
    public List<Transfer> findAll(){
        return transferJpaRepository.findAll();
    }

    @PostMapping
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Transfer save(Transfer transfer){
        return transferJpaRepository.saveAndFlush(transfer);
    }

    @GetMapping(value = "/{transferId}")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Transfer find(@PathVariable("transferId") long transferId) {
        return transferJpaRepository.getOne(transferId);
    }
}
