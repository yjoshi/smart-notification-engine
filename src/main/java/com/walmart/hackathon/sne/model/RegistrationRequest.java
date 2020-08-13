package com.walmart.hackathon.sne.model;

import lombok.*;

import java.util.*;

@Getter
@Setter
public class RegistrationRequest {

    private String userId;
    private String accountInfo;
    private List<String> functions;
    private String zoomEndpoint;
    private String zoomVerificationToken;
}
