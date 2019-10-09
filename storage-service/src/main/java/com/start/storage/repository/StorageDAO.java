package com.start.storage.repository;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.start.storage.entity.Storage;

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
public interface StorageDAO extends BaseMapper<Storage> {

}
