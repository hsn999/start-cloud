package com.start.order.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.start.order.model.Order;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
/**
 * Program Name: springcloud-nacos-seata
 * <p>
 * Description:
 * <p>
 *
 */
@Mapper
@Repository
public interface OrderDAO extends BaseMapper<Order> {

}
