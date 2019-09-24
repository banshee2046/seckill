package com.personal.seckill.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {

    @Autowired
    JedisPool jedisPool;


    public <T> T get(KeyPrefix keyPrefix, String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的KEY
            String realKey = keyPrefix.getPrefix()+key;
            String redisStr = jedis.get(realKey);
            T obj = stringToBean(redisStr, clazz);
            return obj;
        } finally {
            returnToPool(jedis);
        }

    }

    public <T> boolean set(KeyPrefix keyPrefix, String key, T obj) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String redisStr = beanToString(obj);
            if (redisStr == null || redisStr.length() <= 0) {
                return false;
            }
            //生成真正的KEY
            String realKey = keyPrefix.getPrefix()+key;
            int expireSecond = keyPrefix.expireSeconds();
            if(expireSecond<=0){
                jedis.set(realKey, redisStr);
            }else {
                jedis.setex(realKey,expireSecond,redisStr);
            }
            return true;
        } finally {
            returnToPool(jedis);
        }

    }

    public <T> Boolean exists(KeyPrefix keyPrefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的KEY
            String realKey = keyPrefix.getPrefix()+key;

            return jedis.exists(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    public <T> Long incr(KeyPrefix keyPrefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的KEY
            String realKey = keyPrefix.getPrefix()+key;
            return jedis.incr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    public <T> Long decr(KeyPrefix keyPrefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的KEY
            String realKey = keyPrefix.getPrefix()+key;
            return jedis.decr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    private <T> String beanToString(T obj) {
        if (obj == null) {
            return null;
        }
        Class clazz = obj.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return "" + obj;
        } else if (clazz == long.class || clazz == Long.class) {
            return "" + obj;
        } else if (clazz == String.class) {
            return (String) obj;
        } else {
            return JSONObject.toJSONString(obj);
        }
    }

    private <T> T stringToBean(String redisStr, Class clazz) {
        if (redisStr == null || redisStr.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(redisStr);
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(redisStr);
        } else if (clazz == String.class) {
            return (T) redisStr;
        } else {
            //return (T) JSON.parseObject(redisStr,clazz);
            return (T) JSON.toJavaObject(JSON.parseObject(redisStr), clazz);
        }
    }

    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

}
