package com.final_project.Final_Project.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.final_project.Final_Project.utilityClasses.JsonUtil;
import com.final_project.Final_Project.utilityClasses.OperationResult;
import com.final_project.Final_Project.entity.Balance;
import com.final_project.Final_Project.entity.TransferOperation;
import com.final_project.Final_Project.entity.UserOperation;
import com.final_project.Final_Project.repository.BalanceRepository;
import com.final_project.Final_Project.repository.OperationsRepository;
import com.final_project.Final_Project.repository.TransferOperationsRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BankServiceTest {
    @InjectMocks
    private BankService bankService;
    @Mock
    private BalanceRepository balanceRepository;
    @Mock
    private OperationsRepository operationsRepository;
    @Mock
    private TransferOperationsRepository transferOperationsRepository;

    @BeforeEach
    public void beforeAll() {
        System.out.println(System.currentTimeMillis());
    }

    @AfterEach
    public void afterEach() {
        System.out.println(System.currentTimeMillis());
    }

    @Test
    @DisplayName(value = "Testing getBalance()")
    void getBalanceTest() {
        final Balance testBalance = new Balance();
        testBalance.setBalance(1000L);
        testBalance.setId(1L);
        when(balanceRepository.findById(anyLong())).thenReturn(Optional.of(testBalance));
        final String actual = bankService.getBalance(1L);
        assertEquals(JsonUtil.jsonToBalance(actual), testBalance);
        verify(balanceRepository, times(2)).findById(anyLong());
        verifyNoMoreInteractions(balanceRepository);
    }

    @Test
    @DisplayName(value = "Testing getBalance() with wrong User")
    void getBalanceTestNoSuchUser() {
        OperationResult operationResult = new OperationResult().setResult(-1).setOperationMessage("Данного пользователя не существует");
        when(balanceRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertEquals(operationResult, JsonUtil.jsonToOperationResult(bankService.getBalance(25L)));
        verify(balanceRepository, times(1)).findById(25L);
        verifyNoMoreInteractions(balanceRepository);
    }
    @Test
    @DisplayName(value = "Testing takeMoney()")
    void takeMoneyTest() {
        Balance testBalance = new Balance();
        testBalance.setId(1L);
        testBalance.setBalance(2000L);
        Long moneyBeforeTest = testBalance.getBalance();
        when(balanceRepository.findById(anyLong())).thenReturn(Optional.of(testBalance));
        OperationResult operationResult = new OperationResult().setResult(1).setOperationMessage("Со счета User 1 снято 1000");
        assertEquals(JsonUtil.writeOperationResultToJson(operationResult), bankService.takeMoney(1L, 1000L));
        assertEquals(moneyBeforeTest - 1000L, (long) balanceRepository.findById(1L).get().getBalance());
    }
    @Test
    void takeMoneyNotEnoughMoneyTest() {
        Balance testBalance = new Balance();
        testBalance.setId(1L);
        testBalance.setBalance(1000L);
        when(balanceRepository.findById(anyLong())).thenReturn(Optional.of(testBalance));
        OperationResult operationResult = new OperationResult().setResult(0).setOperationMessage("Недостаточно средств");
        assertEquals(JsonUtil.writeOperationResultToJson(operationResult), bankService.takeMoney(1L, 1000000000L));
    }
    @Test
    void takeMoneyNegativeMoney() {
        OperationResult operationResult = new OperationResult().setResult(0).setOperationMessage("Невозможно взять отрицательную сумму");
        assertEquals(JsonUtil.writeOperationResultToJson(operationResult), bankService.takeMoney(1L, -1000L));
    }
    @Test
    void takeMoneyNoSuchUser() {
        when(balanceRepository.findById(anyLong())).thenReturn(Optional.empty());
        OperationResult operationResult = new OperationResult().setResult(-1).setOperationMessage("Данного пользователя не существует");
        assertEquals(JsonUtil.writeOperationResultToJson(operationResult), bankService.takeMoney(1L, 1000L));
    }
    @Test
    void putMoney() {
        Balance testBalance = new Balance();
        testBalance.setId(1L);
        testBalance.setBalance(1000L);
        Long moneyBeforeTest = testBalance.getBalance();
        when(balanceRepository.findById(anyLong())).thenReturn(Optional.of(testBalance));
        OperationResult operationResult = new OperationResult().setResult(1).setOperationMessage("На счет User 1 добавлено 1000");
        assertEquals(JsonUtil.writeOperationResultToJson(operationResult), bankService.putMoney(1L, 1000L));
        assertEquals(moneyBeforeTest + 1000, JsonUtil.jsonToBalance(bankService.getBalance(1L)).getBalance());
    }
    @Test
    void putMoneyNegativeMoney() {
        OperationResult operationResult = new OperationResult().setResult(0).setOperationMessage("Невозможно добавить отрицательную сумму");
        assertEquals(JsonUtil.writeOperationResultToJson(operationResult), bankService.putMoney(1L, -1000L));
    }
    @Test
    void putMoneyNoSuchUser() {
        when(balanceRepository.findById(anyLong())).thenReturn(Optional.empty());
        OperationResult operationResult = new OperationResult().setResult(0).setOperationMessage("Данного пользователя не существует");
        assertEquals(JsonUtil.writeOperationResultToJson(operationResult), bankService.putMoney(1L, 1000L));
    }
    @Test
    void transferMoney() {
        Balance testSenderBalance = new Balance();
        testSenderBalance.setId(1L);
        testSenderBalance.setBalance(1000L);
        Long moneyUserSenderBeforeTest = testSenderBalance.getBalance();
        Balance testReceiverBalance = new Balance();
        testReceiverBalance.setId(2L);
        testReceiverBalance.setBalance(1000L);
        Long moneyUserReceiverBeforeTest = testReceiverBalance.getBalance();
        when(balanceRepository.findById(1L)).thenReturn(Optional.of(testSenderBalance));
        when(balanceRepository.findById(2L)).thenReturn(Optional.of(testReceiverBalance));
        OperationResult operationResult = new OperationResult().setResult(1).setOperationMessage("User 1 отправил 1000 user 2");
        assertEquals(JsonUtil.writeOperationResultToJson(operationResult), bankService.transferMoney(1L, 2L, 1000L));
        assertEquals(testSenderBalance.getBalance() + 1000L, moneyUserSenderBeforeTest);
        assertEquals(testReceiverBalance.getBalance() - 1000L, moneyUserReceiverBeforeTest);
    }
    @Test
    void transferMoneyTestNoSender() {
        when(balanceRepository.findById(1L)).thenReturn(Optional.empty());
        OperationResult operationResult = new OperationResult().setResult(0).setOperationMessage("Отправителя User 1 не существует");
        assertEquals(JsonUtil.writeOperationResultToJson(operationResult), bankService.transferMoney(1L, 2L, 1000L));
    }
    @Test
    void transferMoneyTestNoReceiver() {
        Balance testSenderBalance = new Balance();
        testSenderBalance.setId(1L);
        testSenderBalance.setBalance(1000L);
        when(balanceRepository.findById(1L)).thenReturn(Optional.of(testSenderBalance));
        when(balanceRepository.findById(2L)).thenReturn(Optional.empty());
        OperationResult operationResult = new OperationResult().setResult(0).setOperationMessage("Получателя User 2 не существует");
        assertEquals(JsonUtil.writeOperationResultToJson(operationResult), bankService.transferMoney(1L, 2L, 1000L));
    }
    @Test
    void transferMoneyTestNotEnoughMoney() {
        Balance testSenderBalance = new Balance();
        testSenderBalance.setId(1L);
        testSenderBalance.setBalance(1000L);
        Balance testReceiverBalance = new Balance();
        testReceiverBalance.setId(2L);
        testReceiverBalance.setBalance(1000L);
        when(balanceRepository.findById(1L)).thenReturn(Optional.of(testSenderBalance));
        when(balanceRepository.findById(2L)).thenReturn(Optional.of(testReceiverBalance));
        OperationResult operationResult = new OperationResult().setResult(0).setOperationMessage("Недостаточно средств у User 1");
        assertEquals(JsonUtil.writeOperationResultToJson(operationResult), bankService.transferMoney(1L, 2L, 100000L));
    }
    @Test
    void transferMoneyNegativeMoney() {
        OperationResult operationResult = new OperationResult().setResult(0).setOperationMessage("Невозможно отправить отрицательную сумму");
        assertEquals(JsonUtil.writeOperationResultToJson(operationResult), bankService.transferMoney(1L, 2L, -1000L));
    }
    @Test
    void transferMoneySameSenderAndReceiverId() {
        OperationResult operationResult = new OperationResult().setResult(0).setOperationMessage("У отправителя и получателя не может быть одинаковый id");
        assertEquals(JsonUtil.writeOperationResultToJson(operationResult), bankService.transferMoney(1L, 1L, 1000L));
    }

    @Test
    void getOperationListTest() throws JsonProcessingException {
        UserOperation testUserOperation1 = new UserOperation();
        UserOperation testUserOperation2 = new UserOperation();
        UserOperation testUserOperation3 = new UserOperation();
        UserOperation testUserOperation4 = new UserOperation();
        testUserOperation1.setId(1L);
        testUserOperation2.setId(2L);
        testUserOperation3.setId(3L);
        testUserOperation4.setId(4L);
        testUserOperation1.setUserId(1L);
        testUserOperation2.setUserId(2L);
        testUserOperation3.setUserId(3L);
        testUserOperation4.setUserId(4L);
        testUserOperation1.setOperationTypeNumber(1);
        testUserOperation2.setOperationTypeNumber(2);
        testUserOperation3.setOperationTypeNumber(3);
        testUserOperation4.setOperationTypeNumber(4);
        testUserOperation1.setOperationTypeName("GET_BALANCE");
        testUserOperation2.setOperationTypeName("PUT_MONEY");
        testUserOperation3.setOperationTypeName("TAKE_MONEY");
        testUserOperation3.setOperationTypeName("TRANSFER_MONEY");
        List<UserOperation> testUserOperationsList = List.of(testUserOperation1, testUserOperation2, testUserOperation3, testUserOperation4);
        when(operationsRepository.findAll()).thenReturn(testUserOperationsList);
        assertEquals(testUserOperation1, JsonUtil.jsonToOperationList(bankService.getOperationList(1L, null, null)).get(0));
        assertTrue(JsonUtil.jsonToOperationList(bankService.getOperationList(10L, null, null)).isEmpty());
    }
    @Test
    void getOperationListTestToDateAfterBeforeDate(){
        assertEquals(JsonUtil.writeOperationResultToJson(new OperationResult()
                .setResult(-1)
                .setOperationMessage("Дата начала не может быть больше даты окончания")),
                bankService.getOperationList(1L, "2023-08-01", "2022-08-01"));
    }
    @Test
    void getOperationListTestIncorrectFromDateAndToDate(){
        String result = bankService.getOperationList(1L, "vbfkfahblaba", "vnfslbaa");
        assertEquals(JsonUtil.jsonToOperationResult(result).getResult(), -1L);
        assertEquals(JsonUtil.jsonToOperationResult(result).getOperationMessage(), "Возникла ошибка с извлечением даты начала и/или окончания");
    }
    @Test
    void getOperationResultTestFromDate(){
        String result = bankService.getOperationList(1L, "vbfkfahblaba", null);
        assertEquals(JsonUtil.jsonToOperationResult(result).getResult(), -1L);
        assertEquals(JsonUtil.jsonToOperationResult(result).getOperationMessage(), "Возникла ошибка с извлечением даты начала");
    }
    @Test
    void getOperationTesultTestToDate(){
        String result = bankService.getOperationList(1L,null,  "vbfkfahblaba");
        assertEquals(JsonUtil.jsonToOperationResult(result).getResult(), -1L);
        assertEquals(JsonUtil.jsonToOperationResult(result).getOperationMessage(), "Возникла ошибка с извлечением даты окончания");
    }
    @Test
    void operation_logTest(){
        when(operationsRepository.save(any(UserOperation.class))).thenReturn(new UserOperation());
        List<UserOperation> userOperationList = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            userOperationList.add(operationsRepository.save(new UserOperation()));
        }
        assertEquals(10L, userOperationList.size());
    }
    @Test
    void transfer_logTest(){
        when(transferOperationsRepository.save(any(TransferOperation.class))).thenReturn(new TransferOperation());
        List<TransferOperation> transferOperationList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            transferOperationList.add(transferOperationsRepository.save(new TransferOperation()));
        }
        assertEquals(10L, transferOperationList.size());
    }
}