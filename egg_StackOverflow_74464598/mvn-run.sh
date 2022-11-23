#!/bin/bash

set -e

if [ -n "$1" ]; then
DO_SHUTDOWN=$1
else
  echo "usage: mvn-run.sh DO_SHUTDOWN=yes|no"
  exit -1
fi

mvn install
mvn clean
mvn compile 
mvn exec:exec

# mvn exec:java -Dexec.mainClass="net.codetojoy.Runner" -Dexec.args="$DO_SHUTDOWN --add-modules=jdk.incubator.concurrent --enable-preview"

echo "run complete"
