package com.walmart.hackathon.sne.repository;


import com.azure.cosmos.ConsistencyLevel;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.models.CosmosDatabaseResponse;
import com.azure.cosmos.models.ThroughputResponse;
import org.springframework.stereotype.Repository;

@Repository
public class CosmosRepository {

    private static final String HOST = "https://cosmos-sne-test.documents.azure.com:443/";
    private static final String MASTER_KEY = "fpGfIFECrwIeCcbXDtgV8jfPVFiZBcPlszfHlk5IglMGuLsYT5gJPxrUq754N2ltmDKBvaov7h3SioZuN3tirA==";

    public int getRU() {
        CosmosClient cosmosClient = new CosmosClientBuilder()
                .endpoint(HOST)
                .key(MASTER_KEY)
                .consistencyLevel(ConsistencyLevel.EVENTUAL)
                .buildClient();


        System.out.println(cosmosClient);
        CosmosDatabaseResponse cosmosDatabaseResponse = cosmosClient.createDatabaseIfNotExists("Dhanush-test");
        CosmosDatabase cosmosDatabase = cosmosClient.getDatabase(cosmosDatabaseResponse.getProperties().getId());
        ThroughputResponse throughputResponse = cosmosDatabase.readThroughput();
        int tp = throughputResponse.getProperties().getManualThroughput();
        System.out.println(tp);
        cosmosClient.close();
        return tp;
    }
}
