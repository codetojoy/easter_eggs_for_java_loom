#!/bin/bash

set -e

mvn install
mvn clean
mvn compile 
mvn test

echo "test complete"
