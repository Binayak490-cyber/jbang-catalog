/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.jbang.core.commands.build;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class RegistryAuthResolverTest {

    @TempDir
    Path tempDir;

    @Test
    void returnsEmptyWhenDockerConfigAbsent() {
        Optional<RegistryAuthResolver.RegistryAuth> result = RegistryAuthResolver.resolveFromDockerConfig(
                null, tempDir.resolve("nonexistent.json"));
        assertThat(result).isEmpty();
    }

    @Test
    void resolvesDockerHubCredentialsFromDefaultKey() throws Exception {
        String encoded = Base64.getEncoder().encodeToString("myuser:mypass".getBytes());
        Path config = tempDir.resolve("config.json");
        Files.writeString(config, """
                {
                  "auths": {
                    "https://index.docker.io/v1/": {
                      "auth": "%s"
                    }
                  }
                }
                """.formatted(encoded));

        Optional<RegistryAuthResolver.RegistryAuth> result = RegistryAuthResolver.resolveFromDockerConfig(null, config);
        assertThat(result).isPresent();
        assertThat(result.get().username()).isEqualTo("myuser");
        assertThat(result.get().password()).isEqualTo("mypass");
    }

    @Test
    void resolvesGhcrCredentialsByHostname() throws Exception {
        String encoded = Base64.getEncoder().encodeToString("ghuser:ghtoken".getBytes());
        Path config = tempDir.resolve("config.json");
        Files.writeString(config, """
                {
                  "auths": {
                    "ghcr.io": {
                      "auth": "%s"
                    }
                  }
                }
                """.formatted(encoded));

        Optional<RegistryAuthResolver.RegistryAuth> result = RegistryAuthResolver.resolveFromDockerConfig("ghcr.io", config);
        assertThat(result).isPresent();
        assertThat(result.get().username()).isEqualTo("ghuser");
        assertThat(result.get().password()).isEqualTo("ghtoken");
    }

    @Test
    void returnsEmptyWhenNoMatchingRegistryInDockerConfig() throws Exception {
        String encoded = Base64.getEncoder().encodeToString("user:pass".getBytes());
        Path config = tempDir.resolve("config.json");
        Files.writeString(config, """
                {
                  "auths": {
                    "ghcr.io": {
                      "auth": "%s"
                    }
                  }
                }
                """.formatted(encoded));

        Optional<RegistryAuthResolver.RegistryAuth> result = RegistryAuthResolver.resolveFromDockerConfig(
                "registry.example.com", config);
        assertThat(result).isEmpty();
    }

    @Test
    void returnsEmptyWhenAuthsIsEmpty() throws Exception {
        Path config = tempDir.resolve("config.json");
        Files.writeString(config, """
                {
                  "auths": {}
                }
                """);

        Optional<RegistryAuthResolver.RegistryAuth> result = RegistryAuthResolver.resolveFromDockerConfig(null, config);
        assertThat(result).isEmpty();
    }

    @Test
    void handlesHttpsPrefixedKeyForCustomRegistry() throws Exception {
        String encoded = Base64.getEncoder().encodeToString("admin:secret".getBytes());
        Path config = tempDir.resolve("config.json");
        Files.writeString(config, """
                {
                  "auths": {
                    "https://myregistry.example.com": {
                      "auth": "%s"
                    }
                  }
                }
                """.formatted(encoded));

        Optional<RegistryAuthResolver.RegistryAuth> result = RegistryAuthResolver.resolveFromDockerConfig(
                "myregistry.example.com", config);
        assertThat(result).isPresent();
        assertThat(result.get().username()).isEqualTo("admin");
        assertThat(result.get().password()).isEqualTo("secret");
    }
}
