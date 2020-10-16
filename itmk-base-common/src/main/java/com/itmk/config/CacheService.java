package com.itmk.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CacheService {
    @Autowired
    private RedisService redisService;

    /**
     * 查询单个实体缓存
     * @param key
     * @param timeout
     * @param cla
     * @param function
     * @param <T>
     */
    public <T> T getEntityCache(String key,Long timeout,Class<T> cla,FunctionEntityCache<T> function){
        //从redis缓存查询数据，如果查到，直接返回缓存数据
        //如果没有查到，从数据库查询数据，放到缓存，并返回数据
        T obj = null;
        //从缓存获取数据
        String value = redisService.get(key);
        //如果没有数据，从数据库查询
        if(StringUtils.isBlank(value)){

            obj = function.getCache();
            //查询后放入缓存
            if(obj != null){
                String search = JSONObject.toJSONString(obj);
                redisService.set(key,search,timeout);
            }else{
                redisService.set(key,null,60000L);
            }
        }else{
            obj = JSON.parseObject(value, cla);
        }
        return obj;
    }

    /**
     * 查询List集合缓存
     * @param key
     * @param timeout
     * @param cla
     * @param function
     * @param <T>
     * @return
     */
    public <T> List<T> getListCache(String key,Long timeout,Class<T> cla,FunctionListCache<T> function){
        List<T> list = null;
        //从redis缓存查询数据，如果查到，直接返回缓存数据
        //如果没有查到，从数据库查询数据，放到缓存，并返回数据
        String value = redisService.get(key);
        if(StringUtils.isBlank(value)){
            //从数据库获取数据
            list = function.getCache();
            if(list.isEmpty()){
                redisService.set(key,null,60000L);
            }else{
                String val = JSONObject.toJSONString(list);
                redisService.set(key,val,timeout);
            }
        }else{
            list = JSON.parseArray(value,cla);
        }
        return list;
    }
}
