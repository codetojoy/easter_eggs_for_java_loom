#!/bin/bash

set -e 

./gradlew clean egg_1_max_p_threads:compileJava

./gradlew clean egg_2_max_v_threads:compileJava

./gradlew clean egg_3_legacy_exec:compileJava

./gradlew clean egg_4_virtual_exec:compileJava

./gradlew clean egg_5_sc_invoke_all:compileJava 

./gradlew clean egg_6_sc_invoke_any:compileJava 

./gradlew clean egg_7_sc_deadline:compileJava 

./gradlew clean egg_8_sc_custom_invoke_some:compileJava

./gradlew clean egg_9_sc_fan_in_client:compileJava
./gradlew clean egg_9_sc_fan_in_server:compileJava

# ./gradlew clean egg__10_sc_nested_scopes_thread_dump:compileJava

# ./gradlew clean egg__10b_sc_nested_experiment:compileJava

# ./gradlew clean egg__12_sc_junit:compileJava

# ./gradlew clean egg_StackOverflow_74464598:compileJava

echo "Ready."
