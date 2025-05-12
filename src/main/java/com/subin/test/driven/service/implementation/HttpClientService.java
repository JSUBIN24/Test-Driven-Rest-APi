package com.subin.test.driven.service.implementation;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class HttpClientService {

    HttpClient httpClient = HttpClient.newHttpClient();

    public String fetchDataFromExternalApi(String url) {
        String postBody = "{ \"personId\" : \"12345\" }";


        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(postBody))
                .header("content-type", "application/json")
                .build();


        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (httpResponse.statusCode() == 200){
                return httpResponse.body();
            }
            else {
                return "Error: status code " + httpResponse.statusCode();
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Exception" + e.getMessage();
        }
    }
}
