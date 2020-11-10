package com.example.customconfigapi.rest

import com.example.customconfigapi.service.ConfigService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ConfigController{

    @Autowired private lateinit var service: ConfigService

    @GetMapping("/v1/configs")
    fun get(@RequestParam(value="code", required=false, defaultValue = "") code: String,
            @RequestParam(value="page", required=false, defaultValue = "0") page: Int,
            @RequestParam(value="size", required=false, defaultValue = "10") size: Int
                ) = service.search(code, page, size);
    }

