#!/bin/bash

set -e 

FILE=thread.dump.json

groovy DumpParser.groovy $FILE
