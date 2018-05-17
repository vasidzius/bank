package com.vasidzius.bank.repositories.jdbcrepository;

import com.vasidzius.bank.model.Account;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class AccountJdbcRepository extends NamedParameterJdbcTemplate{

    @Autowired
    public AccountJdbcRepository(DataSource dataSource) {
        super(dataSource);
    }

    public List<Account> findAll(){
        return this.query("SELECT * FROM ACCOUNTS", (rs, rowNum) -> {
            Account account = new Account();
            account.setId(rs.getInt("ID"));
            account.setState(rs.getDouble("STATE"));
            return account;
        });
    }


    public Account saveAndFlush(Account account) {
        return null;
    }

    public Account getOne(long accountId) {
        return null;
    }
}
