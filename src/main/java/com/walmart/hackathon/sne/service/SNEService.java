package com.walmart.hackathon.sne.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.walmart.hackathon.sne.entity.UserMappingWithSneEntity;
import com.walmart.hackathon.sne.model.SNEResponse;
import com.walmart.hackathon.sne.model.UserMappingWithSne;
import com.walmart.hackathon.sne.projection.UserMappingWithSneProjection;
import com.walmart.hackathon.sne.repository.CosmosRepository;
import com.walmart.hackathon.sne.repository.UserMappingWithSneRepository;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    public boolean registerChannel(UserMappingWithSne registrationRequest) {

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
    public boolean registerMonitor(UserMappingWithSne registrationRequest) {

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
        entity.setAppInsightId(registrationRequest.getAppInsightId());
        entity.setAppInsightKey(registrationRequest.getAppInsightKey());
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

    public SNEResponse getAppInsight(String userId) throws IOException {
        // Get details of function from H2 based on user and accound details
        // Call Azure Function to get details of AppInsight
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet("https://api.applicationinsights.io/v1/apps/6aa06d33-951c-4a68-a6a8-aceb05beb3e1/metrics/requests/count");
        request.setHeader("x-api-key","7er83cf3nys4tefkpc2nqxid6042yu56tgsr5wvr");
        HttpResponse response1= client.execute(request);
        int responseCode = response1.getStatusLine().getStatusCode();
        System.out.println("GET Response Code :: " +responseCode);
        BufferedReader in = new BufferedReader(new InputStreamReader((response1.getEntity().getContent())));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        ObjectMapper mapper =new ObjectMapper();
        JsonNode node=null;
        node=mapper.readTree(response.toString());
        String count =node.get("value").get("requests/count").get("sum").toString();
        SNEResponse sneResponse = new SNEResponse();
        sneResponse.setData("Your App service is failing with " +count +" number of failure requests!");
        sneResponse.setType("App Insight");
        return sneResponse;
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
        String cloudCostDetails = null;
        try {
            cloudCostDetails = new CloudCostAnalyzerService().getCloudCostDetails();
            String ruDetails = String.valueOf(cosmosRepository.getRU());
            String messagePushForZoom = "Dear " + userMappingWithSneEntity.getUserId() + " : \n This is Smart Notification:\n\tCloud Cost Deatils:\n\t\t"+cloudCostDetails+
                    "\n\tCosmos RU Details:\n\t\t"+ruDetails;
            if (userMappingWithSneEntity.getZoomEndpoint() != null)
                zoomService.pushToZoom_2(userMappingWithSneEntity.getZoomEndpoint(), userMappingWithSneEntity.getZoomVerificationToken(),messagePushForZoom);
            if (userMappingWithSneEntity.getSlackURL() != null)
                zoomService.postToSlackWithMessage(userMappingWithSneEntity.getSlackURL(),messagePushForZoom);
//          zoomService.postToSlack_2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sendNotification(String cosmosName, String alertName, int newTH) {
        String message = "Got alert for cosmos : " + cosmosName + " and alert name : " + alertName + " with new RU : " + newTH;
        List<UserMappingWithSneEntity> userMappingWithSneEntityList = userMappingWithSneRepository.getUserDetailsForCosmos(cosmosName);
        for (UserMappingWithSneEntity userMappingWithSneEntity : userMappingWithSneEntityList) {
            String NotificationMessage = "Dear " + userMappingWithSneEntity.getUserId() + " :  \n " + message;
            if (userMappingWithSneEntity.getZoomEndpoint() != null)
            zoomService.pushToZoomWithMessage(userMappingWithSneEntity.getZoomEndpoint(), userMappingWithSneEntity.getZoomVerificationToken(),NotificationMessage);
            if (userMappingWithSneEntity.getSlackURL() != null) {
                zoomService.postToSlackWithMessage(userMappingWithSneEntity.getSlackURL(), NotificationMessage);
            }
        }
    }
}
