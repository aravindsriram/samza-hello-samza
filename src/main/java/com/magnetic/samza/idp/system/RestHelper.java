package com.magnetic.samza.idp.system;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnetic.samza.idp.task.NotifyIDServiceStreamTask;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

public class RestHelper
{
    private static final Logger log = LoggerFactory.getLogger(NotifyIDServiceStreamTask.class);

    public static void doPost(String url, String data) {
        try
        {
            HttpResponse<String> jsonResponse = Unirest.post(url)
                    .header("accept", "application/json")
                    .field("data", data)
                    .asString();
        } catch (UnirestException e)
        {
            log.error(e.getMessage());
        }
    }

    public static void doPostAsync(String url, String data){
        Future<HttpResponse<JsonNode>> future = Unirest.post(url)
                .header("accept", "application/json")
                .field("data", data)
                .asJsonAsync(new Callback<JsonNode>() {
    
                  public void failed(UnirestException e) {
                      log.error("The request has failed");
                  }
    
                  public void completed(HttpResponse<JsonNode> response) {
                       int code = response.getStatus();
                       Map<String, List<String>> headers = response.getHeaders();
                       JsonNode body = response.getBody();
                       InputStream rawBody = response.getRawBody();
                  }
    
                  public void cancelled() {
                      log.error("The request has been cancelled");
                  }
    
              });
    }
    
}
