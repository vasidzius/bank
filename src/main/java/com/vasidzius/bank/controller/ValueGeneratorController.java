package com.vasidzius.bank.controller;

import com.vasidzius.bank.generator.ValueGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/generateRandomValues")
@AllArgsConstructor
@Api
@CrossOrigin(value = "http://localhost:4200")
public class ValueGeneratorController {

    private ValueGenerator valueGenerator;

    @ApiOperation(value = "Generate random values")
    @PostMapping
    public ResponseEntity generateRandomValues(
            @RequestParam(value = "accountsNumber", defaultValue = "1") int accountsNumber,
            @RequestParam(value = "threadNumberBetweenTwo", defaultValue = "1") int threadNumberBetweenTwo,
            @RequestParam(value = "transfersBetweenTwo", defaultValue = "1") int transfersBetweenTwo,
            @RequestParam(value = "accountsToDelete", defaultValue = "1") int accountsToDelete,
            @RequestParam(value = "transfersIncreasing", defaultValue = "1") int transfersIncreasing,
            @RequestParam(value = "transfersDecreasing", defaultValue = "1") int transfersDecreasing) {
        try {
            valueGenerator.generateValues(
                    accountsNumber,
                    threadNumberBetweenTwo,
                    transfersBetweenTwo,
                    accountsToDelete,
                    transfersIncreasing,
                    transfersDecreasing);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

}
