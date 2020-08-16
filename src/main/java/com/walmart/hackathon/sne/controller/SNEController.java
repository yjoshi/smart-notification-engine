package com.walmart.hackathon.sne.controller;

import com.azure.core.annotation.QueryParam;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.walmart.hackathon.sne.model.SNEResponse;
import com.walmart.hackathon.sne.model.UserMappingWithSne;
import com.walmart.hackathon.sne.service.SNEService;
import com.walmart.hackathon.sne.util.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/sne")

// TO-DO - Explore pull data through SDK's
// TO-DO - Rest call  to functions whenever there is alerts
// TO-DO - SNE to Zoom call whenever there is an alert
// TO-DO - Write Alerts and logic apps and functions in Azure
public class SNEController {

    @Autowired
    private SNEService sneService;

    // Register endpoint
    @PostMapping("/register/channels")
    private String registerSNEChannel(@RequestBody UserMappingWithSne registrationRequest) {

        // Request should contain
        // user
        // account user belongs to
        // what all he wants to have access to
        // Zoom endpoint
        // Zoom verification token
        if(sneService.registerChannel(registrationRequest)) {
            return "Registered successfully";
        } else {
            return "Failed to register";
        }

    }
    @PostMapping("/register/monitors")
    private String registerSNEMonitoring(@RequestBody UserMappingWithSne registrationRequest) {

        // Request should contain
        // user
        // account user belongs to
        // what all he wants to have access to
        // Zoom endpoint
        // Zoom verification token
        if(sneService.registerMonitor(registrationRequest)) {
            return "Registration on monitoring done successfully";
        } else {
            return "Failed to register";
        }

    }

    @GetMapping("/userId/{userId}/type/{type}")
    private SNEResponse getCloudDetails(@PathVariable String userId, @PathVariable String type, @QueryParam("startDate") Date start, @QueryParam("startDate") Date end) throws IOException {
        SNEResponse sneResponse = new SNEResponse();
        switch (type) {
            case ApplicationConstants.RU:
                sneResponse = sneService.getRU(userId);
                break;
            case ApplicationConstants.AI:
                sneResponse = sneService.getAppInsight(userId);
                break;
            case ApplicationConstants.COST:
                sneResponse = sneService.getCost(userId, start, end);
                break;
            default:
                System.out.println("Error");
        }
        return sneResponse;
    }

    @GetMapping("/notify/{userId}")
    public void notify (@PathVariable String userId) {
        System.out.println(userId);
        sneService.callZoomService(userId);
    }

    @PostMapping("/alert")
    public void alert(@RequestBody String payload) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(payload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }
        String alertName = node.get("data").get("context").get("name").asText();
        String cosmosName = node.get("data").get("context").get("resourceName").asText();
        int newTH = node.get("data").get("context").get("condition").get("allOf").get(0).get("metricValue").asInt();
        System.out.println(newTH);
        sneService.sendNotification(cosmosName,alertName,newTH);
    }
}
