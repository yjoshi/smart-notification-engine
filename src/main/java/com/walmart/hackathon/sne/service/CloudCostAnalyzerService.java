package com.walmart.hackathon.sne.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minidev.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CloudCostAnalyzerService {
    private String CLOUD_URL = "https://strati.walmart.com/cost/api/costs/product-details2?groupBy=resource_group&start=_startDate_--&end=_endDate_--&product=1310";
    //private String CLOUD_URL = "https://strati.walmart.com/cost/api/costs/product-details2?groupBy=resource_group&start=2020-07-24&end=2020-07-27&product=1310";

    public String getCloudCostDetails() throws Exception{


        TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                NoopHostnameVerifier.INSTANCE);

        Registry<ConnectionSocketFactory> socketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory> create()
                        .register("https", sslsf)
                        .register("http", new PlainConnectionSocketFactory())
                        .build();

        BasicHttpClientConnectionManager connectionManager =
                new BasicHttpClientConnectionManager(socketFactoryRegistry);
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
                .setConnectionManager(connectionManager).build();

        HttpResponse response = null;
        String startDate = dateString(withOffsetDaysPast(3));
        String endDate = dateString(withOffsetDaysPast(2));
        String cloudUrl = CLOUD_URL.replace("_startDate_", startDate).replace("_endDate_", endDate);
        HttpGet httpGet = new HttpGet(cloudUrl);
        String responseString = "";
        String toReturn = "";
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            responseString = EntityUtils.toString(entity, "UTF-8");
            JsonObject jsonObject = new JsonParser().parse(responseString).getAsJsonObject();
            toReturn = "All Resources Cost between "+ startDate + " and " + endDate + " = " + jsonObject.get("data").getAsJsonObject().get("total_cost").getAsString();
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toReturn;

    }

    private Date withOffsetDaysPast(int daysPast) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1 * daysPast);
        return cal.getTime();
    }

    private String dateString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

}
