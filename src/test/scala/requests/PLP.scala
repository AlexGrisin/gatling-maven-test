package requests

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.structure.ChainBuilder

object PLP {

	def goToCategory(category: String): ChainBuilder =
		exec(
			http("Go to PLP")
				.get("gb/shop/" + category)
				.check(status.is(200))
				.check(css("body.product-list").exists)
		).exec(session => {
			session
		})

}
