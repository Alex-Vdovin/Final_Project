package com.final_project.Final_Project.Services;

import com.final_project.Final_Project.UtilityClasses.JsonUtil;
import com.final_project.Final_Project.entity.Balance;
import com.final_project.Final_Project.repository.BalanceRepository;
import com.final_project.Final_Project.repository.OperationsRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityListeners;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BankServiceTest {
    @InjectMocks
    private BankService bankService;
    @Mock
    private BalanceRepository balanceRepository;
    @Mock
    private OperationsRepository operationsRepository;
    @BeforeEach
    public void beforeAll() {
        System.out.println(System.currentTimeMillis());
    }

    @AfterEach
    public void afterEach() {
        System.out.println(System.currentTimeMillis());
    }

    @Test
    void getBalance() {
        Long id = 1L;
        String message = bankService.getBalance(id);
        Assertions.assertEquals(JsonUtil.jsonToBalance(message).getId(), id);
        Assertions.assertTrue(JsonUtil.jsonToBalance(message).getBalance() >= 0);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    void takeMoney() {
        Long id = 2L;
        Long money = 1000L;
        Long userBalance = JsonUtil.jsonToBalance(bankService.getBalance(id)).getBalance();
        String correctRequest = bankService.takeMoney(id, money);
        assertEquals(userBalance - money, JsonUtil.jsonToBalance(bankService.getBalance(id)).getBalance());
        String negativeMoney = bankService.takeMoney(id, -1000L);
        assertEquals(JsonUtil.jsonToOperationResult(correctRequest).getResult(), 1);
        assertEquals(JsonUtil.jsonToOperationResult(correctRequest).getOperationMessage(), "Со счета User 2 снято 1000");
        assertEquals(JsonUtil.jsonToOperationResult(negativeMoney).getOperationMessage(), "Невозможно взять отрицательную сумму");
        Long actualBalance = JsonUtil.jsonToBalance(bankService.getBalance(id)).getBalance();
        String notEnoughMoney = bankService.takeMoney(id, actualBalance + 1000L);
        assertEquals(JsonUtil.jsonToOperationResult(notEnoughMoney).getOperationMessage(), "Недостаточно средств");
        String noSuchId = bankService.takeMoney(-25L, 1000L);
        assertEquals(JsonUtil.jsonToOperationResult(noSuchId).getOperationMessage(), "Данного пользователя не существует");
    }

    @Test
    void putMoney() {
    }

    @Test
    void transferMoney() {
    }

    @Test
    void operation_log() {
    }

    @Test
    void getOperationList() {
    }
}