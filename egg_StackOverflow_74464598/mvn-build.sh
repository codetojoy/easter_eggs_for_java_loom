#!/bin/bash

set -e

mvn install
mvn clean
mvn compile 

echo "run complete"
