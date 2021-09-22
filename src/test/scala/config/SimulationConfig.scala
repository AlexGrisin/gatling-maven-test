package config

import java.util.concurrent.TimeUnit

import scala.concurrent.duration._

object SimulationConfig {

	val PAUSE_TIME: Duration = new FiniteDuration(2, TimeUnit.SECONDS)

	//basic simulation
	val USERS: Int = Integer.valueOf(System.getProperty("target_users", "50"))
	private val durationInMinutes: Int = Integer.valueOf(System.getProperty("duration", "5"))
	val DURATION: FiniteDuration = new FiniteDuration(durationInMinutes, TimeUnit.MINUTES)

	//throttling simulation
	val TARGET_USERS: Int = 50
	val TARGET_RPS: Int = 30
	val TOTAL_DURATION: FiniteDuration = new FiniteDuration(3, TimeUnit.MINUTES)
	val RAMP_UP_DURATION: FiniteDuration = new FiniteDuration(30, TimeUnit.SECONDS)
	val HOLD_FOR_DURATION: FiniteDuration = new FiniteDuration(2, TimeUnit.MINUTES)

	//for asserts
	val SUCCESS_RESPONSE_RATE_PERCENTAGE: Int = 95
	val RESPONSE_TIME_MEAN_THRESHOLD: Int = 5000
	val RESPONSE_TIME_MAX_THRESHOLD: Int = 30000

}
