package com.learning.ui.checkout

import com.learning.data.Cart
import com.learning.ui.Endpoints
import com.learning.ui.GeneralViewTemplate
import io.ktor.server.html.*
import kotlinx.html.*

class ReceiptTemplate(private val cart: Cart) : Template<HTML> {
    private val basicTemplate = GeneralViewTemplate(cart)

    override fun HTML.apply() {
        insert(basicTemplate) {
            content {
                div(classes = "mt-2 row") {
                    div(classes = "card") {
                        this.style {
                            +"width: 18rem;"
                        }
                        div(classes = "card-body") {
                            h5(classes = "card-title") {
                                +"Thank you for shopping at my Bookstore!"
                            }
                            p(classes = "card-text") {
                                +"""You just purchased ${cart.qtyTotal} books for a total of ${cart.sum} gold coins.
                                    |The titles you purchased are listed here:
                                """.trimMargin()
                                ul {
                                    cart.entries.forEach {
                                        li { +"${it.book.title} written by ${it.book.author}" }
                                    }
                                }
                            }
                            p(classes = "card-text") {
                                +"When you are done reading those come back and get 15% discount on your next purchase."
                            }
                            a(href = Endpoints.BOOKS.url, classes = "btn btn-primary") {
                                +"Shop some more"
                            }
                        }
                    }
                }
            }
        }
    }
}