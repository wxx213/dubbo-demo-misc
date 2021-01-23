#!/bin/bash

set -e

if [ -n "$1" ]; then
	num=$1
else
	num=100
fi
RESULT_FILE=result

rm -f $RESULT_FILE
touch $RESULT_FILE

for((i=0;i<$num;i++)) do
	curl -s http://localhost:8087/test >> $RESULT_FILE
done

echo "`cat $RESULT_FILE | sort | uniq -c | sort -n`"
