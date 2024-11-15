#!/bin/bash

usage() {
  echo "Usage: $0 -n <num_vertices> <method>"
  echo "<method>: '1' for twoDisjointPaths, '2' for articulations, or '3' for tarjan"
  exit 1
}

if [ $# -lt 3 ]; then
  usage
fi

N=""
METHOD=""

while getopts ":n:" opt; do
  case $opt in
    n) N=$OPTARG ;;
    \?) echo "Invalid option -$OPTARG" >&2; usage ;;
    :) echo "Option -$OPTARG requires an argument." >&2; usage ;;
  esac
done

shift $((OPTIND - 1))

METHOD=$1
if [[ -z "$METHOD" || ! "$METHOD" =~ ^[1-3]$ ]]; then
  usage
fi

if [[ -z "$N" ]]; then
  usage
fi

readonly FILE_PATH="graph-files/test/test.txt"
readonly FILE_PATH_RESP="graph-files/test/test_resp.txt"
readonly TESTER_OUTPUT_FILE="graph-files/test/tester_output.txt"

mkdir -p graph-files/test

python3 test_generator.py "$N" "$FILE_PATH" "$FILE_PATH_RESP"

# javac -d bin -sourcepath src $(find src -name "*.java")

sudo sync; sudo sh -c 'echo 3 > /proc/sys/vm/drop_caches'

start_time=$(date +%s%N)
java -Xss32m -cp bin Tester "$METHOD" "$FILE_PATH" > "$TESTER_OUTPUT_FILE"
end_time=$(date +%s%N)

elapsed_time=$(( (end_time - start_time) / 1000000 ))
echo "Execution time: $elapsed_time ms"

# process_output() {
#     echo "$1" | tr -d '[]' | tr ',' ' ' | xargs -n1 | sort -n | xargs
# }

# tester_blocks=$(process_output "$(cat $TESTER_OUTPUT_FILE)")
# python_blocks=$(process_output "$(cat $FILE_PATH_RESP)")

# if [[ "$tester_blocks" == "$python_blocks" ]]; then
#     echo "The outputs match!"
# else
#     echo "The outputs do not match!"
# fi
