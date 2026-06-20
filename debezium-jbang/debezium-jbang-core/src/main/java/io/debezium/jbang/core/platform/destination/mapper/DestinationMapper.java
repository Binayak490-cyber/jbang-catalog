/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.jbang.core.platform.destination.mapper;

import java.util.List;
import java.util.stream.Collectors;

import io.debezium.jbang.core.platform.destination.Destination;
import io.debezium.jbang.core.platform.destination.dto.DestinationResponse;

public class DestinationMapper {

    private DestinationMapper() {
    }

    public static Destination toDomain(DestinationResponse response) {
        return new Destination(
                response.id(),
                response.name(),
                response.description(),
                response.type(),
                response.schema(),
                response.connection() != null ? new Destination.NamedRef(response.connection().id(), response.connection().name()) : null,
                response.vaults() != null ? response.vaults().stream()
                        .map(v -> new Destination.NamedRef(v.id(), v.name()))
                        .collect(Collectors.toSet()) : null,
                response.config());
    }

    public static List<Destination> toDomain(List<DestinationResponse> responses) {
        return responses.stream().map(DestinationMapper::toDomain).toList();
    }
}
