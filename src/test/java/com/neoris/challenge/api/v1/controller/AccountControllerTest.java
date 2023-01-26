package com.neoris.challenge.api.v1.controller;

import com.neoris.challenge.repository.AccountRepository;
import com.neoris.challenge.repository.entity.Account;
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

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountRepository repositoryMock;

    @Test
    void getAccount() throws Exception {
        Account account = mockAccount(1);

        when(repositoryMock.findById(anyInt())).thenReturn(Optional.of(account));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/v1/account/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAccountNotFound() throws Exception {
        when(repositoryMock.findById(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/v1/account/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private Account mockAccount(int id) {
        Account account = new Account();
        account.setId(id);
        account.setBalance(new BigDecimal(200));
        account.setStatus(true);
        account.setType("Ahorros");
        return account;
    }
}