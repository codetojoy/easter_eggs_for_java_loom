#!/bin/bash

set -e

CLASSPATH=.

ROOT_DIR=$PWD
SRC_DIR=$ROOT_DIR/src/main/java
TARGET_DIR=$ROOT_DIR/my_build/main

mkdir -p $TARGET_DIR

javac --release 19 --enable-preview \
-cp $CLASSPATH \
--add-modules jdk.incubator.concurrent \
-d $TARGET_DIR `find $SRC_DIR -name "*.java"`

echo "compile ok"
