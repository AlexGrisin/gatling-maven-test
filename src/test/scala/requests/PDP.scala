package requests

import com.typesafe.scalalogging.Logger
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.structure.ChainBuilder
import org.slf4j.LoggerFactory

object PDP {

	val logger: Logger =
		Logger(LoggerFactory.getLogger(getClass.getName))

	val sessionId: Long = 1559572709603L

	def goToProduct(productId: String): ChainBuilder =
		exec(
			http("Go to PDP")
				.get("gb/c-p/" + productId)
				.check(status.is(200))
				.check(css("body.product-detail").exists)
		).exec(session => {
			session
		})

	def addToCartProduct(productId: String): ChainBuilder =
		exec(
			http("Add to cart")
				.post("gb/shopping-bag/add/" + productId)
				.queryParam("d", sessionId)
				.header("X-CSRF-Token", "${csrfToken}")
				.check(status.is(200))
				.check(bodyString.saveAs("RESPONSE_ADD_TO_CART"))
				.check(jsonPath("$.cartGuid").saveAs("cartGuid"))
		).exitHereIfFailed
			.exec(session => {
				logger.debug("${RESPONSE_ADD_TO_CART}")
				logger.debug("add product to cart: " + productId)
				session
			})

}
