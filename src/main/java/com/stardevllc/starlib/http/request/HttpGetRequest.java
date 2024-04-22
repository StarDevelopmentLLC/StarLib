package com.stardevllc.starlib.http.request;

import com.stardevllc.starlib.http.HttpRequest;

import java.io.IOException;

public class HttpGetRequest extends HttpRequest {
    
    public HttpGetRequest(String url) throws IOException {
        super(url);
    }
}