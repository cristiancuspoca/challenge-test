package com.neoris.challenge.api.v1.controller;

import com.google.gson.Gson;
import com.neoris.challenge.api.v1.resource.dto.TransactionDTO;
import com.neoris.challenge.repository.AccountRepository;
import com.neoris.challenge.repository.ClientRepository;
import com.neoris.challenge.repository.TransactionRepository;
import com.neoris.challenge.repository.entity.Account;
import com.neoris.challenge.repository.entity.Client;
import com.neoris.challenge.repository.entity.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private Gson gson;

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        gson = new Gson();
    }

    @Test
    void createTransactionSuccess() throws Exception {
        Account account = mockAccount();
        Client client = mockClient();

        TransactionDTO transactionDto = new TransactionDTO();
        transactionDto.setType("DEBIT");
        transactionDto.setAmount(new BigDecimal(10));
        transactionDto.setAccountId(account.getId());
        transactionDto.setClientId(client.getId());

        when(clientRepository.findById(anyInt())).thenReturn(Optional.of(client));
        when(accountRepository.findById(anyInt())).thenReturn(Optional.of(account));
        when(transactionRepository.save(any())).thenReturn(new Transaction());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions/v1/transaction/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(transactionDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    void createTransactionInsufficientFunds() throws Exception {
        Account account = mockAccount();
        Client client = mockClient();

        TransactionDTO transactionDto = new TransactionDTO();
        transactionDto.setType("DEBIT");
        transactionDto.setAmount(new BigDecimal(2000));
        transactionDto.setAccountId(account.getId());
        transactionDto.setClientId(client.getId());

        when(clientRepository.findById(anyInt())).thenReturn(Optional.of(client));
        when(accountRepository.findById(anyInt())).thenReturn(Optional.of(account));
        when(transactionRepository.save(any())).thenReturn(new Transaction());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions/v1/transaction/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(transactionDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isPaymentRequired()).andReturn();
    }

    @Test
    void createTransactionTypeNotAllowed() throws Exception {
        Account account = mockAccount();
        Client client = mockClient();

        TransactionDTO transactionDto = new TransactionDTO();
        transactionDto.setType("KNOWN");
        transactionDto.setAmount(new BigDecimal(2000));
        transactionDto.setAccountId(account.getId());
        transactionDto.setClientId(client.getId());

        when(clientRepository.findById(anyInt())).thenReturn(Optional.of(client));
        when(accountRepository.findById(anyInt())).thenReturn(Optional.of(account));
        when(transactionRepository.save(any())).thenReturn(new Transaction());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions/v1/transaction/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(transactionDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isPaymentRequired()).andReturn();
    }

    private Account mockAccount() {
        Account account = new Account();
        account.setId(1);
        account.setBalance(new BigDecimal(200));
        account.setStatus(true);
        account.setType("Ahorros");
        return account;
    }

    private Client mockClient() {
        Client client = new Client();
        client.setId(1);
        client.setName("John");
        client.setPassword("12345");
        client.setStatus(true);
        return client;
    }
}