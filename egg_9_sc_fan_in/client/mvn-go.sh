#!/bin/bash

set -e

./mvn-clean.sh
./mvn-compile.sh
rm -f pid.txt
./mvn-run.sh 
