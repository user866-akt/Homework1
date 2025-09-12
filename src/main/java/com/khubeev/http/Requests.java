package com.khubeev.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Requests implements HttpClient {

    @Override
    public String get(String url, Map<String, String> headers, Map<String, String> params) {
        HttpURLConnection connection = null;

        try {
            String fullUrl = buildUrlWithParams(url, params);
            URL apiUrl = new URL(fullUrl);
            connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            setHeaders(connection, headers);
            return readResponse(connection);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String post(String url, Map<String, String> headers, Map<String, String> data) {
        HttpURLConnection connection = null;
        try {
            URL apiUrl = new URL(url);
            connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            setHeaders(connection, headers);
            if (data != null && !data.isEmpty()) {
                writeFormData(connection, data);
            }
            return readResponse(connection);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String put(String url, Map<String, String> headers, Map<String, String> data) {
        HttpURLConnection connection = null;
        try {
            URL apiUrl = new URL(url);
            connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            setHeaders(connection, headers);
            if (data != null && !data.isEmpty()) {
                writeFormData(connection, data);
            }
            return readResponse(connection);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String delete(String url, Map<String, String> headers, Map<String, String> params) {
        HttpURLConnection connection = null;
        try {
            String fullUrl = buildUrlWithParams(url, params);
            URL apiUrl = new URL(fullUrl);
            connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            setHeaders(connection, headers);
            return readResponse(connection);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildUrlWithParams(String baseUrl, Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return baseUrl;
        }
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        boolean hasExistingParams = baseUrl.contains("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (hasExistingParams) {
                urlBuilder.append("&");
            } else {
                urlBuilder.append("?");
                hasExistingParams = true;
            }
            urlBuilder.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return urlBuilder.toString();
    }

    private void setHeaders(HttpURLConnection connection, Map<String, String> headers) {
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("User-Agent", "Java-HTTP-Client");
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
    }

    private String readResponse(HttpURLConnection connection) {
        if (connection == null) {
            return null;
        }
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder content = new StringBuilder();
            String input;
            while ((input = reader.readLine()) != null) {
                content.append(input);
            }
            return content.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeFormData(HttpURLConnection connection, Map<String, String> data) throws IOException {
        StringBuilder formData = new StringBuilder();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            if (formData.length() > 0) {
                formData.append("&");
            }
            formData.append(entry.getKey()).append("=").append(entry.getValue());
        }
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = formData.toString().getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);
        }
    }
}