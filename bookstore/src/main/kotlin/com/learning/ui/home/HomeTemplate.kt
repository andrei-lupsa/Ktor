package com.learning.ui.home

import com.learning.data.Cart
import com.learning.ui.GeneralViewTemplate
import io.ktor.server.html.*
import kotlinx.html.HTML
import kotlinx.html.div
import kotlinx.html.h2
import kotlinx.html.p

class HomeTemplate(cart: Cart?) : Template<HTML> {
    private val basicTemplate = GeneralViewTemplate(cart)
    override fun HTML.apply() {
        insert(basicTemplate) {
            content {
                div(classes = "mt-2") {
                    h2 { +"Welcome to the Bookstore" }
                    p { +"- We have many good deals on a lot of different topics" }
                    p { +"Let us know if you are looking for something that we do not currently have." }
                }
            }
        }
    }
}