/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.jbang.core.commands.pipeline;

import io.debezium.jbang.core.util.ConfigUtil;

import picocli.CommandLine;

public class PlatformApiMixin {

    @CommandLine.Option(names = { "--api-url" }, description = "Debezium Platform API URL (overrides ~/.dbz/config.yaml, default: "
            + ConfigUtil.DEFAULT_PLATFORM_URL + ")", defaultValue = "${DEBEZIUM_PLATFORM_URL:-}")
    private String apiUrl;

    public String resolvedApiUrl() {
        if (apiUrl != null && !apiUrl.isBlank()) {
            return apiUrl;
        }
        return ConfigUtil.getPlatformUrl();
    }
}
