package com.seres.base.tool.redis;

import com.seres.base.PageInfo;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis操作
 * @ClassName RedisService
 * @Description 操作redis API
 * @author lyly
 * @date 2020年2月12日 上午13:55:35
 *
 */
@Service
public class RedisService {

    /**
     * 注入redis服务
     */
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 查询redis中是否存在指定的key
     *
     * @param key
     * @return
     */
    public Boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置过期时间，对已经设置过ttl的数据从新set时，ttl将重置
     * @param key
     * @param timeout，单位：秒
     * @return
     */
    public Boolean setExpire(String key, long timeout) {
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置指定key在指定时间过期
     * @param key
     * @param date
     * @return
     */
    public Boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 获取过期时间, 单位 秒
     * @param key
     * @return
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 获取值
     * @param key
     * @return
     */
    public String getString(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取值
     * @param key
     * @return
     */
    public <T> T getObject(String key, Class<T> clazz) {
        return JSON.parseObject(redisTemplate.opsForValue().get(key), clazz);
    }

    /**
     * 设置值
     *
     * @param key
     * @param value
     */
    public void setString(String key, String value){
        setString(key, value, null);
    }

    /**
     * 设置值
     * @param key
     * @param value
     */
    public void setObject(String key, Object value){
        setString(key, JSON.toJSONString(value));
    }

    /**
     * 删除
     *
     * @param key
     */
    public void delete(String key){
        redisTemplate.delete(key);
    }

    /**
     * 设置值并指定过期时间
     * @param key
     * @param value
     * @param timeout   单位 秒
     */
    public void setString(String key, String value, Long timeout){
        if(null == timeout){
            redisTemplate.opsForValue().set(key, value);
        }else {
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 设置值并指定过期时间
     * @param key
     * @param value
     * @param timeout   单位 分
     */
    public void setStringByMinute(String key, String value, Long timeout){
        if(null == timeout){
            redisTemplate.opsForValue().set(key, value);
        }else {
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MINUTES);
        }
    }

    /**
     * 设置值并指定过期时间
     * @param key
     * @param value
     * @param timeout   单位 时
     */
    public void setStringByHour(String key, String value, Long timeout){
        if(null == timeout){
            redisTemplate.opsForValue().set(key, value);
        }else {
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.HOURS);
        }
    }

    /**
     * 设置值并指定过期时间
     * @param key
     * @param value
     * @param timeout   单位 天
     */
    public void setStringByDay(String key, String value, Long timeout){
        if(null == timeout){
            redisTemplate.opsForValue().set(key, value);
        }else {
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.DAYS);
        }
    }

    /**
     * 设置值并指定过期时间
     * @param key
     * @param value
     * @param timeout   单位 秒
     */
    public void setObject(String key, Object value, Long timeout){
        setString(key, JSON.toJSONString(value), timeout);
    }

    /**
     * 设置值并指定过期时间
     * @param key
     * @param value
     * @param timeout   单位 分
     */
    public void setObjectByMinute(String key, Object value, Long timeout){
        setStringByMinute(key, JSON.toJSONString(value), timeout);
    }

    /**
     * 设置值并指定过期时间
     * @param key
     * @param value
     * @param timeout   单位 时
     */
    public void setObjectByHour(String key, Object value, Long timeout){
        setStringByHour(key, JSON.toJSONString(value), timeout);
    }

    /**
     * 设置值并指定过期时间
     * @param key
     * @param value
     * @param timeout   单位 天
     */
    public void setObjectByDay(String key, Object value, Long timeout){
        setStringByDay(key, JSON.toJSONString(value), timeout);
    }

    /**
     * 添加数据（Redis Set类型）至Redis
     * @param key
     * @param elements
     */
    public void addSetData(String key, Object... elements){
        Set<Object> set = new HashSet<>(Arrays.asList(elements));
        addSetData(key, set);
    }

    /**
     * 添加数据：Redis Set类型
     * @param key
     * @param elements
     */
    public void addSetData(String key, Set<Object> elements){
        SetOperations<String, String> set = redisTemplate.opsForSet();
        // 遍历
        for (Object element : elements) {
            String str = JSON.toJSONString(element);
            // 向redis中写set类型数据
            set.add(key, str);
        }
    }

    /**
     * 获取数据：Redis Set类型
     * @param key
     * @param clazz
     * @return
     */
    public <T> Set<T> getSetData(String key, Class<T> clazz){
        SetOperations<String, String> set = redisTemplate.opsForSet();
        // 获取set类型数据
        Set<String> strSet = set.members(key);
        if(null == strSet || strSet.isEmpty()){
            return Collections.EMPTY_SET;
        }
        Set<T> result = new HashSet<>(strSet.size());

        // 遍历
        for (String str : strSet) {
            // 转化为指定类型
            T obj = JSON.parseObject(str, clazz);
            result.add(obj);
        }

        return result;
    }


    /**
     * 添加数据（Redis SortSet类型）至Redis
     * @param key
     * @param element
     * @param score 权重，用于排序的值
     */
    public void addSortSetData(String key, Object element, double score){
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        String str = JSON.toJSONString(element);
        zset.add(key, str, score);
    }

    /**
     * 获取所有数据：Redis SortSet类型
     * @param key
     * @param clazz
     * @return
     */
    public <T> List<T> getSortSetData(String key, Class<T> clazz){
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        // 获取set类型数据
        Set<String> strSet = zset.reverseRange(key, 0, zset.size(key));
        if(null == strSet || strSet.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        List<T> result = new ArrayList<>(strSet.size());
        // 遍历
        for (String str : strSet) {
            // 转化为指定类型
            T obj = JSON.parseObject(str, clazz);
            result.add(obj);
        }
        return result;
    }

    /**
     * 获取分页数据：Redis SortSet类型
     * @param key
     * @param clazz
     * @param pageNum
     * @param pageSize
     * @param <T>
     * @return
     */
    public <T> PageInfo<T> pageSortSetData(String key, Class<T> clazz, int pageNum, int pageSize){
        List<T> result = new ArrayList<>();
        if(pageNum < 0){
            pageNum = 1;
        }
        if(pageSize < 0){
            pageSize = 10;
        }
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        Long size = zset.size(key);
        long start = (pageNum - 1) * pageSize;
        long end = pageNum * pageSize;
        if(end > size){
            end = size;
        }
        if(size != 0 && start < size){
            // 获取set类型数据
            Set<String> strSet = zset.reverseRange(key, start, end-1);
            // 遍历
            for (String str : strSet) {
                // 转化为指定类型
                T obj = JSON.parseObject(str, clazz);
                result.add(obj);
            }
        }
        PageInfo<T> page = PageInfo.of(result);
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.setTotal(size);
        long pages = size / pageSize;
        if(size % pageSize != 0){
            pages += 1;
        }
        page.setPages((int)pages);
        return page;
    }

    /**
     * 添加数据：Redis Map类型
     * @param redisKey
     * @param mapKey
     * @param mapValue
     */
    public void addMapData(String redisKey, String mapKey, Object mapValue){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        String str = JSON.toJSONString(mapValue);
        hash.put(redisKey, mapKey, str);
    }

    /**
     * 添加数据：Redis Map类型
     * @param key
     * @param map
     */
    public void addMapData(String key, Map<String, Object> map){
        // 为空直接返回
        if(map.isEmpty()){
            return;
        }
        // 转化为<String, String>
        Map<String, String> rMap = new HashMap<>(map.size());
        map.forEach((k, v) -> {
            String str = JSON.toJSONString(v);
            rMap.put(k, str);
        });
        // redis map ops
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.putAll(key, rMap);
    }

    /**
     * 获取数据：Redis Map类型
     * @param redisKey
     * @param mapKey
     * @param clazz
     * @return
     */
    public <T> T getMapData(String redisKey, String mapKey, Class<T> clazz){
        if(!hasKey(redisKey)){
            return null;
        }

        // redis map ops
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();

        // 获取hash
        Object obj = hash.get(redisKey, mapKey);
        if(Objects.isNull(obj)){
            return null;
        }
        String str = obj.toString();
        return JSON.parseObject(str, clazz);
    }

    /**
     * 获取数据：Redis Map类型
     * @param redisKey
     * @param clazz
     * @return
     */
    public <T> Map<String, T> getMapData(String redisKey, Class<T> clazz){
        if(!hasKey(redisKey)){
            return Collections.EMPTY_MAP;
        }
        // redis map ops
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        Map<Object, Object> es = hash.entries(redisKey);

        if(null == es || es.isEmpty()){
            return Collections.EMPTY_MAP;
        }

        Map<String, T> map = new HashMap<>(es.size());
        es.forEach((k, v) -> {
            T obj = JSON.parseObject(v.toString(), clazz);
            map.put(k.toString(), obj);
        });

        return map;
    }

    /**
     * 左入队：Redis List类型（队列）
     * @param redisKey
     * @param values
     */
    public void leftPushData(String redisKey, Object... values){
        // 为空直接返回
        if(Objects.isNull(values)){
            return;
        }
        // 转化为List<String>
        List<String> sll = new ArrayList<>();
        for(Object o : values){
            String str = JSON.toJSONString(o);
            sll.add(str);
        }

        // redis list ops
        ListOperations<String, String> list = redisTemplate.opsForList();
        list.leftPushAll(redisKey, sll);
    }

    /**
     * 右入队：Redis List类型（队列）
     * @param redisKey
     * @param values
     */
    public void rightPushData(String redisKey, Object... values){
        // 为空直接返回
        if(Objects.isNull(values)){
            return;
        }
        // 转化为List<String>
        List<String> srl = new ArrayList<>();
        for(Object o : values){
            String str = JSON.toJSONString(o);
            srl.add(str);
        }

        // redis list ops
        ListOperations<String, String> list = redisTemplate.opsForList();
        list.rightPushAll(redisKey, srl);
    }

    /**
     * 左出队：Redis List类型（队列）
     * @param redisKey
     * @param clazz
     * @return
     */
    public <T> T leftPopData(String redisKey, Class<T> clazz){

        // redis list ops
        ListOperations<String, String> list = redisTemplate.opsForList();
        String str = list.leftPop(redisKey);
        T t = JSON.parseObject(str, clazz);
        return t;
    }

    /**
     * 右出队：Redis List类型（队列）
     * @param redisKey
     * @param clazz
     * @return
     */
    public <T> T rightPopData(String redisKey, Class<T> clazz){

        // redis list ops
        ListOperations<String, String> list = redisTemplate.opsForList();
        String str = list.rightPop(redisKey);
        T t = JSON.parseObject(str, clazz);
        return t;
    }

}
