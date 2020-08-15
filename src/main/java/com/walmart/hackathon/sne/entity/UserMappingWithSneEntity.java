package com.walmart.hackathon.sne.entity;

import com.walmart.hackathon.sne.repository.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class UserMappingWithSneEntity {
    @Id
    @GeneratedValue
    private int id;
    private String userId;
    private String account;
    private String function;
    private String zoomEndpoint;
    private String zoomVerificationToken;
    private String cosmosDbName;
    private String appInsightName;
    private String subscription;
    private String groupName;
    private String cloudSvc;
    private String slackURL;
    private String appInsightId;
    private String appInsightKey;

    public UserMappingWithSneEntity(){

    }

    public UserMappingWithSneEntity(int id, String userId, String account, String function, String zoomEndpoint, String zoomVerificationToken) {

    }
}
