package com.comics.comic.service;


import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CacheInspectionService {

    private final CacheManager cacheManager;

    public String printCacheContent(String cacheName){
        Cache cache = cacheManager.getCache(cacheName);
        System.out.println("Cache contents");
        return Objects.requireNonNull(cache.getNativeCache()).toString();
    }

}
