/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.jbang.core.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.debezium.jbang.core.common.CommandLineHelper;

public class LocalConfig {

    private static final String CONFIG_FILE = ".dbz/config.yaml";

    private List<Platform> platforms;

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public static LocalConfig load() {
        Path configFile = CommandLineHelper.getHomeDir().resolve(CONFIG_FILE);
        if (Files.exists(configFile)) {
            try {
                return new ObjectMapper(new YAMLFactory()).readValue(configFile.toFile(), LocalConfig.class);
            }
            catch (IOException ignore) {
            }
        }
        return new LocalConfig();
    }
}
