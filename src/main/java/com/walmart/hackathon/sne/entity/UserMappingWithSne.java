package com.walmart.hackathon.sne.entity;

import com.walmart.hackathon.sne.repository.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class UserMappingWithSne {
    @Id
    @GeneratedValue
    private int id;
    private String userId;
    private String account;
    private String function;
    private String zoomEndpoint;
    private String zoomVerificationToken;
    // cosmos
    // appinsight
    // subscription
    // resource groups

    public UserMappingWithSne(){

    }

    public UserMappingWithSne(int id, String userId, String account, String function, String zoomEndpoint, String zoomVerificationToken) {

    }
}
