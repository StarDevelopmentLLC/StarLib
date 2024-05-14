package com.stardevllc.starlib.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class HttpRequest {
    protected final String url;
    protected final HttpURLConnection connection;
    
    public HttpRequest(String url) throws IOException {
        this.url = url;
        this.connection = (HttpURLConnection) new URL(this.url).openConnection();
    }
    
    public HttpResponse connect() throws IOException {
        this.connection.connect();
    
        try (Reader stream = new InputStreamReader(this.connection.getInputStream()); BufferedReader in = new BufferedReader(stream)) {
            StringBuilder response = new StringBuilder();
            String line;
        
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        
            return new HttpResponse(this.url, response.toString(), this.connection.getResponseCode());
        }
    }
}
