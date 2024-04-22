package com.stardevllc.starlib.http.request;

import com.stardevllc.starlib.http.HttpRequest;

import java.io.IOException;

public class HttpPostRequest extends HttpRequest {
    
    public HttpPostRequest(String url) throws IOException {
        super(url);
        this.connection.setRequestMethod("POST");
    }
}
