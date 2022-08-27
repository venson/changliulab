#!/bin/bash

#JAVA_HOME=/Library/Java/JavaVirtualMachines/temurin-8.jdk/Contents/Home
JAVA="$JAVA_HOME/bin/java"
BASE_DIR=$(cd $(dirname "$0")/.. || exit; pwd)
MEM_64="-Xms64M -Xmx64M"
MEM_128="-Xms64M -Xmx128M "
MEM_256="-Xms64M -Xmx256M "
MEM_512="-Xms64M -Xmx512M "

GATEWAY="*changliulab-gateway*.jar"
MAX_MEM="*changliulab-service-edu*.jar"
MED_MEM="*changliulab-service-oss*.jar *changliulab-service-cms*.jar"
MIN_MEM="*changliulab-service-msm*.jar *changliulab-service-acl*.jar "

SERVICE_PREFIX="changliulab-"

#JARFILE_NAME="$(cd "${BASE_DIR}" && find . -name '*.jar' )"

#PROJECT_HOME=$(pwd |awk -F"/" '{print $NF}')
#JAVA_OPT="${JAVA_OPT} -Xms512m -Xmx512m -Xmn256m"


# waiting timeout for starting, in seconds

START_WAIT_TIMEOUT=30

pSid=0

checkPid() {

  echo "Checking if the services are running"
  javaPs=$("$JAVA_HOME"/bin/jps -l | grep "${SERVICE_PREFIX}")

  if [ -n "$javaPs" ]; then

    echo "Following PID are the Services"
   pSid=$(echo "$javaPs" | awk '{print $1}')

  else

  echo "No service is running"
   pSid=0

  fi
  echo ${pSid}

}


killCurrentRunning(){
  if [ -n "${pSid}"  ]; then
    echo "========================"
    echo "Start kill follow process"
    echo ${pSid}
    kill -9 ${pSid}
  fi
}

#JAVA_OPTS="$JAVA_OPTS -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:InitiatingHeapOccupancyPercent=55 -XX:G1ReservePercent=15"
#JAVA_OPTS="$JAVA_OPTS -XX:MaxTenuringThreshold=15 -XX:ParallelGCThreads=8 -XX:+UnlockExperimentalVMOptions"
#JAVA_OPTS="$JAVA_OPTS -XX:+DisableExplicitGC -XX:+UseFastAccessorMethods -XX:SoftRefLRUPolicyMSPerMB=0"
#JAVA_OPTS="$JAVA_OPTS -XX:+PrintGC -XX:+PrintHeapAtGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps"
#JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCTimeStamps -XX:+PrintGCCause"
#JAVA_OPTS="$JAVA_OPTS -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$prog_log_dir/"
#JAVA_OPTS="$JAVA_OPTS -Xloggc:$prog_gc_log"
#JAVA_OPTS="$JAVA_OPTS $BASE_OPTS $TLIB_OPTS"
startService(){
  checkPid
  if [  "${pSid}" != 0 ]; then
    echo "Services are running "
    killCurrentRunning
    startService
  else
    echo "============="
    echo "start gateway"
    JAR_NAME=$(find ./lab_jar -name "${GATEWAY}")
    JAVA_OPT=${MEM_256}

    echo "Starting ${JAR_NAME}"
    echo "${JAVA_OPT_FINAL}"

    nohup "$JAVA_HOME"/bin/java $JAVA_OPT -jar "${JAR_NAME}" >> "log/${JAR_NAME##*/}.log" 2>&1 &

    echo "start "
    for JAR in ${MAX_MEM}
    do
      JAR_NAME=$(find ./lab_jar -name "${JAR}")
      JAVA_OPT=${MEM_512}
      echo "Starting ${JAR_NAME##*/}"
      nohup "$JAVA_HOME"/bin/java $JAVA_OPT -jar "${JAR_NAME}"  >> "log/${JAR_NAME##*/}.log" 2>&1 &
      echo "${JAR_NAME##*/} started"
    done

    echo "============="
    echo "start "
    for JAR in ${MED_MEM}
    do
      JAR_NAME=$(find ./lab_jar -name "${JAR}")
      JAVA_OPT=${MEM_256}
#      JAVA_OPT_FINAL="${JAVA_OPT} ${JAVA_OPTS}"
      echo "Starting ${JAR_NAME}"
      echo "${JAVA_OPT_FINAL}"
      nohup "$JAVA_HOME"/bin/java $JAVA_OPT -jar "${JAR_NAME}" >> "log/${JAR_NAME##*/}.log" 2>&1 &
    done
    echo "============="
    echo "start "
    for JAR in ${MIN_MEM}
    do
      JAR_NAME=$(find ./lab_jar -name "${JAR}")
      JAVA_OPT=${MEM_128}
#      JAVA_OPT_FINAL="${JAVA_OPT} ${JAVA_OPTS}"
      echo "Starting ${JAR_NAME}"
      nohup "${JAVA_HOME}"/bin/java $JAVA_OPT -jar "${JAR_NAME}" >> "log/${JAR_NAME##*/}.log" 2>&1 &
    done


  fi
}

startService
