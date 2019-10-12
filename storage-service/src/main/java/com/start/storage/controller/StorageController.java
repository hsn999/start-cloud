package com.start.storage.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.start.storage.service.StorageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;

/**
 * Program Name: springcloud-nacos-seata
 * <p>
 * Description:
 * <p>
 */
@RestController
@Api(tags="storage 服务")
@RequestMapping("storage")
public class StorageController {

    @Resource
    private StorageService storageService;

    /**
     * 减库存
     * @param commodityCode 商品代码
     * @param count 数量
     * @return
     */
    @ApiOperation(value=" 减库存demo示例")
    @RequestMapping(path = "/deduct")
    public Boolean deduct(String commodityCode, Integer count) {
        storageService.deduct(commodityCode, count);
        return true;
    }

}
