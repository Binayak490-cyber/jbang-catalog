/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.jbang.core.platform.source;

import java.util.Map;
import java.util.Set;

public record Source(
        Long id,
        String name,
        String description,
        String type,
        String schema,
        NamedRef connection,
        Set<NamedRef> vaults,
        Map<String, Object> config) {

    public record NamedRef(Long id, String name) {
    }
}
