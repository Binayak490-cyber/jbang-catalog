/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.jbang.core.commands.pipeline;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.debezium.jbang.core.commands.DebeziumCommand;
import io.debezium.jbang.core.commands.DebeziumJBangMain;
import io.debezium.jbang.core.platform.Pipeline;
import io.debezium.jbang.core.platform.PipelineMapper;
import io.debezium.jbang.core.platform.dto.PipelineRequest;
import io.debezium.jbang.core.platform.http.HttpPlatformClient;

import picocli.CommandLine;

@CommandLine.Command(name = "create", description = "Create a new pipeline from a YAML file", sortOptions = false)
public class PipelineCreate extends DebeziumCommand {

    @CommandLine.Option(names = { "--file", "-f" }, required = true, description = "Path to pipeline YAML file")
    File file;

    @CommandLine.Mixin
    PlatformApiMixin platformOptions;

    public PipelineCreate(DebeziumJBangMain main) {
        super(main);
    }

    @Override
    public Integer doCall() throws Exception {
        ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
        PipelineRequest request = yamlMapper.readValue(file, PipelineRequest.class);

        var client = new HttpPlatformClient(platformOptions.resolvedApiUrl());
        Pipeline created = PipelineMapper.toDomain(client.createPipeline(request));

        println("Pipeline created with id: " + created.id());
        return 0;
    }
}
