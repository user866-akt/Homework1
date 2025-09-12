package com.khubeev.http;

import java.util.Map;

public class RequestsTest {
    public static void main(String[] args) {
        //get test
        Requests client = new Requests();
        String userPosts = client.get("https://jsonplaceholder.typicode.com/posts",null, Map.of("userId", "6"));
        System.out.println("Посты пользователя 6");
        System.out.println(userPosts);

        //post test
        Map<String, String> data = Map.of("name", "Test User", "comment", "COMMENT");
        String response = client.post("https://httpbin.org/post", null, data);
        System.out.println(response);

        //put test
        Map<String, String> updateData = Map.of("name", "John Updated", "email", "john.updated@mail.com", "status", "active");
        String resp = client.put("https://httpbin.org/put", null, updateData);
        System.out.println(resp);

        //put test (обновляем пост)
        Map<String, String> postUpdate = Map.of("title", "Updated Title", "body", "Updated body content", "userId", "1");
        String updatedPost = client.put("https://jsonplaceholder.typicode.com/posts/1", Map.of("Content-type", "application/json; charset=UTF-8"), postUpdate);
        System.out.println(updatedPost);

        //delete test
        String res = client.delete("https://httpbin.org/delete", null, Map.of("id", "42"));
        System.out.println(res);
    }
}