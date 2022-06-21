#!/bin/bash

set -e

./thread-dump.sh `cat pid.txt`
./parse-thread-dump.sh 
