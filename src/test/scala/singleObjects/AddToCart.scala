package singleObjects

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.check.HttpCheck

object AddToCart {

	lazy val request0 = "AddToCart"

	// Checks for preparing add to cart.
	val mAddToCartChecks: Seq[HttpCheck] = Seq(
		// Add to cart form action
		css("form[id^=product_addtocart_form]", "action").find.optional.saveAs("formAction"),
		//		// Add to cart form method (GET/POST)
		css("form[id^=product_addtocart_form]", "method").find.optional.saveAs("formMethod"),
		//		// form key aKa CSRF token
		css("input[name=form_key]", "value").find.optional.saveAs("formKey"),
		//		// product ID
		css("input[name=product]", "value").find.optional.saveAs("product"),
		// item ID
		css("input[name=product]", "value").find.optional.saveAs("item"),
		// Related products
		css("input[name=related_product]", "value").find.optional.saveAs("relatedProduct"),
		// Selected configurable option
		css("input[name=selected_configurable_option]", "value").find.optional.saveAs("selectedConfigOption"))
	//		// Bundled options form element name
	//		css("[name^=bundle_option\\[]", "name").findAll.optional.saveAs("bundleOptionSelectName"),
	//		// Bundled options form element values
	//		css("[name^=bundle_option\\[]", "value").findAll.optional.saveAs("bundleOptionSelectValue"))

	val mAddToCartAction =

		exec((session: Session) => {
			val formAction = session("formAction")
			val formKey = session("formKey")
			//			val relatedProduct = session("relatedProduct")
			val product = session("product")

			session
				.set("qty", 1) // you may use a random number
		})
			.exec(
				http(request0)
					.post("${formAction}")
					.headers(Headers.headersPost)
					.formParamMap(Map(
						"product" -> "${product}",
						"selected_configurable_options" -> "${selectedConfigOption}",
						"related_product" -> "${relatedProduct}" ,
						"item" ->  "${item}",
						"form_key" -> "${formKey}",
						"qty" -> "${qty}"
					)).asMultipartForm
					.disableFollowRedirect
					.check(status.in(200 to 307)))
}
