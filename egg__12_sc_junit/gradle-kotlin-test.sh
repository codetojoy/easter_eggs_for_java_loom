#!/bin/bash

set -e

# nasty hack for now
# TODO: tell Gradle that we don't want to use ../settings.gradle

mv ../settings.gradle ../tmp.settings.gradle

../gradlew \
--build-file build.gradle.kts \
clean test

mv ../tmp.settings.gradle ../settings.gradle

