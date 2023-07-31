package com.final_project.Final_Project.repository;

import com.final_project.Final_Project.entity.Balance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class BalanceRepositoryTest {
    @Autowired
    private BalanceRepository balanceRepository;
    @Test
    public void test1(){
        System.out.println("In test");
        Balance balance = new Balance();
        balance.setBalance(100L);
        balanceRepository.save(balance);

        Balance balanceFromDB = balanceRepository.findById(1L).orElseThrow();
        System.out.println("id = " + balanceFromDB.getId());
        System.out.println("balance = " + balanceFromDB.getBalance());
    }

}