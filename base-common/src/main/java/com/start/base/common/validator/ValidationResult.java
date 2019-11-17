package com.start.base.common.validator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

//import org.springframework.util.StringUtils;

public class ValidationResult {

    //判断校验是否有错
    private boolean hasError = false;

    //存放错误信息的map
    private Map<String,String> errMsgMap = new HashMap<>();

    //格式化字符串信息获得错误结果的msg方法
    public String getErrMsg(){
       
        return JSONObject.toJSONString(errMsgMap);
    }

	public void setHasError(boolean b) {
		// TODO Auto-generated method stub
		this.hasError=b;
	}

	public Object getErrMsgMap() {
		// TODO Auto-generated method stub
		return errMsgMap;
	}

	public void setErrMsgMap(Map<String, String> errMsgMap) {
		 this.errMsgMap = errMsgMap;
	}
}