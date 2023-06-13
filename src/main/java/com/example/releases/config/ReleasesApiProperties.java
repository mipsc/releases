package com.example.releases.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "releases")
@Data
public class ReleasesApiProperties {
    private String apiUrl;
}