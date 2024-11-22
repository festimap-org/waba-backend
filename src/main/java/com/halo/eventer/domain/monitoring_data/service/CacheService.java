package com.halo.eventer.domain.monitoring_data.service;

import com.halo.eventer.domain.monitoring_data.MonitoringData;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    @Cacheable(value = "lastVisitorCount", key = "#festivalId")
    public int getLastVisitorCache(Long festivalId, String timestamp) {
        return 0;
    }

    @CachePut(value = "lastVisitorCount", key = "#festivalId")
    public Integer updateLastVisitorCache(Long festivalId, int currentVisitors) { return currentVisitors; }

    @Cacheable(value = "maxCapacity", key = "#festivalId")
    public int getMaxCapacityCache(Long festivalId, MonitoringData monitoringData) { return monitoringData.getMaxCapacity(); }

    @CachePut(value = "maxCapacity", key = "#festivalId")
    public int updateMaxCapacityCache(Long festivalId, int maxCapacity) {
        return maxCapacity;
    }
}
