#!/bin/bash

INSTANCES_DIR="pmed_instances/"
OUTPUT_BASE_DIR="result/"
MAIN_CLASS="Main"
BIN_DIR="bin"
SRC_DIR="src"

compile_program() {
  mkdir -p "$BIN_DIR"
  javac -d "$BIN_DIR" -sourcepath "$SRC_DIR" "$SRC_DIR/$MAIN_CLASS.java"
  if [ $? -ne 0 ]; then
    echo "Compilation failed. Check the code and try again."
    exit 1
  fi
}

clear_java_cache() {
  jcmd | grep "Main" | awk '{print $1}' | xargs -r jcmd >/dev/null 2>&1
}

execute_program() {
  local instance=$1
  local output_file=$2
  START_TIME=$(date +%s.%N)

  MEMORY_INFO=$(/usr/bin/time -v java -cp "$BIN_DIR" "$MAIN_CLASS" "$instance" "$algorithm" 2>&1 >/tmp/java_output)
  END_TIME=$(date +%s.%N)
  ELAPSED_TIME=$(echo "$END_TIME - $START_TIME" | bc)

  MAX_MEMORY=$(echo "$MEMORY_INFO" | grep "Maximum resident set size" | awk '{print $6}')

  cat /tmp/java_output > "$output_file"
  echo "Execution time: ${ELAPSED_TIME}s" >> "$output_file"
  echo "Max memory used: ${MAX_MEMORY} KB" >> "$output_file"

  # Clean up temporary files
  rm -f /tmp/java_output
}

process_instances() {
  local pattern="$1"

  local output_dir
  if [[ "$algorithm" == "1" ]]; then
    output_dir="${OUTPUT_BASE_DIR}/brute-force"
  else
    output_dir="${OUTPUT_BASE_DIR}/greedy"
  fi
  mkdir -p "$output_dir"

  if [[ $pattern =~ ^\[([0-9]+)-([0-9]+)\]$ ]]; then
    local start=${BASH_REMATCH[1]}
    local end=${BASH_REMATCH[2]}
    for i in $(seq "$start" "$end"); do
      local instance_file="${INSTANCES_DIR}/pmed${i}.txt"
      if [ -f "$instance_file" ]; then
        local output_file="${output_dir}/pmed${i}_result.txt"
        clear_java_cache
        execute_program "$instance_file" "$output_file"
      else
        echo "Instance file $instance_file not found."
      fi
    done
  elif [[ $pattern =~ ^\[([0-9,]+)\]$ ]]; then
    IFS=',' read -ra numbers <<< "${BASH_REMATCH[1]}"
    for i in "${numbers[@]}"; do
      local instance_file="${INSTANCES_DIR}/pmed${i}.txt"
      if [ -f "$instance_file" ]; then
        local output_file="${output_dir}/pmed${i}_result.txt"
        clear_java_cache
        execute_program "$instance_file" "$output_file"
      else
        echo "Instance file $instance_file not found."
      fi
    done
  else
    local instance_file="${INSTANCES_DIR}/${pattern}"
    if [ -f "$instance_file" ]; then
      local output_file="${output_dir}/${pattern}_result.txt"
      clear_java_cache
      execute_program "$instance_file" "$output_file"
    else
      echo "Instance file $instance_file not found."
    fi
  fi
}

if [ "$#" -lt 2 ]; then
  echo "Usage: $0 [-c] <instance_pattern> <algorithm>"
  exit 1
fi

compile=false
while getopts ":c" opt; do
  case $opt in
    c)
      compile=true
      ;;
    \?)
      echo "Invalid option: -$OPTARG"
      exit 1
      ;;
  esac
done
shift $((OPTIND - 1))

pattern="$1"
algorithm="$2"

if [[ ! "$algorithm" =~ ^(1|2)$ ]]; then
  echo "Invalid algorithm. Only '1' or '2' are accepted."
  exit 1
fi

if $compile; then
  compile_program
fi

process_instances "$pattern"
