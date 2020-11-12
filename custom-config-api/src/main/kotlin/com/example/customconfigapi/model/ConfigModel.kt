package com.example.customconfigapi.model;
import java.io.Serializable
class ConfigModel(code:String, configuration:String) : Serializable{
    val code = code;
    val configuration = configuration
}
