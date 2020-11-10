package com.example.customconfigapi.service

import com.example.customconfigapi.entity.ConfigEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ConfigService {

    @Autowired
    private lateinit var repository: ConfigRepository

    fun search(searchTerm: String, page: Int,size: Int): MutableList<ConfigModel> {
        val pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "code")
        return repository.searchByCodeLike(searchTerm.toLowerCase(), pageRequest)
                .map { config -> ConfigModel(config.code, config.configuration) }
                .content
    }
}

