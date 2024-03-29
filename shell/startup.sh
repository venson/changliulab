#!/bin/bash

#JAVA_HOME=/Library/Java/JavaVirtualMachines/temurin-8.jdk/Contents/Home
JAVA="$JAVA_HOME/bin/java"
BASE_DIR=$(cd "$(dirname "$0")"/.. || exit; pwd)
#MEM_64="-Xms64M -Xmx64M"
MEM_128="-Xms64M -Xmx128M "
MEM_256="-Xms64M -Xmx256M "
MEM_512="-Xms64M -Xmx512M "

GATEWAY="*changliulab-gateway*.jar"
MAX_MEM="*changliulab-service-edu*.jar"
MED_MEM=""
MIN_MEM="*changliulab-service-msm*.jar *changliulab-service-acl*.jar *changliulab-service-oss*.jar *changliulab-service-cms*.jar"

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

startService(){
  echo "$BASE_DIR"
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

    echo "nohup $JAVA -jar $JAVA_OPT  ${JAR_NAME} >> /dev/null 2>&1 &"
    nohup "$JAVA" -jar "$JAVA_OPT"  "${JAR_NAME}" >> "/dev/null" 2>&1 &
    sleep $START_WAIT_TIMEOUT


    echo "start "
    for JAR in ${MAX_MEM}
    do
      JAR_NAME=$(find ./lab_jar -name "${JAR}")
      JAVA_OPT=${MEM_512}
      echo "Starting ${JAR_NAME##*/}"
      nohup "$JAVA_HOME"/bin/java "$JAVA_OPT" -jar "${JAR_NAME}"  >> "/dev/null" 2>&1 &
      echo "${JAR_NAME##*/} started"
    sleep $START_WAIT_TIMEOUT
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
      nohup "$JAVA_HOME"/bin/java "$JAVA_OPT" -jar "${JAR_NAME}" >> "/dev/null" 2>&1 &
      sleep $START_WAIT_TIMEOUT
    done
    echo "============="
    echo "start "
    for JAR in ${MIN_MEM}
    do
      JAR_NAME=$(find ./lab_jar -name "${JAR}")
      JAVA_OPT=${MEM_128}
#      JAVA_OPT_FINAL="${JAVA_OPT} ${}"
      echo "Starting ${JAR_NAME}"
      nohup "${JAVA_HOME}"/bin/java "$JAVA_OPT" -jar "${JAR_NAME}" >> "/dev/null" 2>&1 &
      sleep $START_WAIT_TIMEOUT
    done


  fi
}

startService
