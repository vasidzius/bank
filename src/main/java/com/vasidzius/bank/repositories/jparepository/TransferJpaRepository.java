package com.vasidzius.bank.repositories.jparepository;

import com.vasidzius.bank.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRES_NEW)
public interface TransferJpaRepository extends JpaRepository<Transfer, Long> {
}
