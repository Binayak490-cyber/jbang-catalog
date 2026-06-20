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

import io.debezium.jbang.core.platform.destination.Destination;
import io.debezium.jbang.core.platform.destination.dto.DestinationResponse;
import io.debezium.jbang.core.platform.destination.mapper.DestinationMapper;

class DestinationMapperTest {

    @Test
    void shouldMapFullResponse() {
        DestinationResponse response = new DestinationResponse(
                1L, "my-destination", "A destination", "destination", "io.debezium.kafka",
                new DestinationResponse.NamedRef(10L, "conn"),
                Set.of(new DestinationResponse.NamedRef(20L, "vault")),
                Map.of("key", "value"));

        Destination d = DestinationMapper.toDomain(response);

        assertThat(d.id()).isEqualTo(1L);
        assertThat(d.name()).isEqualTo("my-destination");
        assertThat(d.description()).isEqualTo("A destination");
        assertThat(d.type()).isEqualTo("destination");
        assertThat(d.schema()).isEqualTo("io.debezium.kafka");
        assertThat(d.connection()).isEqualTo(new Destination.NamedRef(10L, "conn"));
        assertThat(d.vaults()).containsExactly(new Destination.NamedRef(20L, "vault"));
        assertThat(d.config()).containsEntry("key", "value");
    }

    @Test
    void shouldHandleNullOptionalFields() {
        DestinationResponse response = new DestinationResponse(
                2L, "minimal", null, "destination", "io.debezium.kafka", null, null, null);

        Destination d = DestinationMapper.toDomain(response);

        assertThat(d.id()).isEqualTo(2L);
        assertThat(d.name()).isEqualTo("minimal");
        assertThat(d.description()).isNull();
        assertThat(d.connection()).isNull();
        assertThat(d.vaults()).isNull();
        assertThat(d.config()).isNull();
    }

    @Test
    void shouldMapList() {
        List<DestinationResponse> responses = List.of(
                new DestinationResponse(1L, "a", null, "destination", "io.debezium.kafka", null, null, null),
                new DestinationResponse(2L, "b", null, "destination", "io.debezium.kafka", null, null, null));

        List<Destination> result = DestinationMapper.toDomain(responses);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).name()).isEqualTo("a");
        assertThat(result.get(1).name()).isEqualTo("b");
    }
}
