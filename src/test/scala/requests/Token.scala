package requests

import com.typesafe.scalalogging.Logger
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

object Token {

	val logger: Logger =
		Logger(LoggerFactory.getLogger(getClass.getName))

	val sessionId: Long = 1559572709603L

	def get: ChainBuilder =
		exec(
			http("get csrf token")
				.get("gb/_s/ajax/dynamic/")
				.queryParam("_", sessionId)
				.check(status.is(200))
				.check(jsonPath("$.csrfToken").saveAs("csrfToken"))
		).exec(session => {
			logger.debug("csrf token: " + session("csrfToken").as[String])
			session
		})
}
