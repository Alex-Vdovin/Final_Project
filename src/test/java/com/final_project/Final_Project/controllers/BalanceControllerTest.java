package com.final_project.Final_Project.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BalanceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getBalance() throws Exception {
        mockMvc.perform(get("/getBalance?id=1"))
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(get("/getBalance"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    void putMoney() throws Exception {
        mockMvc.perform(get("/putMoney?id=1&money=1000"))
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(get("/putMoney"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    void takeMoney() throws Exception{
        mockMvc.perform(get("/takeMoney?id=1&money=1000"))
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(get("/takeMoney"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    void transferMoney() throws Exception{
        mockMvc.perform(get("/transferMoney?userSenderId=1&userReceiverId=2&money=1000"))
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(get("/transferMoney"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    void getOperationList() throws Exception{
        mockMvc.perform(get("/getOperationList?id=1&fromDate=2023-07-01&toDate=2023-07-30"))
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(get("/getOperationList?id=1&toDate=2023-07-30"))
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(get("/getOperationList?id=1&fromDate=2023-07-01"))
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(get("/getOperationList?id=1"))
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(get("/getOperationList"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }
}