package com.walmart.hackathon.sne.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
public class UserMappingWithSne {

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

}
