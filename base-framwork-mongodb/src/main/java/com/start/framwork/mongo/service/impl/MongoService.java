package com.start.framwork.mongo.service.impl;

import org.springframework.stereotype.Service;

import com.start.framwork.mongo.dao.impl.OrderDao;
import com.start.framwork.mongo.dao.impl.UserDao;
import com.start.framwork.mongo.eneity.Order;
import com.start.framwork.mongo.eneity.StorageChange;
import com.start.framwork.mongo.eneity.User;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MongoService  {
    
    @Resource
    UserDao userDao;
    
    @Resource
    OrderDao orderDao;
    
    public void mongoSaveUser(User user) {
    	userDao.mongoSave(user);
    }
    
    public void mongoSaveOrder(Order order) {
    	orderDao.mongoSave(order);
    }
    
    public void mongoSaveStorageChange(StorageChange storageChange) {
    	orderDao.mongoSave(storageChange);
    }
    
    public User seleteUser(String k,String v) {
    	return (User) userDao.mongoSingleTableSelete(k,v);
    }
    
    public void updateUser(String k,String v,User user) throws Exception {
    	userDao.mongoUpdate(k, v, user);
    }
    
    public List<User> pagingSeleteUser(int pageIndex,int pageSize){
    	return userDao.mongoPagingSelete(pageIndex, pageSize);
    }

}
