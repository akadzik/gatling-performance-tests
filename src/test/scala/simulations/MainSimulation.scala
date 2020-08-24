package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import singleObjects.{AddToCart, CartFromProductPage, CategoryFromHomePage, HomePage, HttpConf, ProductsFromCategory, SearchFromHomePage}

import scala.concurrent.duration._

class MainSimulation extends Simulation {

  /*** BEFORE ***/
  before {
    println(s"Running test on ${HttpConf.baseUrl}")
    println(s"Running test with ${HttpConf.userCount} users per each scenario")
    println(s"Ramping users over ${HttpConf.rampDuration} seconds")
    println(s"Total Test duration: ${HttpConf.testDuration} minutes")
    println(s"Running test with random timeRatio factor between: 1*${HttpConf.timeRatio} and 5*${HttpConf.timeRatio} seconds")
  }

  /***************** DEFINITION ************************/
  /*** NORMAL SCENARIOS ***/
  def mScenarioType1() = {
    exec(HomePage.homePage)
      .pause(1 * HttpConf.timeRatio seconds, 5 * HttpConf.timeRatio seconds)
      .exec(CategoryFromHomePage.mCategoryFromHomepageAction)
      .pause(1 * HttpConf.timeRatio seconds, 5 * HttpConf.timeRatio seconds)
      .exec(CategoryFromHomePage.mCategoryPageSort)
      .pause(1 * HttpConf.timeRatio seconds, 5 * HttpConf.timeRatio seconds)
      .exec(CategoryFromHomePage.mCategoryPageSortDir)
    //      .pause(1 * HttpConf.timeRatio seconds, 5 * HttpConf.timeRatio seconds)
    //    .exec(CategoryFromHomePage.mCategoryPageSortDirPageNo)
  }

  def mScenarioType2() = {
    exec(HomePage.homePage)
      .pause(1 * HttpConf.timeRatio seconds, 5 * HttpConf.timeRatio seconds)
      .exec(CategoryFromHomePage.mCategoryFromHomepageAction)
      .pause(1 * HttpConf.timeRatio seconds, 5 * HttpConf.timeRatio seconds)
      .exec(ProductsFromCategory.mProductFromCategoryAction)
      .pause(1 * HttpConf.timeRatio seconds, 5 * HttpConf.timeRatio seconds)
      .exec(AddToCart.mAddToCartAction)
      .pause(1 * HttpConf.timeRatio seconds, 5 * HttpConf.timeRatio seconds)
      .exec(CartFromProductPage.mGotoCartAction)
  }

  def mScenarioType3() = {
    exec(HomePage.homePage)
      .pause(1 * HttpConf.timeRatio seconds, 5 * HttpConf.timeRatio seconds)
      .exec(SearchFromHomePage.mSearchFromHomepageAction)
      .pause(1 * HttpConf.timeRatio seconds, 5 * HttpConf.timeRatio seconds)
      .exec(SearchFromHomePage.mSearchSort)
      .pause(1 * HttpConf.timeRatio seconds, 5 * HttpConf.timeRatio seconds)
      .exec(SearchFromHomePage.mSearchSortDir)
      //      .pause(1 * HttpConf.timeRatio seconds,5 * HttpConf.timeRatio seconds)
      //    .exec(SearchFromHomePage.mSearchSortDirPageNo)
      .pause(1 * HttpConf.timeRatio seconds,5 * HttpConf.timeRatio seconds)
      .exec(SearchFromHomePage.mSearchFilter)
  }

  def mScenarioType4() = {
    exec(HomePage.homePage)
      .pause(1 * HttpConf.timeRatio seconds, 5 * HttpConf.timeRatio seconds)
      .exec(CategoryFromHomePage.mCategoryFromHomepageAction)
  }

  /***************** PREPARATION ************************/
  /*** NORMAL SCENARIOS ***/
  val mScn1 = scenario("mScn1")
    .exec(mScenarioType1())

  val mScn2 = scenario("mScn2")
    .exec(mScenarioType2())

  val mScn3 = scenario("mScn3")
    .exec(mScenarioType3())

  val mScn4 = scenario("mScn4")
    .exec(mScenarioType4())

  /***************** SETUP ************************/
  setUp(
      mScn1.inject(
        rampConcurrentUsers(1) to (HttpConf.userCount) during (HttpConf.rampDuration seconds),
        constantConcurrentUsers(HttpConf.userCount) during (HttpConf.testDuration minutes)),
      mScn2.inject(
        rampConcurrentUsers(1) to (HttpConf.userCount) during (HttpConf.rampDuration seconds),
        constantConcurrentUsers(HttpConf.userCount) during (HttpConf.testDuration minutes)),
      mScn3.inject(
        rampConcurrentUsers(1) to (HttpConf.userCount) during (HttpConf.rampDuration seconds),
        constantConcurrentUsers(HttpConf.userCount) during (HttpConf.testDuration minutes)),
      mScn4.inject(
        rampConcurrentUsers(1) to (HttpConf.userCount) during (HttpConf.rampDuration seconds),
        constantConcurrentUsers(HttpConf.userCount) during (HttpConf.testDuration minutes)),
  )
  .protocols(HttpConf.httpConf)
//      .assertions(
//        global.successfulRequests.percent.gte(97),
//        global.responseTime.max.lte(30000),
//        global.responseTime.mean.lte(6000),
//        global.requestsPerSec.gt(100)
//      )
}
