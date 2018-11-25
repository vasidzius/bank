package com.vasidzius.bank.controller;

import com.vasidzius.bank.jdbcrepository.TransferJdbcRepository;
import com.vasidzius.bank.model.Transfer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/transfers")
@AllArgsConstructor
@Api
@CrossOrigin(value = "http://localhost:4200")
public class TransferController {

    private final TransferJdbcRepository transferJdbcRepository;

    @ApiOperation(value = "view a list of all existing Transfers")
    @ApiResponses({
            @ApiResponse(code = 204, message = "There are no Transfers")
    })
    @GetMapping
    public ResponseEntity<List<Transfer>> findAll() {
        List<Transfer> all = transferJdbcRepository.findAll();
        if (all.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(all);
        }
        return ResponseEntity.ok(all);
    }

    @ApiOperation(value = "view a list of all existing Transfers that more than beginIndex")
    @ApiResponses({
            @ApiResponse(code = 204, message = "There are no Transfers")
    })
    @GetMapping(value = "/after/{beginIndex}")
    public ResponseEntity<List<Transfer>> findAllAfter(long beginIndex) {
        List<Transfer> all = transferJdbcRepository.findAllAfter(beginIndex);
        if (all.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(all);
        }
        return ResponseEntity.ok(all);
    }

    @ApiOperation(value = "Find Transfer by Id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "There is no Transfer with given Id")
    })
    @GetMapping(value = "/{transferId}")
    public ResponseEntity<Transfer> find(@PathVariable("transferId") long transferId) {
        try {
            return ResponseEntity.ok(transferJdbcRepository.find(transferId));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.noContent().build();
        }
    }

    public void update(Transfer transfer){
        transferJdbcRepository.update(transfer);
    }

    @ApiOperation(value = "create Transfer. There are three possibilities: between two account, increasing (field fromAccountId is null), decreasing (field toAccountId is null)")
    @PostMapping
    public Transfer create(@RequestBody TransferRequest transferRequest) {
        Transfer transfer = new Transfer();
        transfer.setFromAccountId(transferRequest.getFromAccountId());
        transfer.setToAccountId(transferRequest.getToAccountId());
        transfer.setAmountUsingDoubleView(transferRequest.getAmount());
        return transferJdbcRepository.insert(transfer);
    }
}
