/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.jbang.core.commands.destination;

import java.net.URI;

import io.debezium.jbang.core.platform.destination.service.DestinationService;
import io.debezium.jbang.core.platform.destination.service.HttpDestinationService;
import io.debezium.jbang.core.util.ConfigUtil;

import picocli.CommandLine;

public class PlatformFactory {

    @CommandLine.Option(names = { "--platform-address" }, description = "Debezium Platform API URL (overrides ~/.dbz/config.yaml, default: "
            + ConfigUtil.DEFAULT_PLATFORM_URL + ")", defaultValue = "${DEBEZIUM_PLATFORM_URL:-}")
    private String platformAddress;

    public DestinationService create() {
        if (platformAddress != null && !platformAddress.isBlank()) {
            return new HttpDestinationService(URI.create(platformAddress));
        }
        return new HttpDestinationService(URI.create(ConfigUtil.getPlatformUrl()));
    }
}
