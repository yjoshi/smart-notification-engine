package com.walmart.hackathon.sne.controller;

import com.azure.core.annotation.*;
import com.walmart.hackathon.sne.model.*;
import com.walmart.hackathon.sne.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/sne")
public class SNEController {

    @Autowired
    private SNEService sneService;

    // Register endpoint
    @PostMapping("/register")
    private String registerSNE(@RequestBody RegistrationRequest registrationRequest) {

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
    // Endpoint to RU
    @GetMapping("/ru")
    private Integer getRU(@RequestParam(required = true) String userId,@RequestParam(required = true)  String account) {
        sneService.getRU(userId, account);
        return 0;
    }


    // Endpoint to get Appinsight logs
    @GetMapping("/appInsight")
    private AppInsight getAppInsights(@RequestParam(required = true) String userId,@RequestParam(required = true)  String account) {
        return sneService.getAppInsight(userId, account);
    }
    // Endpoint to get cost details
    @GetMapping("/cost")
    private Double getCostDetails(@RequestParam(required = true) String userId,@RequestParam(required = true)  String account,
            @QueryParam("startDate") Date start, @QueryParam("startDate") Date end) {
        return sneService.getCost(userId, account, start, end);
    }
}
