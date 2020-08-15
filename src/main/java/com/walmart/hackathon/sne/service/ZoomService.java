package com.walmart.hackathon.sne.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walmart.hackathon.sne.model.SlackMessage;
import org.apache.http.*;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.web.client.*;
import org.springframework.http.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
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

    public void pushToZoom_2(String zoomUrl, String zoomToken) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(zoomUrl);
//        SlackMessage slackMessage2 = SlackMessage.builder()
//                .username("Fintech Alert")
//                .text("Your app service stopped working")
//                .build();
        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            String json = objectMapper.writeValueAsString(slackMessage2);
            StringEntity entity = new StringEntity("test message");
            httpPost.setEntity(entity);
//            httpPost.setHeader("Accept", "application/json");
//            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Authorization", zoomToken);
            client.execute(httpPost);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void postToSlack(String slackURL) {
        CloseableHttpClient client = HttpClients.createDefault();
//        HttpPost httpPost = new HttpPost("https://hooks.slack.com/services/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");
        HttpPost httpPost = new HttpPost(slackURL);
        SlackMessage slackMessage2 = SlackMessage.builder()
                .username("Fintech Alert")
                .text("Your app service stopped working")
                .build();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(slackMessage2);
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            client.execute(httpPost);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void postToSlack_2(String slackURL) {
        CloseableHttpClient client = HttpClients.createDefault();
//        HttpPost httpPost = new HttpPost("https://hooks.slack.com/services/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");
        HttpPost httpPost = new HttpPost(slackURL);
        SlackMessage slackMessage2 = SlackMessage.builder()
                .username("Fintech Alert")
                .text("Your app service started working")
                .build();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(slackMessage2);
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            client.execute(httpPost);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void pushToZoomWithMessage(String zoomUrl, String zoomToken,String message) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(zoomUrl);
//        SlackMessage slackMessage2 = SlackMessage.builder()
//                .username("Fintech Alert")
//                .text("Your app service stopped working")
//                .build();
        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            String json = objectMapper.writeValueAsString(slackMessage2);
            StringEntity entity = new StringEntity(message);
            httpPost.setEntity(entity);
//            httpPost.setHeader("Accept", "application/json");
//            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Authorization", zoomToken);
            client.execute(httpPost);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void postToSlackWithMessage(String slackURL,String message) {
        CloseableHttpClient client = HttpClients.createDefault();
//        HttpPost httpPost = new HttpPost("https://hooks.slack.com/services/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");
        HttpPost httpPost = new HttpPost(slackURL);
        SlackMessage slackMessage2 = SlackMessage.builder()
                .username("Fintech Alert")
                .text(message)
                .build();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(slackMessage2);
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            client.execute(httpPost);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
