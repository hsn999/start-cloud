package com.start.order.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
/**
 * Program Name: springcloud-nacos-seata
 * <p>
 * Description:
 * <p>
 *
 */
@Data
@Accessors(chain = true)
@TableName("order_tbl")
public class Order {

  @TableId(type=IdType.AUTO)
  private Integer id;
  private String userId;
  private String commodityCode;
  private Integer count;
  private BigDecimal money;

}
