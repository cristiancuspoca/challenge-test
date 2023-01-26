package com.neoris.challenge.repository;

import com.neoris.challenge.repository.entity.SummaryTransaction;
import com.neoris.challenge.repository.entity.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

    @Query(value = "SELECT trn.date as transactionDate, trn.amount as transactionAmount, " +
            " trn.balance as transactionBalance, trn.status as transactionStatus, " +
            " acc.type as typeAccount, acc.number as numberAccount, cl.name as clientName " +
            " FROM Transaction trn " +
            " JOIN trn.account acc " +
            " JOIN trn.client cl " +
            " WHERE cl.id = :clientId AND FORMATDATETIME(trn.date, 'Y-MM-dd') >= :init AND " +
            " FORMATDATETIME(trn.date, 'Y-MM-dd') <= :end ")
    List<SummaryTransaction> getSummaryTransactions(
            @Param("clientId") int clientId,
            @Param("init") String initDate,
            @Param("end") String endDate);
}
