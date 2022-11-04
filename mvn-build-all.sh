#!/bin/bash

set -e 

MY_HOME=$PWD

cd $MY_HOME/egg_1_max_p_threads
./mvn-build.sh 

cd $MY_HOME/egg_2_max_v_threads
./mvn-build.sh 

cd $MY_HOME/egg_3_legacy_exec
./mvn-build.sh

cd $MY_HOME/egg_4_virtual_exec
mvn install && ./mvn-clean.sh && ./mvn-compile.sh 

cd $MY_HOME/egg_5_sc_invoke_all
mvn install && ./mvn-clean.sh && ./mvn-compile.sh 

cd $MY_HOME/egg_6_sc_invoke_any
mvn install && ./mvn-clean.sh && ./mvn-compile.sh 

cd $MY_HOME/egg_7_sc_deadline
mvn install && ./mvn-clean.sh && ./mvn-compile.sh 

cd $MY_HOME/egg_8_sc_custom_invoke_some
mvn install && ./mvn-clean.sh && ./mvn-compile.sh 

cd $MY_HOME/egg_9_sc_fan_in/client
mvn install && ./mvn-clean.sh && ./mvn-compile.sh 

cd $MY_HOME/egg_9_sc_fan_in/server
mvn install && ./mvn-clean.sh && ./mvn-compile.sh 

cd $MY_HOME/egg__10_sc_nested_scopes_thread_dump
mvn install && ./mvn-clean.sh && ./mvn-compile.sh 

cd $MY_HOME/egg__10b_sc_nested_experiment
mvn install && ./mvn-clean.sh && ./mvn-compile.sh 

cd $MY_HOME/egg__11_sc_utility
mvn install && ./mvn-clean.sh && ./mvn-compile.sh 

cd $MY_HOME/egg__11b_sc_utility
mvn install && ./mvn-clean.sh && ./mvn-compile.sh 

cd $MY_HOME

echo "Ready."
