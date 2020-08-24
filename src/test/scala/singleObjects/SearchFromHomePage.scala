package singleObjects

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import ProductsFromCategory._

import scala.util.Random

object SearchFromHomePage {

	private lazy val requestName0 = "SearchAjaxSuggest"
	private lazy val requestName00 = "SearchFromHomePage"
	private lazy val requestName1 = "SearchPageSort"
	private lazy val requestName2 = "SearchPageSort+Dir"
	private lazy val requestName3 = "SearchPageSort+Dir+PageNum"
	private lazy val requestName4 = "SearchPageFilter"

	val feederSearchPageKey = csv("data/searchPageKey.csv").random
	val feederSortCategory = csv("data/pageSorting.csv").random
	val feederFilterCategory = csv("data/pageFiltering.csv").random
	val quantity = csv ("data/quantity.csv").random
	val rnd = new Random()
	val max = 1000000000
	val min = 100000000

	val mSearchFromHomepageAction = feed(feederSearchPageKey).feed(feederSortCategory).feed(quantity)
		.exec(http(requestName0)
			.get("/search/ajax/suggest/")
			.queryParam("q", "${keys}")
			.queryParam( "_", _ => rnd.nextInt(max+1-min)+min)
			//        .check(css("#search", "value").saveAs("searchedKey"))
			.check(status.is(200)))

		.exec(http(requestName00)
			.get("/catalogsearch/result/index/?q=${keys}")
			.check(css("#search", "value").find.saveAs("searchedKey"))
			.check(status.is(200)))

	val mSearchSort = feed(feederSortCategory)
		.exec(http(requestName1)
			.get("/catalogsearch/result/index/?q=${searchedKey}")
			.queryParam("product_list_order", "${sort1}")
//			.queryParam("shopbyAjax", "1") // if needed - additional modules
			//      .check(mProductFromCategoryChecks: _*)
			.check(status.is(200)))

	  val mSearchSortDir = feed(feederSortCategory)
	    .exec(http(requestName2)
	      .get("/catalogsearch/result/index/?q=${searchedKey}")
	      .queryParam("product_list_order", "${sort1}")
	      .queryParam("product_list_dir", "desc")
//	      .queryParam("shopbyAjax", "1")
	//      .check(mProductFromCategoryChecks: _*)
	      .check(status.is(200)))


	  val mSearchSortDirPageNo = feed(feederSortCategory).feed(quantity)
	    .exec(http(requestName3)
	      .get("/catalogsearch/result/index/?q=${searchedKey}")
	      .queryParam("product_list_order", "${sort1}")
	      .queryParam("product_list_dir", "desc")
	      .queryParam("p", "${quantity}")
//	      .queryParam("shopbyAjax", "1")
	//      .check(mProductFromCategoryChecks: _*)
	      .check(status.is(200)))

	val mSearchFilter = feed(feederSortCategory).feed(feederFilterCategory).feed(quantity)
		.exec(http(requestName4)
			.get("/catalogsearch/result/index/?q=${searchedKey}")
			.queryParam("amshopby[colors][]","${color}") //Amasty Improved Layered Navigation module installed in that case
//			.queryParam("shopbyAjax", "1")
			//        .check(mProductFromCategoryChecks: _*)
			.check(status.is(200)))

}
