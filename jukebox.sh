#!/bin/bash

THIS_DIR=`pwd`

AN=`uname`
LINUX="Linux"

if [ $AN == $LINUX ]; then	
  SEPARATOR=":"
else
  SEPARATOR=";"
  THIS_DIR=`cygpath -m ${THIS_DIR}` # (hopefully) running through cygwin
fi

CLASSPATH="jukebox.jar"
CLASSPATH="${CLASSPATH}${SEPARATOR}images/"
for JAR in `ls lib` ; do
  JAR_PATH="lib/${JAR}"
  CLASSPATH="${CLASSPATH}${SEPARATOR}${JAR_PATH}"
done
CLASSPATH="${CLASSPATH#${SEPARATOR}}"

echo $CLASSPATH

java -ea -Djava.library.path=. -classpath ${CLASSPATH} org.jukebox.Application

exit 0;
