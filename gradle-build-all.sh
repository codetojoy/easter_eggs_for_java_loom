#!/bin/bash

set -e 

./gradlew egg_1_max_p_threads:compileJava

./gradlew egg_2_max_v_threads:compileJava

./gradlew egg_3_legacy_exec:compileJava

./gradlew egg_4_virtual_exec:compileJava

./gradlew egg_5_sc_invoke_all:compileJava 

./gradlew egg_6_sc_invoke_any:compileJava 

./gradlew egg_7_sc_deadline:compileJava 

./gradlew egg_8_sc_custom_invoke_some:compileJava

./gradlew egg_9_sc_fan_in_client:compileJava
./gradlew egg_9_sc_fan_in_server:compileJava

./gradlew egg__10_sc_nested_scopes_thread_dump:compileJava

./gradlew egg__10b_sc_nested_experiment:compileJava

./gradlew egg__11_sc_utility:compileJava

# cd $MY_HOME/egg__11b_sc_utility

echo "Ready."
