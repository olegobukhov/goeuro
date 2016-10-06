package com.goeuro.test.service;

import com.goeuro.test.model.Location;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class HttpLocationSuggesterService implements LocationSuggesterService, DisposableBean {
    private static Logger log = LoggerFactory.getLogger(HttpLocationSuggesterService.class);

    @Value("${endpoint.pattern}")
    private String endpointPattern;

    /**
     * Thread-safe
     */
    private final Gson gson = new GsonBuilder().create();

    /**
     * Thread-safe
     */
    private final CloseableHttpClient httpClient = HttpClientBuilder.create().build();

    @Override
    public List<Location> getSuggestionsForCity(String cityName) throws ServiceException {
        if (cityName == null || "".equals(cityName)) {
            throw new IllegalArgumentException("Arg [String cityName] cannot be null or empty");
        }
        String href = String.format(endpointPattern, cityName);
        log.debug("connecting to {}", href);
        HttpGet httpGet = new HttpGet(href);
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            HttpEntity httpEntity = response.getEntity();
            checkStatus(response.getStatusLine());
            InputStream inputStream = httpEntity.getContent();
            log.debug("Parsing model entities from json...");
            return unParse(inputStream);
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

    List<Location> unParse(InputStream inputStream) throws IOException {
        final Type listType = new TypeToken<ArrayList<Location>>() {}.getType();
        try(JsonReader reader = new JsonReader(new BufferedReader(new InputStreamReader(inputStream)))){
            return gson.fromJson(reader, listType);
        }
    }

    private void checkStatus(StatusLine statusLine) throws ServiceException {
        int status = statusLine.getStatusCode();
        if (status != HttpStatus.SC_OK) {
            throw new ServiceException("Server returned " + status + " status");
        }
    }

    @Override
    public void destroy() throws Exception {
        log.debug("Closing http client...");
        httpClient.close();
    }
}
