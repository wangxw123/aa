#!/bin/bash

#Config your java home

if [ -z "$JAVA_HOME" ]; then
  export JAVA=`which java`
else
  export JAVA="$JAVA_HOME/bin/java"
fi

export CLASSPATH=$CLASSPATH:$BASE_DIR/conf:$(ls $BASE_DIR/lib/*.jar | tr '\n' :)

#Server jvm args
SERVER_JVM_ARGS="-Xmx2048m -Xms2048m -server -cp $CLASSPATH "

if [ -z "$SERVER_ARGS" ]; then
  export SERVER_ARGS="$SERVER_JVM_ARGS"
fi
