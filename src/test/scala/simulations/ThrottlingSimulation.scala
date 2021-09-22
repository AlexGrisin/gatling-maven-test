package simulations

import config.{Environment, SimulationConfig}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import scenarios.AccountScenario

class ThrottlingSimulation extends Simulation {

	val httpProtocol: HttpProtocolBuilder = http
		.baseUrl(Environment.baseUrl)
		.shareConnections
		.inferHtmlResources()
		.acceptHeader("application/json")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;")
		.userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.80 Safari/537.36")
		.basicAuth(Environment.basicAuthLogin, Environment.basicAuthPassword)

	setUp(
		AccountScenario.getAccountStatus
			.inject(constantUsersPerSec(SimulationConfig.TARGET_USERS) during SimulationConfig.TOTAL_DURATION)
	)
		.throttle(
			reachRps(SimulationConfig.TARGET_RPS) in SimulationConfig.RAMP_UP_DURATION,
			holdFor(SimulationConfig.HOLD_FOR_DURATION)
		)
		.assertions(
			global.successfulRequests.percent.gte(SimulationConfig.SUCCESS_RESPONSE_RATE_PERCENTAGE)
		)
		.protocols(httpProtocol)

}
