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

class SourceCreateJB extends AbstractSourceJB {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("should create source from YAML file and print its id")
    void shouldCreateSource() throws IOException {
        Path yaml = tempDir.resolve("source.yaml");
        Files.writeString(yaml, sourceYaml("create-test-source"));

        String output = executeSource("create -f " + yaml.toAbsolutePath());

        assertThat(output.trim()).startsWith("Source created with id:");
    }
}
