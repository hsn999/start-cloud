package com.start.framwork.mongo.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.start.framwork.mongo.util.MyClassUtil;

import java.lang.reflect.Type;

public abstract class MongoBasicsDAOImpl<T> implements MongoBasicsDAO<T>{
	
	@Autowired
    private MongoTemplate mongoTemplate;
	
	private Class<T> obj;
	
    protected MongoBasicsDAOImpl() {
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];
        this.obj = (Class<T>) trueType;
    }

	@Override
	public void mongoSave(Object obj) {
		// TODO Auto-generated method stub
		mongoTemplate.save(obj);
	}

	@Override
	public void mongoDelete(String key,String value) {
		// TODO Auto-generated method stub
		Query query = new Query(Criteria.where(key).is(value));
		mongoTemplate.remove(query,obj);
	}

	@Override
	public void mongoUpdate(String key,String value,Object o) throws Exception {
        Query query = new Query(Criteria.where(key).is(value));         
        Update update = new Update();        
        String[] field = MyClassUtil.getFieldName(o);
		
		for(int j = 0; j < field.length; j++) {		
		   Object v = MyClassUtil.getFieldValueByName(field[j],o);		   
		   //System.out.println(field[j]+"---"+v);		   
		   update.set(field[j], v);
		}           
        mongoTemplate.updateFirst(query, update, obj);        
	}

	@SuppressWarnings("unchecked")
	@Override
	public T mongoSingleTableSelete(String key,String value) {
		// TODO Auto-generated method stub
		Query query = new Query(Criteria.where(key).is(value));		
		Type t = this.getClass().getGenericSuperclass();		
        return (T) mongoTemplate.findOne(query, obj);
	}

	@Override
	public List<T> mongoPagingSelete(Integer pageIndex, Integer pageSize) {
		// TODO Auto-generated method stub
        Query query = new Query();
        //Pageable pageable = new PageRequest(pageIndex ,pageSize);
        Pageable pageable = PageRequest.of(pageIndex ,pageSize);
        query.with(pageable);
        return (List<T>) mongoTemplate.find(query, obj);
	}

	@Override
	public List mongoCompositeSelete() {
		// TODO Auto-generated method stub
		return null;
	}

}
