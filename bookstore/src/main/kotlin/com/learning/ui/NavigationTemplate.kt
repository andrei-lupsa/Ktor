package com.learning.ui

import com.learning.data.Cart
import io.ktor.server.html.*
import kotlinx.html.*

class NavigationTemplate(private val cart: Cart?) : Template<FlowContent> {
    val menuitems = PlaceholderList<UL, FlowContent>()

    override fun FlowContent.apply() {
        div {
            nav(classes = "navbar navbar-expand-md navbar-dark bg-dark") {
                a(classes = "navbar-brand", href = "#") { +"My Bookstore" }
                button(classes = "navbar-toggler", type = ButtonType.button) {
                    attributes["data-toggle"] = "collapse"
                    attributes["data-target"] = "#navbarsExampleDefault"
                    attributes["aria-controls"] = "navbarsExampleDefault"
                    attributes["aria-expanded"] = "false"
                    attributes["aria-label"] = "Toggle navigation"
                    span(classes = "navbar-toggler-icon")
                }
                div(classes = "collapse navbar-collapse") {
                    id = "navbarsExampleDefault"
                    ul(classes = "navbar-nav mr-auto") {
                        each(menuitems) {
                            li {
                                insert(it)
                            }
                        }
                    }
                }
                div {
                    if (cart != null) {
                        form(action = Endpoints.CART.url) {
                            button(classes = "btn btn-danger", type = ButtonType.submit) {
                                +"Items in cart: ${cart.qtyTotal}, total price: ${cart.sum}"
                            }
                        }
                    }
                }
            }
        }
    }
}