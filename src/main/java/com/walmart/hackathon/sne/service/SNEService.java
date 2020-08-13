package com.walmart.hackathon.sne.service;

import com.fasterxml.jackson.databind.*;
import com.walmart.hackathon.sne.entity.*;
import com.walmart.hackathon.sne.model.*;
import com.walmart.hackathon.sne.projection.*;
import com.walmart.hackathon.sne.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class SNEService {

    @Autowired
    UserMappingWithSneRepository userMappingWithSneRepository;

    @Autowired
    private ZoomService zoomService;
    public boolean register(UserMappingWithSne registrationRequest) {

        // Integrate with H2.
        UserMappingWithSneEntity entity = new UserMappingWithSneEntity();
        entity.setAccount(registrationRequest.getAccount());
        entity.setAppInsightName(registrationRequest.getAppInsightName());
        entity.setCloudSvc(registrationRequest.getCloudSvc());
        entity.setCosmosDbName(registrationRequest.getCosmosDbName());
        entity.setFunction(registrationRequest.getFunction());
        entity.setGroupName(registrationRequest.getGroupName());
        entity.setSubscription(registrationRequest.getSubscription());
        entity.setUserId(registrationRequest.getUserId());
        entity.setZoomEndpoint(registrationRequest.getZoomEndpoint());
        entity.setZoomVerificationToken(registrationRequest.getZoomVerificationToken());
        userMappingWithSneRepository.save(entity);

        return true;
    }

    public SNEResponse getRU(String userId) {

        // Get details of function from H2 based on user and accound details
        // TO-DO
        UserMappingWithSneProjection userMappingByUserId = userMappingWithSneRepository.getUserMappingByUserId(userId);
        System.out.println(userMappingByUserId.getUserId() + " " + userMappingByUserId.getAccount() + " " + userMappingByUserId.getSubscription());

        // Call Azure Function to get details of RU
        SNEResponse sneResponse = new SNEResponse();
        sneResponse.setData("<RU from Azure>");
        sneResponse.setType("RU");
        return sneResponse;
    }

    public SNEResponse getAppInsight(String userId) {
        // Get details of function from H2 based on user and accound details
        // Call Azure Function to get details of AppInsight

        return null;
    }

    public SNEResponse getCost(String userId, Date start, Date end) {
        // Get details of function from H2 based on user and accound details
        // Call Azure Function to get details of cost
        if(start == null || end == null) {
            start = new Date();
            end = new Date();
        }

        return null;
    }

    public void callZoomService(String userId) {
        UserMappingWithSneProjection userMappingByUserId = userMappingWithSneRepository.getUserMappingByUserId(userId);
        zoomService.postToZoom(userMappingByUserId.getZoomEndpoint(), userMappingByUserId.getZoomVerificationToken(), new HashMap<>());
    }
}
