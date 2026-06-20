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

class DestinationDeleteJB extends AbstractDestinationJB {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("should delete destination by id and confirm")
    void shouldDeleteDestination() throws IOException {
        Path yaml = tempDir.resolve("destination.yaml");
        Files.writeString(yaml, destinationYaml("delete-test-destination"));
        String createOutput = executeDestination("create -f " + yaml.toAbsolutePath());
        String id = createOutput.replace("Destination created with id:", "").trim();

        String output = executeDestination("delete " + id);

        assertThat(output.trim()).isEqualTo("Destination " + id + " deleted.");
    }
}
