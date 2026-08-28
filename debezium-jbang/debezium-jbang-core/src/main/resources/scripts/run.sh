#!/bin/bash
#
# Copyright Debezium Authors.
#
# Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
#

if [ -z "$JAVA_HOME" ]; then
  JAVA_BINARY="java"
else
  JAVA_BINARY="$JAVA_HOME/bin/java"
fi

RUNNER=$(ls debezium-server-*runner.jar 2>/dev/null | head -1)
if [ -z "$RUNNER" ]; then
  echo "ERROR: runner jar not found in $(pwd)"; exit 1
fi

exec "$JAVA_BINARY" \
  --add-opens java.base/java.lang=ALL-UNNAMED \
  $JAVA_OPTS -cp "$RUNNER:conf:lib/*" io.debezium.server.Main "$@"
