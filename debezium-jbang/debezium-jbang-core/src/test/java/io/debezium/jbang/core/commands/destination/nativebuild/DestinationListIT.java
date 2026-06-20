/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.jbang.core.commands.destination.nativebuild;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class DestinationListIT extends AbstractDestinationIT {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("should list destinations in table format")
    void shouldListDestinations() throws IOException {
        Path yaml = tempDir.resolve("destination.yaml");
        Files.writeString(yaml, destinationYaml("list-test-destination"));
        executeDestination("create -f " + yaml.toAbsolutePath());

        String output = executeDestination("list");

        assertThat(output)
                .contains("list-test-destination")
                .contains("io.debezium.kafka");
    }
}
