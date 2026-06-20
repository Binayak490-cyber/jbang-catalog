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

class SourceListIT extends AbstractSourceIT {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("should list sources in table format")
    void shouldListSources() throws IOException {
        Path yaml = tempDir.resolve("source.yaml");
        Files.writeString(yaml, sourceYaml("list-test-source"));
        executeSource("create -f " + yaml.toAbsolutePath());

        String output = executeSource("list");

        assertThat(output)
                .contains("list-test-source")
                .contains("io.debezium.postgres");
    }
}
