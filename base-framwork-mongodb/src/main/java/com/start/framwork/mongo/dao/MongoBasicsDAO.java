package com.start.framwork.mongo.dao;

import java.util.List;

public interface MongoBasicsDAO<T> {
    void mongoSave(T obj);
    void mongoDelete(String key,String value);
    void mongoUpdate(String key,String value,T obj) throws Exception;
    T mongoSingleTableSelete(String key,String value);
    List<T> mongoPagingSelete(Integer pageIndex, Integer pageSize);
    List<T> mongoCompositeSelete();
}
