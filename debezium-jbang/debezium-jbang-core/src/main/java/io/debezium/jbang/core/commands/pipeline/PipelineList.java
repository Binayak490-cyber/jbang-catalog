/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.jbang.core.commands.pipeline;

import java.util.List;

import io.debezium.jbang.core.commands.DebeziumCommand;
import io.debezium.jbang.core.commands.DebeziumJBangMain;
import io.debezium.jbang.core.platform.Pipeline;
import io.debezium.jbang.core.platform.PipelineMapper;
import io.debezium.jbang.core.platform.http.HttpPlatformClient;

import picocli.CommandLine;

@CommandLine.Command(name = "list", description = "List all pipelines", sortOptions = false)
public class PipelineList extends DebeziumCommand {

    @CommandLine.Mixin
    PlatformApiMixin platformOptions;

    public PipelineList(DebeziumJBangMain main) {
        super(main);
    }

    @Override
    public Integer doCall() throws Exception {
        var client = new HttpPlatformClient(platformOptions.resolvedApiUrl());
        List<Pipeline> pipelines = PipelineMapper.toDomain(client.listPipelines());

        if (pipelines.isEmpty()) {
            println("No pipelines found.");
            return 0;
        }

        printf("%-5s %-30s %-20s %-20s %-10s%n", "ID", "NAME", "SOURCE", "DESTINATION", "LOG LEVEL");
        printf("%-5s %-30s %-20s %-20s %-10s%n", "---", "----", "------", "-----------", "---------");
        for (Pipeline p : pipelines) {
            printf("%-5s %-30s %-20s %-20s %-10s%n",
                    p.id(),
                    p.name(),
                    p.source() != null ? p.source().name() : "-",
                    p.destination() != null ? p.destination().name() : "-",
                    p.logLevel() != null ? p.logLevel() : "-");
        }
        return 0;
    }
}
