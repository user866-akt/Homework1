package com.khubeev.server;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/api/*")
public class Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        handleRequest(request, response, "GET");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        handleRequest(request, response, "POST");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        handleRequest(request, response, "PUT");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        handleRequest(request, response, "DELETE");
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response,
                               String method) throws IOException {

        response.setContentType("application/json");

        String result = "{"
                + "\"method\": \"" + method + "\","
                + "\"path\": \"" + request.getPathInfo() + "\","
                + "\"params\": " + getParamsAsJson(request) + ","
                + "\"headers\": " + getHeadersAsJson(request)
                + "}";

        response.getWriter().write(result);
    }

    private String getParamsAsJson(HttpServletRequest request) {
        StringBuilder json = new StringBuilder("{");
        Enumeration<String> paramNames = request.getParameterNames();

        boolean first = true;
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            if (!first) {
                json.append(",");
            }
            json.append("\"").append(paramName).append("\": \"")
                    .append(request.getParameter(paramName)).append("\"");
            first = false;
        }
        json.append("}");

        return json.toString();
    }

    private String getHeadersAsJson(HttpServletRequest request) {
        StringBuilder json = new StringBuilder("{");
        Enumeration<String> headerNames = request.getHeaderNames();

        boolean first = true;
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (!first) {
                json.append(",");
            }
            json.append("\"").append(headerName).append("\": \"")
                    .append(request.getHeader(headerName)).append("\"");
            first = false;
        }
        json.append("}");

        return json.toString();
    }
}