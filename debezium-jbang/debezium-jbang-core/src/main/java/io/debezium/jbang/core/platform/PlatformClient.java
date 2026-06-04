/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.jbang.core.platform;

import java.util.List;

import io.debezium.jbang.core.platform.dto.PipelineRequest;
import io.debezium.jbang.core.platform.dto.PipelineResponse;

public interface PlatformClient {

    List<PipelineResponse> listPipelines() throws Exception;

    PipelineResponse getPipeline(Long id) throws Exception;

    PipelineResponse createPipeline(PipelineRequest pipelineRequest) throws Exception;

    PipelineResponse updatePipeline(Long id, PipelineRequest pipelineRequest) throws Exception;

    void deletePipeline(Long id) throws Exception;
}
