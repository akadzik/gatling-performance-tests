package singleObjects

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import CategoryFromHomePage._

object HomePage {

	lazy val requestName = "HomePage"

	val homePage = exec(http(requestName)
		.get("/")
		.headers(Headers.headersGet)
		.check(mCategoryFromHomepageChecks:_*)
		.check(status.is(200)))
		.exitHereIfFailed
}
