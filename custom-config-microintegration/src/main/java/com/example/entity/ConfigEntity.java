package com.example.entity;

@Entity
public class ConfigEntity {

    @Id
    private String code;

    @Column(length=30)
    private String configuration;

    public ConfigEntity(String code, String configuration) {
        this.code = code;
        this.configuration = configuration;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }
}
