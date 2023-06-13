package com.example.releases.services;

import com.example.releases.config.ReleasesApiProperties;
import com.example.releases.domain.Release;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class ReleasesApiService {
    ReleasesApiProperties releasesApiProperties;
    ObjectMapper mapper;
    public List<Release> fetchReleases() {
        var restTemplate = new RestTemplate();
        var response = restTemplate.getForEntity(
                releasesApiProperties.getApiUrl(),
                String.class);
        List<Release> releases = null;
        try {
            var jsonNodes = mapper.readTree(response.getBody());
            releases = StreamSupport.
                    stream(jsonNodes.spliterator(), false)
                    .map(node -> new Release(
                            node.get("tag_name").asText(),
                            OffsetDateTime.parse(node.get("created_at").asText()),
                            node.get("prerelease").asBoolean())).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return releases;
    }
}