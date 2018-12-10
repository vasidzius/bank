package com.vasidzius.bank;

import com.vasidzius.bank.controller.TransfersExecutor;
import com.vasidzius.bank.config.spring.ApplicationContextProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
        TransfersExecutor transferStarter = ApplicationContextProvider.getBean(TransfersExecutor.class);
        transferStarter.startTransfers();
    }


}
