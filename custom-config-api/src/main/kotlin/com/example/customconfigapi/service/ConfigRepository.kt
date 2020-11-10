package com.example.customconfigapi.service

import com.example.customconfigapi.entity.ConfigEntity
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ConfigRepository : JpaRepository<ConfigEntity, String> {

    @Query("SELECT c FROM ConfigEntity c WHERE c.code LIKE %:searchTerm%")
    fun searchByCodeLike(@Param("searchTerm") searchTerm: String ,pageable: Pageable): Page<ConfigEntity>
}

