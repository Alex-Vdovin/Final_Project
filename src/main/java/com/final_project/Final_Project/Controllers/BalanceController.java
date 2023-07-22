package com.final_project.Final_Project.Controllers;

import com.final_project.Final_Project.Services.BankService;
import com.final_project.Final_Project.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class BalanceController {
    public final BankService bankService;
    @ResponseBody
    @RequestMapping("/test")
    String test(){
        return "TEST COMPLETED";
    }
    @ResponseBody
    @RequestMapping("/test2")
    int test2(){
        return 100;
    }
    @RequestMapping("/balance")
    @ResponseBody
    String getBalance1(){
        return bankService.getBalance(1L);
    }
    @RequestMapping("/addUser")
    @ResponseBody
    String addUserTest(){
        String message;
        String messageTest;
        try {
            return bankService.addUser(7L, 1000L);
        }catch (Exception e){
            return Arrays.toString(e.getStackTrace());
        }

    }
}
