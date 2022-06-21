#!/bin/bash

set -e 

if [ -n "$1" ]; then
PID=$1
else
  echo "usage: ./thread.sh {ProcessId}"
  exit -1
fi

FILE=thread.dump.json

jcmd $PID Thread.dump_to_file -format=json -overwrite $FILE 

echo "wrote thread.dump.json"
echo "Ready."
