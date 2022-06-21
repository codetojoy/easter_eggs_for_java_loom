#!/bin/bash

set -e 

if [ -n "$1" ]; then
PID=$1
else
  echo "usage: ./thread.sh {ProcessId}"
  exit -1
fi

FILE=thread.dump.json

# the JEP states that the command is `JavaDump.thread`, but 
* this thread has newer information: https://mail.openjdk.org/pipermail/loom-dev/2022-April/004139.html
* and confirms `Thread.dump_to_file`

jcmd $PID Thread.dump_to_file -format=json -overwrite $FILE 

echo "wrote thread.dump.json"
echo "Ready."
