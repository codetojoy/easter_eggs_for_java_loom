#!/bin/bash

set -e

if [ -n "$1" ]; then
DO_SHUTDOWN=$1
else
  echo "usage: gradle-run.sh DO_SHUTDOWN=yes|no"
  exit -1
fi

../gradlew run --args="$DO_SHUTDOWN"

echo "run complete"
