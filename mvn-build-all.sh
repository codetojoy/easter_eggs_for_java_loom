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
./mvn-build.sh 

cd $MY_HOME/egg_5_sc_invoke_all
./mvn-build.sh 

cd $MY_HOME/egg_6_sc_invoke_any
./mvn-build.sh 

cd $MY_HOME/egg_7_sc_deadline
./mvn-build.sh 

cd $MY_HOME/egg_8_sc_custom_invoke_some
./mvn-build.sh 

cd $MY_HOME/egg_9_sc_fan_in/client
./mvn-build.sh

cd $MY_HOME/egg_9_sc_fan_in/server
./mvn-build.sh

cd $MY_HOME/egg__10_sc_nested_scopes_thread_dump
./mvn-build.sh 

cd $MY_HOME/egg__10b_sc_nested_experiment
./mvn-build.sh

cd $MY_HOME/egg__11_sc_utility
./mvn-build.sh

cd $MY_HOME/egg__11b_sc_utility
./mvn-build.sh 

cd $MY_HOME/egg__12_sc_junit
./mvn-build.sh 

cd $MY_HOME/egg_StackOverflow_74464598
./mvn-build.sh

cd $MY_HOME

echo "Ready."
