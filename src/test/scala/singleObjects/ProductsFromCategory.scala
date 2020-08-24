package singleObjects

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.check.HttpCheck
import scala.util.Random
import AddToCart._

object ProductsFromCategory {

	private lazy val requestName0 = "ProductPageFromCategoryPage"

	val mProductFromCategoryChecks: Seq[HttpCheck] = Seq(
		css(".product-item-photo","href").findAll.saveAs("products")
	)
	val mProductFromCategoryAction =
		exec((session: Session) => {
			val products = session("products").as[Seq[String]]
			val productURL = products(Random.nextInt(products.length))
			//			println("Chose " + productURL) // for console debugging
			session.set("productURL", productURL)
		})
			.exec(
				http(requestName0)
					.get("${productURL}")
					.check(status.is(200))
					.headers(Headers.headersGet)
					.check(mAddToCartChecks:_*)
			)

}
