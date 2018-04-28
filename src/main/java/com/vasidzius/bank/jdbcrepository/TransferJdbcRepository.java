package com.vasidzius.bank.jdbcrepository;

import com.vasidzius.bank.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TransferJdbcRepository extends NamedParameterJdbcTemplate{

    @Autowired
    public TransferJdbcRepository(DataSource dataSource) {
        super(dataSource);
    }

    public List<Transfer> findAll(){
        return this.query("SELECT * FROM TRANSFERS", getTransferRowMapper());
    }

    public Transfer insert(Transfer transfer) {
        Long id = this.getJdbcOperations().queryForObject("SELECT TRANSFERS_SEQ.NEXTVAL", Long.class);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("fromAccountId", transfer.getFromAccountId());
        params.put("toAccountId", transfer.getToAccountId());
        params.put("amount", transfer.getAmount());
        this.update("INSERT INTO TRANSFERS (ID, FROM_ACCOUNT_ID, TO_ACCOUNT_ID, AMOUNT) VALUES(:id, :fromAccountId, :toAccountId, :amount)", params);
        transfer.setId(id);
        return transfer;
    }

    //@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Transfer find(long transferId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", transferId);
        return this.queryForObject("SELECT * FROM TRANSFERS WHERE ID = :id", params, getTransferRowMapper());
    }

    //@Transactional(propagation = Propagation.MANDATORY)
    public void update(Transfer transfer){
        Map<String, Object> params = new HashMap<>();
        params.put("id", transfer.getId());
        params.put("executed", Optional.ofNullable(transfer.getExecuted()).map(value -> value  ? 1 : 0).orElse(null));
        int update = this.update("UPDATE TRANSFERS SET EXECUTED = :executed WHERE ID = :id", params);
        assert(update == 1);
    }

    private RowMapper<Transfer> getTransferRowMapper() {
        return (ResultSet rs, int rowNum) -> {
            Transfer transfer = new Transfer();
            transfer.setId(rs.getLong("ID"));
            transfer.setToAccountId(rs.getLong("TO_ACCOUNT_ID") == 0 ? null : rs.getLong("TO_ACCOUNT_ID"));
            transfer.setFromAccountId(rs.getLong("FROM_ACCOUNT_ID") == 0 ? null : rs.getLong("FROM_ACCOUNT_ID"));
            transfer.setAmount(rs.getLong("AMOUNT"));
            transfer.setExecuted(Optional.ofNullable(rs.getString("EXECUTED")).map(value -> value.equals("1")).orElse(null));
            return transfer;
        };
    }
}
