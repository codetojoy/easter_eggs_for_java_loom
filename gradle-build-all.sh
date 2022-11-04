#!/bin/bash

set -e 

gradle egg_1_max_p_threads:compileJava

# cd $MY_HOME/egg_2_max_v_threads

# cd $MY_HOME/egg_3_legacy_exec

# cd $MY_HOME/egg_4_virtual_exec

# cd $MY_HOME/egg_5_sc_invoke_all

# cd $MY_HOME/egg_6_sc_invoke_any

# cd $MY_HOME/egg_7_sc_deadline

# cd $MY_HOME/egg_8_sc_custom_invoke_some

# cd $MY_HOME/egg_9_sc_fan_in/client

# cd $MY_HOME/egg_9_sc_fan_in/server

# cd $MY_HOME/egg__10_sc_nested_scopes_thread_dump

# cd $MY_HOME/egg__10b_sc_nested_experiment

# cd $MY_HOME/egg__11_sc_utility

# cd $MY_HOME/egg__11b_sc_utility

echo "Ready."
