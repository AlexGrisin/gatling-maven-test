#!/bin/bash
#launch with parameters:
# $1 - environment (demo, smoke, etc.)
# $2 - pod type (admin, store, etc.)
#Example: ./port-forward.sh demo store

JMX_PORT=9003
LOCAL_PORT=11000

env=$1

if [ $2 = "all" ]; then
   type="store|cron"
else
  type=$2
fi

pods=`kubectl get pods -n $env -o wide | grep -i -E "$type" | awk '{print $1}'`

for pod in $pods
do
    let "LOCAL_PORT += 1"
	#kill all processes listening on a specific port
    lsof -n -i4TCP:$LOCAL_PORT | grep LISTEN | awk '{ print $2 }' | xargs kill
    #run port forward command in the background and keep it running
    nohup kubectl port-forward -n $env $pod $LOCAL_PORT:$JMX_PORT &>/dev/null &
    echo "Port forward $pod to $LOCAL_PORT"
done