package com.start.account.controller;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.start.account.service.AccountService;

/**
 * @author 
 */
@RestController
@RequestMapping("account")
public class AccountController {

    @Autowired
    private AccountService accountServiceImpl;

    /**
     * 扣减账户余额
     * @param userId 用户id
     * @param money 金额
     * @return
     */
    @RequestMapping("decrease")
    public String decrease(@RequestParam("userId") Long userId,@RequestParam("money") BigDecimal money){
        accountServiceImpl.decrease(userId,money);
        System.out.println("Account decrease success-------------");
        return "Account decrease success";
    }
}
