#!/bin/sh
# Change this to your JDK installation root
#
#JAVA_HOME=/usr/java/jdk1.6.0_18

JRE=$JAVA_HOME/jre
JAVA=$JRE/bin/java

workdir=`dirname $0`
workdir=`cd ${workdir} && pwd`
QUARTZ=${workdir}/..

QUARTZ_CP=""
for jarfile in $QUARTZ/extlib/*.jar; do
  QUARTZ_CP=$QUARTZ_CP:$jarfile
done

#LOGGING_PROPS="-Dlog4j.configuration=file:${workdir}/../config/log4j.xml"

#$JAVA -classpath $QUARTZ_CP:${workdir}/../lib/quartz-1.0-SNAPSHOT.jar $LOGGING_PROPS org.quartz.datamaster.DispatchService
$JAVA -classpath $QUARTZ_CP:${workdir}/../lib/quartz-1.0-SNAPSHOT.jar org.quartz.datamaster.DispatchService

