package com.start.base.common.validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;


@Component
public class ValidatorImpl implements InitializingBean {

   private Validator validator;

   //实现校验方法并返回结果
   public ValidationResult validate(Object bean) {
       ValidationResult validationResult = new ValidationResult();
       Set<ConstraintViolation<Object>> constraintViolations = validator.validate(bean);
       if (constraintViolations.size() > 0) {
           //有错误
           validationResult.setHasError(true);
           Map<String,String> errMsgMap = new HashMap<>();
           constraintViolations.forEach(constraintViolation -> {
               String errMsg = constraintViolation.getMessage();
               String propertyName = constraintViolation.getPropertyPath().toString();
               errMsgMap.put(propertyName,errMsg);
               
           });
           validationResult.setErrMsgMap(errMsgMap);
       }
       return validationResult;
   }

   @Override
   public void afterPropertiesSet() throws Exception {
       //使hibernate validator通过工厂初始化进行实例化
	   ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	   this.validator = factory.getValidator();
      
   }
}