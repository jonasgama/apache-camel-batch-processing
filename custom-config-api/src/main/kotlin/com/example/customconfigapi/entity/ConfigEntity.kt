package com.example.customconfigapi.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="config_entity")
class ConfigEntity {

    @Id
    lateinit var code: String
    lateinit var configuration: String
}