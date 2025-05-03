package com.comics.comic.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CacheInspectionService {

    private final CacheManager cacheManager;

    public String printCacheContent(String cacheName){
        Cache cache = cacheManager.getCache(cacheName);
        log.info("Cache contents");
        return Objects.requireNonNull(cache.getNativeCache()).toString();
    }

}
