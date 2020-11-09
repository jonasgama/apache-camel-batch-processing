package com.example.customconfigapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CustomConfigApiApplication

fun main(args: Array<String>) {
	runApplication<CustomConfigApiApplication>(*args)
}
