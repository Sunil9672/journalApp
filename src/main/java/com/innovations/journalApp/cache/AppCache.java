package com.innovations.journalApp.cache;

import com.innovations.journalApp.entity.AppCacheEntity;
import com.innovations.journalApp.repository.AppCacheRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Getter
@Setter
public class AppCache {
    public enum Keys {
        quote_api;
    }

    private Map<String,String> cache;

    @Autowired
    private AppCacheRepository appCacheRepository;

    @PostConstruct
    public void initCache() {
        List<AppCacheEntity> cacheList = appCacheRepository.findAll();
        
        // fill cache using cacheList
        cache = new HashMap<>();
        cacheList.forEach(x -> cache.put(x.getKey(), x.getValue()));
        
        
    }
}
