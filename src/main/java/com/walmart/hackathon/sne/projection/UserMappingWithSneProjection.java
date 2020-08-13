package com.walmart.hackathon.sne.projection;

import lombok.*;

public interface UserMappingWithSneProjection {

    public String getAccount();
    public String getAppInsightName();
    public String getCloudSvc();
    public String getCosmosDbName();
    public String getFunction();
    public String getGroupName();
    public String getSubscription();
    public String getUserId();
    public String getZoomEndpoint();
    public String getZoomVerificationToken();

}
