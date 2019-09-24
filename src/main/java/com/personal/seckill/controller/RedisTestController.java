package com.personal.seckill.controller;

import com.personal.seckill.domain.User;
import com.personal.seckill.redis.RedisService;
import com.personal.seckill.redis.UserKey;
import com.personal.seckill.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("redis")
public class RedisTestController {

    @Autowired
    RedisService redisService;

    @RequestMapping("getRedis")
    @ResponseBody
    public CommonResult<User> getRedis (){

        User user = redisService.get(UserKey.getById,"1",User.class);
        return CommonResult.success(user);
    }

    @RequestMapping("setRedis")
    @ResponseBody
    public CommonResult<Boolean> setRedis (){

        User user = new User(1,"test");
        Boolean ret = redisService.set(UserKey.getById ,"1",user);
        return CommonResult.success(ret);
    }
}
