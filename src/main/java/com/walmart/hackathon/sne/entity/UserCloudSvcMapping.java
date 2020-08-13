package com.walmart.hackathon.sne.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class UserCloudSvcMapping {

    @Id
    @GeneratedValue
    private int id;
    private String userId;
    private String cloudSvc;
}
