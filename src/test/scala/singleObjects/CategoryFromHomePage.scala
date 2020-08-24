package singleObjects

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.check.HttpCheck
import scala.util.Random
import ProductsFromCategory._

object CategoryFromHomePage {

	private lazy val requestName0 = "CategoryFromHomePage"
	private lazy val requestName1 = "CatPageSort"
	private lazy val requestName2 = "CatPageSort+Dir"
	private lazy val requestName3 = "CatPageSort+Dir+PageNum"

	private val feederSortCategory = csv("data/pageSorting.csv").random
	private val quantity = csv("data/quantity.csv").random

	val mCategoryFromHomepageChecks: Seq[HttpCheck] = Seq(
		css(".level-top ", "href").findAll.optional.saveAs("categories"),
		css("li[class~='ui-menu-item'] > a", "href").findAll.optional.saveAs("categories")

//			css("A[role=menuitem]","href").findAll.optional.saveAs("categories"), //sample alternative methods
//		regex("""(?<=href=")(.*?/category.*?)(?=")""").findAll.optional.saveAs("categories") //sample alternative methods
	)

	val mCategoryFromHomepageAction =
		exec((session: Session) => {
			val categories = session("categories").as[Seq[String]]
			val categoryURL = categories(Random.nextInt(categories.length))
//      println("Chose " + categoryURL) // for console debugging
			session.set("categoryURL", categoryURL)
		})
			.exec(
				http(requestName0)
					.get("${categoryURL}")
					.headers(Headers.headersGet)
					.check(mProductFromCategoryChecks: _*)
					.check(status.is(200)))
			.exitHereIfFailed

	val mCategoryPageSort = feed(feederSortCategory)
		.exec(
			http(requestName1)
				.get("${categoryURL}")
				.headers(Headers.headersGet)
				.queryParam("product_list_order", "${sort1}")
//				.queryParam("shopbyAjax", "1") //if needed - additional modules
				.check(mProductFromCategoryChecks: _*)
				.check(status.is(200)))
		.exitHereIfFailed

	val mCategoryPageSortDir = feed(feederSortCategory)
		.exec(http(requestName2)
			.get("${categoryURL}")
			.queryParam("product_list_order", "${sort1}")
			.queryParam("product_list_dir", "desc")
//			.queryParam("shopbyAjax", "1")
			.check(mProductFromCategoryChecks: _*)
			.check(status.is(200)))
		.exitHereIfFailed

	val mCategoryPageSortDirPageNo = feed(feederSortCategory).feed(quantity)
		.exec(http(requestName3)
			.get("${categoryURL}")
			.queryParam("product_list_order", "${sort1}")
			.queryParam("product_list_dir", "desc")
			.queryParam("p", "${quantity}")
//			.queryParam("shopbyAjax", "1")
			.check(mProductFromCategoryChecks: _*)
			.check(status.is(200)))
		.exitHereIfFailed

}
