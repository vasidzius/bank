package com.vasidzius.bank.controller;

import com.vasidzius.bank.model.Transfer;
import com.vasidzius.bank.repository.TransferRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping(value = "/transfers")
@AllArgsConstructor
@Transactional
public class TransferController {

    private TransferRepository transferRepository;

    @GetMapping
    public List<Transfer> findAll(){
        return transferRepository.findAll();
    }

    @PostMapping
    public Transfer save(Transfer transfer){
        return transferRepository.save(transfer);
    }


}
