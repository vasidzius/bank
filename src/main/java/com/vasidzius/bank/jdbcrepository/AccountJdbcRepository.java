package com.vasidzius.bank.jdbcrepository;

import com.vasidzius.bank.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AccountJdbcRepository extends NamedParameterJdbcTemplate {

    @Autowired
    public AccountJdbcRepository(DataSource dataSource) {
        super(dataSource);
    }

    public List<Account> findAll() {
        return this.query("SELECT * FROM ACCOUNTS WHERE DELETED = 0", getAccountRowMapper());
    }

    public List<Account> findAllDeleted() {
        return this.query("SELECT * FROM ACCOUNTS WHERE DELETED = 1", getAccountRowMapper());
    }

    public Account insert(Account account) {
        Map<String, Object> params = new HashMap<>();
        Long id = this.getJdbcOperations().queryForObject("SELECT ACCOUNTS_SEQ.NEXTVAL", Long.class);
        params.put("id", id);
        params.put("balance", account.getBalance());
        this.update("INSERT INTO ACCOUNTS (ID, BALANCE) VALUES(:id, :balance)", params);
        account.setId(id);
        return account;
    }

    //@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Account find(long accountId) {
        Map<String, Object> params = new HashMap<>();
        params.put("accountId", accountId);
            return this.queryForObject("SELECT * FROM ACCOUNTS WHERE ID = :accountId", params, getAccountRowMapper());
    }

    //@Transactional(propagation = Propagation.MANDATORY)
    public void update(Account account){
        Map<String, Object> params = new HashMap<>();
        params.put("id", account.getId());
        params.put("balance", account.getBalance());
        int update = this.update("UPDATE ACCOUNTS SET BALANCE = :balance WHERE id = :id ", params);
        assert(update == 1);
    }

    public void delete(long accountId) {
        this.getJdbcOperations().update("UPDATE ACCOUNTS SET DELETED = 1 WHERE ID = " + accountId);
    }

    private RowMapper<Account> getAccountRowMapper() {
        return (rs, rowNum) -> {
            Account account = new Account();
            account.setId(rs.getLong("ID"));
            account.setBalance(rs.getLong("BALANCE"));
            account.setDeleted(rs.getBoolean("DELETED"));
            return account;
        };
    }
}
