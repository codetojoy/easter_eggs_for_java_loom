#!/bin/bash

set -e

ROOT_DIR=$PWD
TARGET_DIR=$ROOT_DIR/my_build

rm -rf $TARGET_DIR
rm -f *.jfr

echo "clean ok"
