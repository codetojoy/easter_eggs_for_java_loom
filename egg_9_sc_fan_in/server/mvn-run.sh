#!/bin/bash

set -e

mvn install
mvn clean
mvn compile 
mvn exec:exec

echo "run complete"
