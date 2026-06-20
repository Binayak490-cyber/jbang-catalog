/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.jbang.core.platform.source.mapper;

import java.util.List;
import java.util.stream.Collectors;

import io.debezium.jbang.core.platform.source.Source;
import io.debezium.jbang.core.platform.source.dto.SourceResponse;

public class SourceMapper {

    private SourceMapper() {
    }

    public static Source toDomain(SourceResponse response) {
        return new Source(
                response.id(),
                response.name(),
                response.description(),
                response.type(),
                response.schema(),
                response.connection() != null ? new Source.NamedRef(response.connection().id(), response.connection().name()) : null,
                response.vaults() != null ? response.vaults().stream()
                        .map(v -> new Source.NamedRef(v.id(), v.name()))
                        .collect(Collectors.toSet()) : null,
                response.config());
    }

    public static List<Source> toDomain(List<SourceResponse> responses) {
        return responses.stream().map(SourceMapper::toDomain).toList();
    }
}
