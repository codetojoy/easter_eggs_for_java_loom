#!/bin/bash

set -e

./clean.sh
./compile.sh
rm -f pid.txt
./run.sh 

./thread-dump.sh `cat pid.txt`
./parse-dump.sh 

