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

# cd $MY_HOME/egg_9_sc_fan_in/client
# cd $MY_HOME/egg_9_sc_fan_in/server
# cd $MY_HOME/egg__10_sc_nested_scopes_thread_dump
# cd $MY_HOME/egg__10b_sc_nested_experiment
# cd $MY_HOME/egg__11_sc_utility
# cd $MY_HOME/egg__11b_sc_utility

echo "Ready."
