package com.example.file_convertor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "files")
@Configuration("fileProperties")
@Data
public class TestConfig {

    private String baseUri;

}
