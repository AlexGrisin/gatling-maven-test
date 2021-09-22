package scenarios

import config.SimulationConfig
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.feeder.SourceFeederBuilder
import io.gatling.core.session.Expression
import io.gatling.core.structure.ScenarioBuilder
import requests.{Cart, Home, PDP, PLP, Token}

object SimpleScenario {

	def accessHomePage: ScenarioBuilder =
		scenario("Access home page")
			.exec(Home.open)
			.pause(SimulationConfig.PAUSE_TIME)

	def singleUserJourney(csvFeeder: SourceFeederBuilder[String]): ScenarioBuilder =
		scenario("Simple user journey simulation")
			.feed(csvFeeder)
			.exec(Home.open)
			.pause(SimulationConfig.PAUSE_TIME)
			.exec(PLP.goToCategory("${category}"))
			.pause(SimulationConfig.PAUSE_TIME)
			.exec(PDP.goToProduct("${product}"))
			.pause(SimulationConfig.PAUSE_TIME)
			.exec(Cart.open)
			.pause(SimulationConfig.PAUSE_TIME)

	def multipleUserJourney(csvFeeder: SourceFeederBuilder[String], users: Expression[Int]): ScenarioBuilder =
		scenario("Simple user journey simulation - Repeated")
			.repeat(users) {
				feed(csvFeeder)
					.exec(Home.open)
					.pause(SimulationConfig.PAUSE_TIME)
					.exec(PLP.goToCategory("${category}"))
					.pause(SimulationConfig.PAUSE_TIME)
					.exec(PDP.goToProduct("${product}"))
					.pause(SimulationConfig.PAUSE_TIME)
			}

	def foreverUserJourney(csvFeeder: SourceFeederBuilder[String]): ScenarioBuilder =
		scenario("Simple user journey simulation - Forever Loop")
			.feed(csvFeeder)
			.forever() {
				Home.open
					.pause(SimulationConfig.PAUSE_TIME)
				PLP.goToCategory("${category}")
					.pause(SimulationConfig.PAUSE_TIME)
				PDP.goToProduct("${product}")
					.pause(SimulationConfig.PAUSE_TIME)
			}

	def addToCartAndBrowse(csvFeeder: SourceFeederBuilder[String]): ScenarioBuilder =
		scenario("Simple user journey simulation - Forever Loop")
			.feed(csvFeeder)
			//add to cart
			.exec(Token.get)
			.exec(Home.open)
			.pause(SimulationConfig.PAUSE_TIME)
			.exec(PLP.goToCategory("${category}"))
			.pause(SimulationConfig.PAUSE_TIME)
			.exec(PDP.goToProduct("${product}"))
			.pause(SimulationConfig.PAUSE_TIME)
			.exec(PDP.addToCartProduct("${product}"))
			.pause(SimulationConfig.PAUSE_TIME)
			.exec(Cart.open)
			.exec(Cart.checkCart("${product}"))
			.pause(SimulationConfig.PAUSE_TIME)
			//continue browsing through the web site
			.forever() {
				exec(
					Token.get,
					Home.open,
					PLP.goToCategory("${category}"),
					PDP.goToProduct("${product}"),
					PDP.addToCartProduct("${product}"),
					Cart.checkCart("${product}")
				)
				.pause(SimulationConfig.PAUSE_TIME)
			}

}
