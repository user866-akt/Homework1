package com.khubeev.http;

import java.util.Map;

public class SimpleTest {
    public static void main(String[] args) {
        Requests client = new Requests();

        String baseUrl = "http://localhost:8080/api";

        Map<String, String> params = Map.of(
                "param1", "value1",
                "param2", "value2"
        );

        Map<String, String> headers = Map.of(
                "User-Agent", "TestClient",
                "Accept", "application/json",
                "Authorization", "Bearer test-token"
        );

        Map<String, String> postData = Map.of(
                "username", "testuser",
                "email", "test@example.com",
                "password", "secret123"
        );

        Map<String, String> putData = Map.of(
                "id", "123",
                "name", "Updated Name",
                "status", "active"
        );

        //GET запрос
        try {
            String getUrl = baseUrl + "/test-get";
            String response = client.get(getUrl, headers, params);
            System.out.println("GET Response:");
            System.out.println(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println();

        //POST запрос
        try {
            String postUrl = baseUrl + "/test-post";
            String response = client.post(postUrl, headers, postData);
            System.out.println("POST Response:");
            System.out.println(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println();

        //PUT запрос
        try {
            String putUrl = baseUrl + "/test-put";
            String response = client.put(putUrl, headers, putData);
            System.out.println("PUT Response:");
            System.out.println(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println();

        //DELETE запрос
        try {
            String deleteUrl = baseUrl + "/test-delete";
            String response = client.delete(deleteUrl, headers, params);
            System.out.println("DELETE Response:");
            System.out.println(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
