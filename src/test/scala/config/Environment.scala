package config

import com.typesafe.config.{Config, ConfigFactory}

object Environment {

	private val conf: Config = ConfigFactory.load()
	private val name: String = "mulberry-" + System.getProperty("env", "demo")

	val baseUrl: String = conf.getString(name + ".baseUrl")
	val basicAuthLogin: String = conf.getString(name + ".login")
	val basicAuthPassword: String = conf.getString(name + ".password")

}
