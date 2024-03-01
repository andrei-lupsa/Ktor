package com.learning.ui.cart

import com.learning.data.Cart
import com.learning.ui.Endpoints
import com.learning.ui.GeneralViewTemplate
import io.ktor.server.html.*
import kotlinx.html.*

class CartTemplate(private val cart: Cart) : Template<HTML> {
    private val basicTemplate = GeneralViewTemplate(cart)

    override fun HTML.apply() {
        insert(basicTemplate) {
            content {
                div(classes = "mt-2 row") {
                    h2 { +"Your shopping cart" }
                }
                table(classes = "table table-striped") {
                    thead {
                        tr {
                            th(scope = ThScope.col) { +"Title" }
                            th(scope = ThScope.col) { +"Author" }
                            th(scope = ThScope.col) { +"Price" }
                            th(scope = ThScope.col) { +"Quantity" }
                            th(scope = ThScope.col) { +"Sum" }
                            th(scope = ThScope.col) { +"" }
                        }
                    }
                    tbody {
                        cart.entries.forEach {
                            tr {
                                td { +it.book.title }
                                td { +it.book.author }
                                td { +"${it.book.price}" }
                                td { +"${it.qty}" }
                                td { +"${it.sum}" }
                                td {
                                    form(
                                        method = FormMethod.post,
                                        encType = FormEncType.multipartFormData,
                                        action = Endpoints.REMOVE_FROM_CART.url
                                    ) {
                                        button(classes = "btn btn-success", type = ButtonType.submit) {
                                            +"Remove 1 from cart"
                                        }
                                        input(type = InputType.hidden, name = "bookid") {
                                            value = "${it.book.id}"
                                        }
                                    }
                                }
                            }
                        }
                        tr { }
                        tr {
                            td { +"Sum" }
                            td { }
                            td { }
                            td { +"${cart.qtyTotal}" }
                            td { +"${cart.sum}" }
                        }
                    }
                }
                div(classes = "row mt-3") {
                    form(
                        method = FormMethod.get,
                        encType = FormEncType.multipartFormData,
                        action = Endpoints.RECEIPT.url
                    ) {
                        button(classes = "btn btn-warning", type = ButtonType.submit) {
                            +"Check out and pay"
                        }
                    }
                }
            }
        }
    }
}