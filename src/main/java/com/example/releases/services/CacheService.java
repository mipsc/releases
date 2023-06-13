package com.example.releases.services;

import com.example.releases.config.CacheProperties;
import com.example.releases.domain.Release;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CacheService {
    CacheManager cacheManager;
    CacheProperties cacheProperties;
    public ResponseEntity getCachedReleasesResponse(String key) {
        var cache = cacheManager.getCache(cacheProperties.getFilteredReleasesName());
        var wrapper = cache != null ? cache.get(key) : null;
        var cached = wrapper != null ? wrapper.get() : null;
        var value = cached != null ? (ResponseEntity<List<Release>>)cached : ResponseEntity.ok(List.of());
        return value;
    }
}