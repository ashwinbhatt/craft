package com.ashwinbhatt.craft.orgmanager.redisautoexpcacheservicetests;

import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class MockRedisTemplate<K, V>  extends RedisTemplate{

    private final Map<K, V> inMemMap;

    public MockRedisTemplate() {
        this.inMemMap = new HashMap<>();
    }

    public void addKey(K key, V value) {
        this.inMemMap.put(key, value);
    }

    @Override
    public Boolean hasKey(Object key) {
        return inMemMap.containsKey(key);
    }

    @Override
    public Set<K> keys(Object pattern) {
        // Todo: add logic to actually filter pattern in all keys.
        return inMemMap.keySet();
    }

    @Override
    public Boolean expire(Object key, final long timeout, final TimeUnit unit) {
        // functionality usage is to expire the key. This logic directly does that.
        if(inMemMap.containsKey(key)) {
            inMemMap.remove(key);
            return true;
        }
        return false;
    }

    @Override
    public ValueOperations<K, V> opsForValue() {
        return new ValueOperations<K, V>() {
            @Override
            public void set(K key, V value) {
                inMemMap.put(key, value);
            }

            @Override
            public void set(K key, V value, long timeout, TimeUnit unit) {

            }

            @Override
            public Boolean setIfAbsent(K key, V value) {
                return null;
            }

            @Override
            public Boolean setIfAbsent(K key, V value, long timeout, TimeUnit unit) {
                return null;
            }

            @Override
            public Boolean setIfPresent(K key, V value) {
                return null;
            }

            @Override
            public Boolean setIfPresent(K key, V value, long timeout, TimeUnit unit) {
                return null;
            }

            @Override
            public void multiSet(Map<? extends K, ? extends V> map) {

            }

            @Override
            public Boolean multiSetIfAbsent(Map<? extends K, ? extends V> map) {
                return null;
            }

            @Override
            public V get(Object key) {
                return inMemMap.get(key);
            }

            @Override
            public V getAndDelete(K key) {
                return null;
            }

            @Override
            public V getAndExpire(K key, long timeout, TimeUnit unit) {
                return null;
            }

            @Override
            public V getAndExpire(K key, Duration timeout) {
                return null;
            }

            @Override
            public V getAndPersist(K key) {
                return null;
            }

            @Override
            public V getAndSet(K key, V value) {
                return null;
            }

            @Override
            public List<V> multiGet(Collection<K> keys) {
                return null;
            }

            @Override
            public Long increment(K key) {
                return null;
            }

            @Override
            public Long increment(K key, long delta) {
                return null;
            }

            @Override
            public Double increment(K key, double delta) {
                return null;
            }

            @Override
            public Long decrement(K key) {
                return null;
            }

            @Override
            public Long decrement(K key, long delta) {
                return null;
            }

            @Override
            public Integer append(K key, String value) {
                return null;
            }

            @Override
            public String get(K key, long start, long end) {
                return null;
            }

            @Override
            public void set(K key, V value, long offset) {

            }

            @Override
            public Long size(K key) {
                return null;
            }

            @Override
            public Boolean setBit(K key, long offset, boolean value) {
                return null;
            }

            @Override
            public Boolean getBit(K key, long offset) {
                return null;
            }

            @Override
            public List<Long> bitField(K key, BitFieldSubCommands subCommands) {
                return null;
            }

            @Override
            public RedisOperations<K, V> getOperations() {
                return null;
            }
        };
    }

}
