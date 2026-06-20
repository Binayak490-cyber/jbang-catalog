/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.jbang.core.commands.destination;

import io.debezium.jbang.core.DebeziumJBangMain;
import io.debezium.jbang.core.commands.DebeziumCommand;

import picocli.CommandLine;

@CommandLine.Command(name = "destination", description = "Manage Debezium Platform destinations (use destination --help to see subcommands)", mixinStandardHelpOptions = true, sortOptions = false)
public class DestinationCommand extends DebeziumCommand {

    public DestinationCommand(DebeziumJBangMain main) {
        super(main);
    }

    @Override
    public Integer doCall() throws Exception {
        spec.commandLine().execute("--help");
        return 0;
    }
}
