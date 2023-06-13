package com.example.releases.controllers;

import com.example.releases.ReleasesApplication;
import com.example.releases.domain.Release;
import com.example.releases.config.ReleasesApiProperties;
import com.example.releases.services.CacheService;
import com.example.releases.services.ReleaseLookupService;
import com.example.releases.services.ReleasesApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path="/releases")
@AllArgsConstructor
@CrossOrigin
public class ReleasesController {
    private final CacheService cacheService;
    private final ReleaseLookupService releaseLookupService;
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @CachePut(
            value = "filteredReleases",
            key = "'latest'", condition =
            "(#createdBefore != null && !#createdBefore.isBlank() && !#createdBefore.isEmpty()) " +
                    "|| (#prerelease != null && !#prerelease.isBlank() && !#prerelease.isEmpty())"
    )
    public ResponseEntity<List<Release>> getReleases(
            @RequestParam(name = "createdBefore", required = false) String createdBefore,
            @RequestParam(name = "prerelease", required = false) String prerelease
            ) {
        var releases = releaseLookupService.findReleasesBy(createdBefore, prerelease);
        return ResponseEntity.ok(releases);
    }
    @GetMapping(path = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<Release>> getReleasesAsXML () {
        return cacheService.getCachedReleasesResponse("latest");
    }
}