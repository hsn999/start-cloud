package com.start.order.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
public class Order {

  @ApiModelProperty(name="id",value="id",example="1234")
  @TableId(type=IdType.AUTO)
  private Integer id;
  
  @ApiModelProperty(name="userId",value="userId",example="oKong")
  private String userId;
  
  @ApiModelProperty(name="commodityCode",value="商品编码",example="j098h")
  private String commodityCode;
  
  @ApiModelProperty(name="count",value="数量",example="1")
  private Integer count;
  
  @ApiModelProperty(name="money",value="money",example="100")
  private BigDecimal money;

}
