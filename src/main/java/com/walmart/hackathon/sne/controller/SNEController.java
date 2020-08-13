package com.walmart.hackathon.sne.controller;

import com.azure.core.annotation.*;
import com.walmart.hackathon.sne.entity.*;
import com.walmart.hackathon.sne.model.*;
import com.walmart.hackathon.sne.service.*;
import com.walmart.hackathon.sne.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    @PostMapping("/register")
    private String registerSNE(@RequestBody UserMappingWithSne registrationRequest) {

        // Request should contain
        // user
        // account user belongs to
        // what all he wants to have access to
        // Zoom endpoint
        // Zoom verification token
        if(sneService.register(registrationRequest)) {
            return "Registered successfully";
        } else {
            return "Failed to register";
        }

    }

    @GetMapping("/userId/{userId}/type/{type}")
    private SNEResponse getCloudDetails(@PathVariable String userId, @PathVariable String type, @QueryParam("startDate") Date start, @QueryParam("startDate") Date end) {
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

}
