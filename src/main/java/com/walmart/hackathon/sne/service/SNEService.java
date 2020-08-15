package com.walmart.hackathon.sne.service;

import com.walmart.hackathon.sne.entity.UserMappingWithSneEntity;
import com.walmart.hackathon.sne.model.SNEResponse;
import com.walmart.hackathon.sne.model.UserMappingWithSne;
import com.walmart.hackathon.sne.projection.UserMappingWithSneProjection;
import com.walmart.hackathon.sne.repository.CosmosRepository;
import com.walmart.hackathon.sne.repository.UserMappingWithSneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SNEService {

    @Autowired
    UserMappingWithSneRepository userMappingWithSneRepository;

    @Autowired
    private CosmosRepository cosmosRepository;

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
        entity.setSlackURL(registrationRequest.getSlackURL());
        userMappingWithSneRepository.save(entity);

        return true;
    }

    public SNEResponse getRU(String userId) {

        // Get details of function from H2 based on user and accound details
        // TO-DO
        UserMappingWithSneProjection userMappingByUserId = userMappingWithSneRepository.getUserMappingByUserId(userId);
        System.out.println(userMappingByUserId.getUserId() + " " + userMappingByUserId.getAccount() + " " + userMappingByUserId.getSubscription());
        // Call Azure Function to get details of RU
        int ru = cosmosRepository.getRU();
        SNEResponse sneResponse = new SNEResponse();
        sneResponse.setType("RU for Cosmos DB : " + userMappingByUserId.getCosmosDbName());
        sneResponse.setData("provisioned RU : " + ru);
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
//        UserMappingWithSneProjection userMappingByUserId = userMappingWithSneRepository.getUserMappingByUserId(userId);
//        zoomService.postToZoom(userMappingByUserId.getZoomEndpoint(), userMappingByUserId.getZoomVerificationToken(), new HashMap<>());

        UserMappingWithSneEntity userMappingWithSneEntity = userMappingWithSneRepository.getUserDetails(userId);
        zoomService.pushToZoom_2(userMappingWithSneEntity.getZoomEndpoint(), userMappingWithSneEntity.getZoomVerificationToken());
        zoomService.postToSlack(userMappingWithSneEntity.getSlackURL());
//        zoomService.postToSlack_2();
    }


    public void sendNotification(String cosmosName, String alertName, int newTH) {
        String message = "Got alert for cosmos : " + cosmosName + " and alert name : " + alertName + "with new RU : " + newTH;
        List<UserMappingWithSneEntity> userMappingWithSneEntityList = userMappingWithSneRepository.getUserDetailsForCosmos(cosmosName);
        for (UserMappingWithSneEntity userMappingWithSneEntity : userMappingWithSneEntityList) {
            message = "Dear " + userMappingWithSneEntity.getUserId() + " :  \n " + message;
            if (userMappingWithSneEntity.getZoomEndpoint() != null)
            zoomService.pushToZoomWithMessage(userMappingWithSneEntity.getZoomEndpoint(), userMappingWithSneEntity.getZoomVerificationToken(),message);
            if (userMappingWithSneEntity.getSlackURL() != null) {
                zoomService.postToSlackWithMessage(userMappingWithSneEntity.getSlackURL(), message);
            }
        }
    }
}
