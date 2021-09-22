package requests

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

object AccountStatus {

	val sessionId: Long = 1559572709603L

	def get: ChainBuilder =
		exec(
			http("get account status")
				.get("commercewebservices/v2/gb/users/anonymous/status")
				.queryParam("email", "agrisin@tacitknowledge.com")
				.check(status.is(200))
				.check(jsonPath("$.firstName").is("Alex"))
		).exec(session => {
			session
		})
}
