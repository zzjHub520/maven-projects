package org.example;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        HttpResponse<JsonNode> response = Unirest.post("http://localhost/post")
//                .header("accept", "application/json")
//                .queryString("apiKey", "123")
//                .field("parameter", "value")
//                .field("firstname", "Gary")
//                .asJson();

        HttpResponse<String> stringHttpResponse = Unirest.get("https://www.baidu.com")
//                .header("Accept", "application/json")
//                .header("x-custom-header", "hello")
                .asString();

        System.out.println(stringHttpResponse.getBody());

        HttpResponse<String> stringHttpResponse1 = Unirest.get("http://localhost:8080/hello").asString();
        System.out.println(stringHttpResponse1.getBody());
    }
}