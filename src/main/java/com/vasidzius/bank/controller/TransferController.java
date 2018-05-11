package com.vasidzius.bank.controller;

import com.vasidzius.bank.model.Transfer;
import com.vasidzius.bank.repository.TransferRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

    @GetMapping(value = "/{transferId}")
    public Transfer find(@PathVariable("transferId") long transferId) {
        return transferRepository.getOne(transferId);
    }
}
