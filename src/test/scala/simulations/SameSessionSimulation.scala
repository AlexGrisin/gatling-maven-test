package simulations

import config.{Environment, SimulationConfig}
import io.gatling.core.Predef._
import io.gatling.core.feeder.SourceFeederBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import scenarios.SimpleScenario

class SameSessionSimulation extends Simulation {

	val csvFeeder: SourceFeederBuilder[String] = csv("data/mulberry-feeder-demo.csv").random

	val httpProtocol: HttpProtocolBuilder = http
		.baseUrl(Environment.baseUrl)
		.shareConnections
		.inferHtmlResources()
		.acceptHeader("image/webp,*/*")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.5")
		.userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36")
		.header("Connection", "keep-alive")
		.basicAuth(Environment.basicAuthLogin, Environment.basicAuthPassword)

	setUp(
		SimpleScenario.addToCartAndBrowse(csvFeeder).inject(
			atOnceUsers(SimulationConfig.USERS)
		)
	)
		.pauses(constantPauses)
  	.maxDuration(SimulationConfig.DURATION)
		.assertions(
			global.successfulRequests.percent.gte(SimulationConfig.SUCCESS_RESPONSE_RATE_PERCENTAGE),
			global.responseTime.mean.lte(SimulationConfig.RESPONSE_TIME_MEAN_THRESHOLD),
			global.responseTime.max.lte(SimulationConfig.RESPONSE_TIME_MAX_THRESHOLD)
		)
		.protocols(httpProtocol)

}
