#Gatling maven tests

###Run Gatling tests:

Gatling simulation:
```
mvn gatling:test
```
```
mvn gatling:test -Dgatling.simulationClass=simulations.BasicSimulation
```

Gatling recorder:
```
mvn clean gatling:recorder
```


## Scenario execution

https://gatling.io/docs/3.1/general/simulation_setup/

### Injection

The definition of the injection profile of users is done with the inject method. This method takes as argument a sequence of injection steps that will be processed sequentially.

```
  setUp(
    scn.inject(
      rampUsersPerSec(1) to 10 during (1 minutes)
      constantUsersPerSec(20) during (15 seconds)
      rampUsersPerSec(10) to 20 during (10 minutes)
      atOnceUsers(1)
    )
  ).protocols(httpProtocol)
```
  
### Throttling

If you want to reason in terms of requests per second and not in terms of concurrent users

```
  setUp(scn.inject(constantUsersPerSec(20) during Constants.DURATION)).throttle(
    reachRps(20) in Constants.RAMP_UP_TIME,
    holdFor(Constants.HOLD_FOR_TIME),
    jumpToRps(10),
    holdFor(Constants.HOLD_FOR_TIME)
  )
```