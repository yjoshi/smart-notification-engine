package com.walmart.hackathon.sne.service;

import org.apache.http.*;
import org.springframework.boot.web.client.*;
import org.springframework.http.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
public class ZoomService {

    private final RestTemplate restTemplate;

    public ZoomService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void postToZoom(String zoomUrl, String zoomToken, Map<String, Object> payload) {


        HttpHeaders httpHeaders = new HttpHeaders();
        // set `content-type` header
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", zoomToken);

        payload.put("test", "test");
        // build the request
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, httpHeaders);

        ResponseEntity<String> response = this.restTemplate.exchange(zoomUrl, HttpMethod.POST, entity, String.class);
        System.out.println(response);

    }
}
