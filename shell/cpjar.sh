#!/bin/bash bash

export SERVICE="*changliulab-service*.jar"
export GATEWAY="*changliulab-gateway*.jar"
target_dir="lab_jar/"

gateway_file=$(find ./changliulab -name "${GATEWAY}")
target_gateway_file=$(find ./changliulab -name "${GATEWAY}"|awk -F "/" '{print $NF}')

service_file=$(find ./changliulab -name "${SERVICE}")

mkdir -p ${target_dir}

echo "remove file in ${target_dir}"
rm -rf ${target_dir}*

#echo "cp ""${gateway_file}"" ""${target_gateway_file}"""
echo "copy ${target_gateway_file}"
cp "${gateway_file}" "${target_dir}${target_gateway_file}"

echo "copy service file"
for file in ${service_file}
do
#  echo "${file}"
  jar_name=${file##*/}
  echo "${jar_name}"
#  echo "cp ""${file}"" ${jar_name}"
  cp "${file}" "${target_dir}${jar_name}"

done

