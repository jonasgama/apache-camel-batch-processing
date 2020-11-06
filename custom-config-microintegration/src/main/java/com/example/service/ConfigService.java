package com.example.service;

import com.example.entity.ConfigEntity;
import com.example.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {

    @Autowired
    private ConfigRepository repository;

    public void save(ConfigEntity entity){
        repository.saveAndFlush(entity);
    }
}
