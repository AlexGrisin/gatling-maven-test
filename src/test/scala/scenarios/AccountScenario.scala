package scenarios

import io.gatling.core.Predef.scenario
import io.gatling.core.structure.ScenarioBuilder
import requests.AccountStatus

object AccountScenario {

	def getAccountStatus: ScenarioBuilder =
		scenario("Get account status")
			.exec(AccountStatus.get)

}
