/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.jbang.core.commands.source.jbang;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class SourceUpdateJB extends AbstractSourceJB {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("should update source from YAML file and confirm")
    void shouldUpdateSource() throws IOException {
        Path createYaml = tempDir.resolve("create.yaml");
        Files.writeString(createYaml, sourceYaml("update-test-source"));
        String createOutput = executeSource("create -f " + createYaml.toAbsolutePath());
        String id = createOutput.replace("Source created with id:", "").trim();

        Path updateYaml = tempDir.resolve("update.yaml");
        Files.writeString(updateYaml, sourceYaml("update-test-source").replace("io.debezium.postgres", "io.debezium.mysql"));
        String output = executeSource("update " + id + " -f " + updateYaml.toAbsolutePath());

        assertThat(output.trim()).isEqualTo("Source " + id + " updated.");
    }
}
