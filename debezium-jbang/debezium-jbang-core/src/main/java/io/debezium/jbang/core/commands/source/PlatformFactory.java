/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.jbang.core.commands.source;

import java.net.URI;

import io.debezium.jbang.core.platform.source.service.HttpSourceService;
import io.debezium.jbang.core.platform.source.service.SourceService;
import io.debezium.jbang.core.util.ConfigUtil;

import picocli.CommandLine;

public class PlatformFactory {

    @CommandLine.Option(names = { "--platform-address" }, description = "Debezium Platform API URL (overrides ~/.dbz/config.yaml, default: "
            + ConfigUtil.DEFAULT_PLATFORM_URL + ")", defaultValue = "${DEBEZIUM_PLATFORM_URL:-}")
    private String platformAddress;

    public SourceService create() {
        if (platformAddress != null && !platformAddress.isBlank()) {
            return new HttpSourceService(URI.create(platformAddress));
        }
        return new HttpSourceService(URI.create(ConfigUtil.getPlatformUrl()));
    }
}
