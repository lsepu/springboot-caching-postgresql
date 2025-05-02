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

    public void printCacheContent(String cacheName){
        Cache cache = cacheManager.getCache(cacheName);
        if(cache!=null){
            System.out.println("Cache contents");
            System.out.println(Objects.requireNonNull(cache.getNativeCache()));
        }else{
            System.out.println("No cache: " + cacheName);
        }
    }

}
