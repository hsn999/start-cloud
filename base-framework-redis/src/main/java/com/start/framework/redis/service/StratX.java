package com.start.framework.redis.service;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

@Service
public class StratX {
      
       /**
        *启动的时候观察控制台是否打印此信息;
        */
       public StratX() {
              System.out.println("strat ...Redis...Client...................");

       }
}