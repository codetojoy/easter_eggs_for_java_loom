#!/bin/bash

set -e 

MY_HOME=$PWD

clean_compile() {
    echo "... $1"
    cd $MY_HOME/$1
    ./clean.sh && ./compile.sh 
}

clean_compile "egg_1_max_p_threads"

clean_compile "egg_2_max_v_threads"

clean_compile "egg_3_legacy_exec"

clean_compile "egg_4_virtual_exec"

clean_compile "egg_5_sc_invoke_all"

clean_compile "egg_6_sc_invoke_any"

clean_compile "egg_7_sc_deadline"

clean_compile "egg_8_sc_custom_invoke_some"

clean_compile "egg_9_sc_fan_in/client"

clean_compile "egg_9_sc_fan_in/server"

# clean_compile "egg__10_sc_nested_scopes_thread_dump"

# clean_compile "egg__10b_sc_nested_experiment"

# clean_compile "egg_StackOverflow_74464598"

cd $MY_HOME

echo "Ready."
