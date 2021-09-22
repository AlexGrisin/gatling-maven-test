package requests

import com.typesafe.scalalogging.Logger
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.structure.ChainBuilder
import org.slf4j.LoggerFactory

object Cart {

	val logger: Logger =
		Logger(LoggerFactory.getLogger(getClass.getName))

	val sessionId: Long = 1559572709603L

	def open: ChainBuilder =
		exec(
			http("home")
				.get("gb/mlb/shopping-bag")
				.check(status.is(200))
		).exec(session => {
			session
		})

	def checkCart(productId: String): ChainBuilder =
		exec(
			http("What is in cart")
				.get("/commercewebservices/v2/gb/users/anonymous/carts/${cartGuid}")
				.queryParam("d", sessionId)
				.check(status.is(200))
				.check(bodyString.saveAs("RESPONSE_IN_CART"))
				.check(jsonPath("$.entries[0].product.code").saveAs("productCode"))
				.check(jsonPath("$.entries[0].product.code").is(productId))
		)
			.exec(session => {
				logger.debug("Cart Body: " + session("RESPONSE_IN_CART").as[String])
				logger.debug("Product code: " + session("productCode").as[String])
				session
			})

}
