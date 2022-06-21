#!/bin/bash

set -e 

# written by our call to jcmd
FILE=thread.dump.json

groovy DumpParser.groovy $FILE
