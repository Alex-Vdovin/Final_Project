package com.final_project.Final_Project.Controllers;

import com.final_project.Final_Project.Services.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BalanceController {
    public final BankService bankService;

    @RequestMapping("/getBalance")
    @ResponseBody
    String getBalance(@RequestParam(value = "id") Long id) {
        return bankService.getBalance(id);
    }

    @RequestMapping("/putMoney")
    @ResponseBody
    String putMoney(@RequestParam(value = "id") Long id,
                    @RequestParam(value = "money") Long money) {
        return bankService.putMoney(id, money);
    }

    @RequestMapping("/takeMoney")
    @ResponseBody
    String takeMoney(@RequestParam(value = "id") Long id,
                     @RequestParam(value = "money") Long money) {
        return bankService.takeMoney(id, money);
    }

    @RequestMapping("/transferMoney")
    @ResponseBody
    String transferMoney(@RequestParam(value = "userSenderId") Long userSenderId,
                         @RequestParam(value = "userReceiverId") Long userReceiverId,
                         @RequestParam(value = "money") Long money) {
        return bankService.transferMoney(userSenderId, userReceiverId, money);
    }

    @RequestMapping("/getOperationList")
    @ResponseBody
    String getOperationList(@RequestParam(value = "id") Long id,
                @RequestParam (value = "fromDate", required = false) String fromDate,
                @RequestParam(value = "toDate", required = false) String toDate){

        return bankService.getOperationList(id, fromDate, toDate);
    }

}
