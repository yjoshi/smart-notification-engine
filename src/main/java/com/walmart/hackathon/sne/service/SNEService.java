package com.walmart.hackathon.sne.service;

import com.walmart.hackathon.sne.model.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.time.format.*;
import java.util.*;

@Service
public class SNEService {

    public boolean register(RegistrationRequest registrationRequest) {

        // Integrate with H2.
        return false;
    }

    public Integer getRU(String userId, String account) {

        // Get details of function from H2 based on user and accound details
        // TO-DO

        // Call Azure Function to get details of RU

        return 0;
    }

    public AppInsight getAppInsight(String userId, String account) {
        // Get details of function from H2 based on user and accound details
        // Call Azure Function to get details of AppInsight

        return new AppInsight();
    }

    public Double getCost(String userId, String account, Date start, Date end) {
        // Get details of function from H2 based on user and accound details
        // Call Azure Function to get details of cost
        if(start == null || end == null) {
            start = new Date();
            end = new Date();
        }

        return 0.0;
    }
}
