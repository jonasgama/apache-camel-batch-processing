package com.example.customconfigapi.service

import com.example.customconfigapi.model.ConfigModel
import com.example.customconfigapi.repository.ConfigRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class ConfigService {

    @Autowired
    private lateinit var repository: ConfigRepository

    private val logger = LoggerFactory.getLogger(javaClass)

    @Cacheable(cacheNames= ["configs"], key="#searchTerm")
    fun search(searchTerm: String, page: Int,size: Int): MutableList<ConfigModel> {
        logger.info("Retrieving from db the config ${searchTerm}")
        val pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "code")
        return repository.searchByCodeLike(searchTerm.toLowerCase(), pageRequest)
                .map { config -> ConfigModel(config.code, config.configuration) }
                .content
    }
}

