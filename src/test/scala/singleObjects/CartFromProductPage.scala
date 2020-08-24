package singleObjects

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.check.HttpCheck


object CartFromProductPage {

	lazy val request0 = "GoToCart"

	// Cart page checks.
	val checksCartPage: Seq[HttpCheck] = Seq(
		// Shopping cart table
		//		css(".cart-summary").saveAs("cartSummary"))
		css(".cart-totals").saveAs("cartSummary"))

	val mGotoCartAction =
		exec(
			http("request0")
				.get("/checkout/cart")
				.headers(Headers.headersGet))
			.exitHereIfFailed
}
