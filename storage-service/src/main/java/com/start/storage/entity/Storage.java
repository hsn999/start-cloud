package com.start.storage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Program Name: springcloud-nacos-seata
 * <p>
 * Description:
 * <p>
 */
@Data
@Accessors(chain = true)
@TableName("storage_tbl")
public class Storage {

  private Long id;
  private String commodityCode;
  private Long count;


}
