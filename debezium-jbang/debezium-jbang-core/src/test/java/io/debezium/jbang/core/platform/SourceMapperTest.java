/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.jbang.core.platform;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import io.debezium.jbang.core.platform.source.Source;
import io.debezium.jbang.core.platform.source.dto.SourceResponse;
import io.debezium.jbang.core.platform.source.mapper.SourceMapper;

class SourceMapperTest {

    @Test
    void shouldMapFullResponse() {
        SourceResponse response = new SourceResponse(
                1L, "my-source", "A source", "source", "io.debezium.postgres",
                new SourceResponse.NamedRef(10L, "conn"),
                Set.of(new SourceResponse.NamedRef(20L, "vault")),
                Map.of("key", "value"));

        Source s = SourceMapper.toDomain(response);

        assertThat(s.id()).isEqualTo(1L);
        assertThat(s.name()).isEqualTo("my-source");
        assertThat(s.description()).isEqualTo("A source");
        assertThat(s.type()).isEqualTo("source");
        assertThat(s.schema()).isEqualTo("io.debezium.postgres");
        assertThat(s.connection()).isEqualTo(new Source.NamedRef(10L, "conn"));
        assertThat(s.vaults()).containsExactly(new Source.NamedRef(20L, "vault"));
        assertThat(s.config()).containsEntry("key", "value");
    }

    @Test
    void shouldHandleNullOptionalFields() {
        SourceResponse response = new SourceResponse(
                2L, "minimal", null, "source", "io.debezium.postgres", null, null, null);

        Source s = SourceMapper.toDomain(response);

        assertThat(s.id()).isEqualTo(2L);
        assertThat(s.name()).isEqualTo("minimal");
        assertThat(s.description()).isNull();
        assertThat(s.connection()).isNull();
        assertThat(s.vaults()).isNull();
        assertThat(s.config()).isNull();
    }

    @Test
    void shouldMapList() {
        List<SourceResponse> responses = List.of(
                new SourceResponse(1L, "a", null, "source", "io.debezium.postgres", null, null, null),
                new SourceResponse(2L, "b", null, "source", "io.debezium.postgres", null, null, null));

        List<Source> result = SourceMapper.toDomain(responses);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).name()).isEqualTo("a");
        assertThat(result.get(1).name()).isEqualTo("b");
    }
}
