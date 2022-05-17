#!/bin/bash

set -e

ROOT_DIR=$PWD
SRC_DIR=$ROOT_DIR/src/main/java
TARGET_DIR=$ROOT_DIR/my_build/main
CLASSPATH=$CLASSPATH:$TARGET_DIR

java --enable-preview \
-cp $CLASSPATH \
-XX:StartFlightRecording=duration=200s,filename=flight.jfr  \
net.codetojoy.Runner

echo "run complete"
