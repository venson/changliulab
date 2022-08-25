#!/bin/bash bash

export GATEWAY=""
export JAVA_HOME=${JAVA_HOME}
export JAVA="$JAVA_HOME/bin/java"
export BASE_DIR=$(cd $(dirname "$0")/.. || exit; pwd)
export MEM_64="-Xms64m -Xmx64m -Xmn64m"
export MEM_128="-Xms128m -Xmx128m -Xmn128m"
export MEM_256="-Xms256m -Xmx256m -Xmn256m"
export MEM_512="-Xms512m -Xmx512m -Xmn512m"

export MAX_MEM="*changliulab-service-edu*.jar"

export MED_MEM="*changliulab-service-oss*.jar *changliulab-service-cms*.jar *changliulab-gateway*.jar"

export MIN_MEM="*changliulab-service-msm*.jar *changliulab-service-acl*.jar "

export SERVICE_PREFIX="*changliulab-"

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

JAVA_OPTS="$JAVA_OPTS -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:InitiatingHeapOccupancyPercent=55 -XX:G1ReservePercent=15"
JAVA_OPTS="$JAVA_OPTS -XX:MaxTenuringThreshold=15 -XX:ParallelGCThreads=8 -XX:+UnlockExperimentalVMOptions"
JAVA_OPTS="$JAVA_OPTS -XX:+DisableExplicitGC -XX:+UseFastAccessorMethods -XX:SoftRefLRUPolicyMSPerMB=0"
JAVA_OPTS="$JAVA_OPTS -XX:+PrintGC -XX:+PrintHeapAtGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps"
JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCTimeStamps -XX:+PrintGCCause"
#JAVA_OPTS="$JAVA_OPTS -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$prog_log_dir/"
#JAVA_OPTS="$JAVA_OPTS -Xloggc:$prog_gc_log"
#JAVA_OPTS="$JAVA_OPTS $BASE_OPTS $TLIB_OPTS"
startService(){
  checkPid
  if [  "${pSid}" != 0 ]; then
    echo "Services are running "
    killCurrentRunning
  else
    echo "start gateway"
    JAR_NAME=$(find . -name "${MAX_MEM}")
    JAVA_OPT=${MEM_256}
    JAVA_OPT_FINAL="${JAVA_OPT} ${JAVA_OPTS}"

    echo "${JAR_NAME}"
    echo "${JAVA_OPT_FINAL}"

    nohup "${JAVA_HOME}"/bin/java "${JAVA_OPT_FINAL}" -jar "${JAR_NAME}" start > "/dev/null" 2>&1 &



  fi
}

startService
