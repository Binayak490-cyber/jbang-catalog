/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.jbang.core.commands.destination.jbang;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class DestinationUpdateJB extends AbstractDestinationJB {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("should update destination from YAML file and confirm")
    void shouldUpdateDestination() throws IOException {
        Path createYaml = tempDir.resolve("create.yaml");
        Files.writeString(createYaml, destinationYaml("update-test-destination"));
        String createOutput = executeDestination("create -f " + createYaml.toAbsolutePath());
        String id = createOutput.replace("Destination created with id:", "").trim();

        Path updateYaml = tempDir.resolve("update.yaml");
        Files.writeString(updateYaml, destinationYaml("update-test-destination").replace("io.debezium.kafka", "io.debezium.pulsar"));
        String output = executeDestination("update " + id + " -f " + updateYaml.toAbsolutePath());

        assertThat(output.trim()).isEqualTo("Destination " + id + " updated.");
    }
}
