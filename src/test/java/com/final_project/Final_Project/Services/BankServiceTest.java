package com.final_project.Final_Project.Services;

import com.final_project.Final_Project.UtilityClasses.JsonUtil;
import com.final_project.Final_Project.entity.Balance;
import com.final_project.Final_Project.repository.BalanceRepository;
import com.final_project.Final_Project.repository.OperationsRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityListeners;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class BankServiceTest {
    @InjectMocks
    private BankService bankService;
    @Mock
    @Autowired
    private BalanceRepository balanceRepository;
    @Mock
    @Autowired
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
    void getBalanceTest() {
        Balance balance = new Balance();
        balance.setBalance(1000L);
        Balance balance1 = new Balance();
        balance1.setBalance(2000L);
        balanceRepository.save(balance);
        balanceRepository.save(balance1);
        System.out.println(balanceRepository.findAll());
        String message = JsonUtil.writeBalanceToJson(balanceRepository.findById(1L).orElseThrow());
        System.out.println(message);
        String message2 = bankService.getBalance(1L);
        System.out.println(message2);
        Assertions.assertEquals(JsonUtil.jsonToBalance(message).getId(), 1L);
        Assertions.assertEquals(JsonUtil.jsonToBalance(message2).getId(), 1L);
        Assertions.assertTrue(JsonUtil.jsonToBalance(message).getBalance() >= 0);
    }

    @Test
    void takeMoneyTest() {
        Balance balance1 = new Balance();
        Long money = 1000L;
        balance1.setBalance(money);
        balanceRepository.save(balance1);
        String correctRequest = bankService.takeMoney(1L, money);
        System.out.println(balanceRepository.findAll());
        Balance balanceFromDB = balanceRepository.findById(1L).orElseThrow();
        Long moneyAfterTake = balanceFromDB.getBalance();
        System.out.println(balance1.getBalance());
//        Long id = 2L;
//        Long money = 1000L;
//        Long userBalance = JsonUtil.jsonToBalance(bankService.getBalance(id)).getBalance();
//        String correctRequest = bankService.takeMoney(id, money);
//        assertEquals(userBalance - money, JsonUtil.jsonToBalance(bankService.getBalance(id)).getBalance());
//        String negativeMoney = bankService.takeMoney(id, -1000L);
//        assertEquals(JsonUtil.jsonToOperationResult(correctRequest).getResult(), 1);
//        assertEquals(JsonUtil.jsonToOperationResult(correctRequest).getOperationMessage(), "Со счета User 2 снято 1000");
//        assertEquals(JsonUtil.jsonToOperationResult(negativeMoney).getOperationMessage(), "Невозможно взять отрицательную сумму");
//        Long actualBalance = JsonUtil.jsonToBalance(bankService.getBalance(id)).getBalance();
//        String notEnoughMoney = bankService.takeMoney(id, actualBalance + 1000L);
//        assertEquals(JsonUtil.jsonToOperationResult(notEnoughMoney).getOperationMessage(), "Недостаточно средств");
//        String noSuchId = bankService.takeMoney(-25L, 1000L);
//        assertEquals(JsonUtil.jsonToOperationResult(noSuchId).getOperationMessage(), "Данного пользователя не существует");
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