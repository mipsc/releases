package com.example.releases;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

@RestController
@RequestMapping(path="/", produces = "application/json")
public class ReleasesController {


    @GetMapping(value = "releases")
    public ResponseEntity<Object[]> getReleases(@RequestParam(value = "createdBefore") String createdBefore)
            throws URISyntaxException, IOException, InterruptedException {

        var restTemplate = new RestTemplate();
        var response = restTemplate.getForEntity(
                "https://api.github.com/repos/facebook/react/releases",
                Object[].class);
        return new ResponseEntity<>(Arrays.stream(response.getBody()).filter(o -> {
            return o.createdAt == "dw";
        }), HttpStatus.OK);
    }
}
