#!/bin/bash

set -e

stat pid.txt > /dev/null 2>&1 

# this calls jcmd with the process id written by Runner.java
./thread-dump.sh `cat pid.txt`

# this uses DumpParser.groovy
./parse-thread-dump.sh 
