/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.totem_system;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

/**
 *
 * @author Vinícius
 */
public class Slack {
    private static HttpClient client = HttpClient.newHttpClient();
    private static final String URL = "https://hooks.slack.com/services/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";

    public static void sendMensage(JSONObject content) throws IOException, InterruptedException{
        HttpRequest request = HttpRequest.newBuilder(
                URI.create(URL))
                .header("accept", "aplication/json")
                .POST(HttpRequest.BodyPublishers.ofString(content.toString()))
                .build()
                ;
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        System.out.println(String.format("Status: %s", response.statusCode()));
        System.out.println(String.format("Response: %s", response.body()));
    } 
}
