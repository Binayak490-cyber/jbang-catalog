/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.jbang.core.platform.http;

import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.debezium.jbang.core.platform.PlatformClient;
import io.debezium.jbang.core.platform.dto.PipelineRequest;
import io.debezium.jbang.core.platform.dto.PipelineResponse;

public class HttpPlatformClient implements PlatformClient {

    private final URI baseUri;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public HttpPlatformClient(String apiUrl, HttpClient httpClient, ObjectMapper objectMapper) {
        this.baseUri = URI.create(apiUrl);
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public HttpPlatformClient(String apiUrl) {
        this(apiUrl, HttpClient.newHttpClient(), new ObjectMapper());
    }

    @Override
    public List<PipelineResponse> listPipelines() throws Exception {
        try {
            var request = HttpRequest.newBuilder()
                    .uri(baseUri.resolve("/pipelines"))
                    .header("Accept", "application/json")
                    .GET()
                    .build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            checkStatus(response);
            return objectMapper.readValue(response.body(), new TypeReference<List<PipelineResponse>>() {
            });
        }
        catch (ConnectException e) {
            throw connectionError();
        }
    }

    @Override
    public PipelineResponse getPipeline(Long id) throws Exception {
        try {
            var request = HttpRequest.newBuilder()
                    .uri(baseUri.resolve("/pipelines/" + id))
                    .header("Accept", "application/json")
                    .GET()
                    .build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            checkStatus(response);
            return objectMapper.readValue(response.body(), PipelineResponse.class);
        }
        catch (ConnectException e) {
            throw connectionError();
        }
    }

    @Override
    public PipelineResponse createPipeline(PipelineRequest pipelineRequest) throws Exception {
        try {
            var body = objectMapper.writeValueAsString(pipelineRequest);
            var request = HttpRequest.newBuilder()
                    .uri(baseUri.resolve("/pipelines"))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            checkStatus(response);
            return objectMapper.readValue(response.body(), PipelineResponse.class);
        }
        catch (ConnectException e) {
            throw connectionError();
        }
    }

    @Override
    public PipelineResponse updatePipeline(Long id, PipelineRequest pipelineRequest) throws Exception {
        try {
            var body = objectMapper.writeValueAsString(pipelineRequest);
            var request = HttpRequest.newBuilder()
                    .uri(baseUri.resolve("/pipelines/" + id))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            checkStatus(response);
            return objectMapper.readValue(response.body(), PipelineResponse.class);
        }
        catch (ConnectException e) {
            throw connectionError();
        }
    }

    @Override
    public void deletePipeline(Long id) throws Exception {
        try {
            var request = HttpRequest.newBuilder()
                    .uri(baseUri.resolve("/pipelines/" + id))
                    .DELETE()
                    .build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            checkStatus(response);
        }
        catch (ConnectException e) {
            throw connectionError();
        }
    }

    private void checkStatus(HttpResponse<String> response) {
        int status = response.statusCode();
        if (status >= 400) {
            throw new RuntimeException("Platform API error [HTTP " + status + "]: " + response.body());
        }
    }

    private RuntimeException connectionError() {
        return new RuntimeException(
                "Cannot connect to Debezium Platform at " + baseUri
                        + ". Is the Platform running? Use --api-url to specify a different URL.");
    }
}
