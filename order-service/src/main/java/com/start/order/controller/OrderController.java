package com.start.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.start.order.feign.StorageFeignClient;
import com.start.order.model.Order;
import com.start.order.repository.OrderDAO;
import com.start.order.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.math.BigDecimal;

import javax.annotation.Resource;

/**
 * Program Name: springcloud-nacos-seata
 * <p>
 * Description:
 * <p>
 *
 */
@RestController
@Api(tags="order服务")
@RequestMapping("order")
public class OrderController {

    @Resource
    private OrderService orderService;
    @Resource
    private StorageFeignClient storageFeignClient;

    /**
     * 下单：插入订单表、扣减库存，模拟回滚
     *
     * @return
     */
    @GetMapping("/placeOrder/commit")
    @ApiOperation(value="成功提交demo示例")
    public Boolean placeOrderCommit() {

        orderService.placeOrder("1", "product-1", 1);
        return true;
    }

    /**
     * 下单：插入订单表、扣减库存，模拟回滚
     *
     * @return
     */
    @GetMapping("/placeOrder/rollback")
    @ApiOperation(value="失败回滚demo示例")
    public Boolean placeOrderRollback() {
        // product-2 扣库存时模拟了一个业务异常,
        orderService.placeOrder("1", "product-2", 1);
        return true;
    }


    @PostMapping("/placeOrder")
    @ApiOperation(value="demo示例")
    public Boolean placeOrder(String userId, String commodityCode, Integer count) {
        orderService.placeOrder(userId, commodityCode, count);
        return true;
    }
      
}
