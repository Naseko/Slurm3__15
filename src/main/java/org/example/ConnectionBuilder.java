package org.example;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConnectionBuilder {
    String baseURL = "https://meowfacts.herokuapp.com";
    String methodType = "GET";
    URL url;
    Integer factsNumber;

    public ConnectionBuilder(Integer factsNumber){
        this.factsNumber = factsNumber;
    }
    private void buildUrl() throws MalformedURLException {
        this.url =  new URL(baseURL + String.format("/?count=%d", this.factsNumber));
    }
    public HttpURLConnection buildConnection() throws IOException {
        this.buildUrl();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(methodType);
        return con;
    }
}
