#!/bin/bash

set -e 

MY_HOME=$PWD

cd $MY_HOME/egg_1_max_p_threads
./clean.sh && ./compile.sh 

cd $MY_HOME/egg_2_max_v_threads
./clean.sh && ./compile.sh 

cd $MY_HOME/egg_3_legacy_exec
./clean.sh && ./compile.sh 

cd $MY_HOME/egg_4_virtual_exec
./clean.sh && ./compile.sh 

cd $MY_HOME/egg_5_sc_invoke_all
./clean.sh && ./compile.sh 

cd $MY_HOME/egg_6_sc_invoke_any
./clean.sh && ./compile.sh 

cd $MY_HOME/egg_7_sc_deadline
./clean.sh && ./compile.sh 

cd $MY_HOME/egg_8_sc_custom_invoke_some
./clean.sh && ./compile.sh 

exit 0

cd $MY_HOME/egg_9_sc_fan_in/client
./clean.sh && ./compile.sh 

cd $MY_HOME/egg_9_sc_fan_in/server
./clean.sh && ./compile.sh 

cd $MY_HOME/egg__10_sc_nested_scopes_thread_dump
./clean.sh && ./compile.sh 

cd $MY_HOME/egg__10b_sc_nested_experiment
./clean.sh && ./compile.sh 

cd $MY_HOME/egg__11_sc_utility
./clean.sh && ./compile.sh 

cd $MY_HOME/egg__11b_sc_utility
./clean.sh && ./compile.sh 

cd $MY_HOME/egg_StackOverflow_74464598
./clean.sh && ./compile.sh

cd $MY_HOME

echo "Ready."
