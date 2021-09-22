package scenarios

import config.SimulationConfig
import io.gatling.core.Predef.scenario
import io.gatling.core.feeder.SourceFeederBuilder
import io.gatling.core.structure.ScenarioBuilder
import requests.{Cart, Home, PDP, PLP, Token}

object CartScenario {

	def addToCart(csvFeeder: SourceFeederBuilder[String]): ScenarioBuilder =
		scenario("Add to cart simulation")
			.feed(csvFeeder)
			.exec(
				Token.get,
				Home.open,
				PLP.goToCategory("${category}"),
				PDP.goToProduct("${product}"),
				PDP.addToCartProduct("${product}"),
				Cart.checkCart("${product}")
			)
			.pause(SimulationConfig.PAUSE_TIME)

}
