/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.jbang.core.commands.source.nativebuild;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class SourceDeleteIT extends AbstractSourceIT {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("should delete source by id and confirm")
    void shouldDeleteSource() throws IOException {
        Path yaml = tempDir.resolve("source.yaml");
        Files.writeString(yaml, sourceYaml("delete-test-source"));
        String createOutput = executeSource("create -f " + yaml.toAbsolutePath());
        String id = createOutput.replace("Source created with id:", "").trim();

        String output = executeSource("delete " + id);

        assertThat(output.trim()).isEqualTo("Source " + id + " deleted.");
    }
}
