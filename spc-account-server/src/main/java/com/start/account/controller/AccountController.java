package com.start.account.controller;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.start.account.service.AccountService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 
 */
@RestController
@Api(tags="account 服务")
@RequestMapping("account")
public class AccountController {

    @Autowired
    private AccountService accountServiceImpl;
    
	@Value(value = "${swagger.title}")
	String title;

    /**
     * 扣减账户余额
     * @param userId 用户id
     * @param money 金额
     * @return
     */
    @ApiOperation(value=" 扣减账户余额demo示例")
    @PostMapping("decrease")
    public String decrease(@RequestParam("userId") Long userId,@RequestParam("money") BigDecimal money){
        accountServiceImpl.decrease(userId,money);
        System.out.println("Account decrease success-------------");
        return "Account decrease success";
    }
    
    @ApiOperation(value="get properties")
    @GetMapping("getTitle")
    public String getTitle(){
        
        System.out.println("swagger.title"+ title);
        return title;
    }
    
}
