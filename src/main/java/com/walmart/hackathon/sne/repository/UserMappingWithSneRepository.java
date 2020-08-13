package com.walmart.hackathon.sne.repository;

import com.walmart.hackathon.sne.entity.*;
import com.walmart.hackathon.sne.model.*;
import com.walmart.hackathon.sne.projection.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMappingWithSneRepository extends JpaRepository<UserMappingWithSneEntity, Integer> {

    @Query(value="SELECT ACCOUNT AS account, APP_INSIGHT_NAME AS appInsightName, CLOUD_SVC AS cloudSvc, COSMOS_DB_NAME AS cosmosDbName," +
            " FUNCTION AS function, SUBSCRIPTION as subscription, USER_ID AS userId, ZOOM_ENDPOINT AS zoomEndpoint, ZOOM_VERIFICATION_TOKEN AS zoomVerificationToken" +
            " FROM USER_MAPPING_WITH_SNE_ENTITY WHERE USER_ID IN (?1)", nativeQuery = true)
    UserMappingWithSneProjection getUserMappingByUserId(String userId);
}
