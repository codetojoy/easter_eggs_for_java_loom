#!/bin/bash

set -e

if [ -n "$1" ]; then
DO_SHUTDOWN=$1
else
  echo "usage: run.sh DO_SHUTDOWN=yes|no"
  exit -1
fi

ROOT_DIR=$PWD
SRC_DIR=$ROOT_DIR/src/main/java
TARGET_DIR=$ROOT_DIR/my_build/main
CLASSPATH=$CLASSPATH:$TARGET_DIR

java --enable-preview \
-cp $CLASSPATH \
--add-modules jdk.incubator.concurrent \
net.codetojoy.Runner $DO_SHUTDOWN

echo "run complete"
