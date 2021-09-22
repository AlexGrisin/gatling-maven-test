#Monitor JVM on pods

###Install:

* Execute port-forward.sh to expose pods to localhost (JMX port 9003)

```
./port-forward.sh <namespace> <podtype>
./port-forward.sh smoke store
./port-forward.sh demo all
```

* Install VisualVM + Plugins(MBeans, KillApplication, Tracer)