package requests

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.structure.ChainBuilder

object Home {

	def open: ChainBuilder =
		exec(
			http("home")
				.get("gb/")
				.check(status.is(200))
				.check(css("body.home").exists)
		).exec(session => {
			session
		})

}
