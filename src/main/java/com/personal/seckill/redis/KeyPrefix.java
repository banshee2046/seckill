package com.personal.seckill.redis;

public interface KeyPrefix {

    //过期时间
    public int expireSeconds();

    public String getPrefix();
}
