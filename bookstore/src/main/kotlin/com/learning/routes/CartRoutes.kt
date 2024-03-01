package com.learning.routes

import com.learning.data.DataManager
import com.learning.ui.Endpoints
import com.learning.ui.books.BookTemplate
import com.learning.ui.cart.CartTemplate
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.html.i
import org.slf4j.LoggerFactory

fun Route.cartRouting() {
    get(Endpoints.CART.url) {
        val session = call.sessions.get<Session>()!!
        val cart = DataManager.cartForUser(session)
        call.respondHtmlTemplate(CartTemplate(cart)) {}
    }
    post(Endpoints.ADD_TO_CART.url) {
        val log = LoggerFactory.getLogger("Add to cart")
        val multipart = call.receiveMultipart()
        val session = call.sessions.get<Session>()!!
        var bookId = ""
        while (true) {
            val part = multipart.readPart() ?: break
            if (part is PartData.FormItem) {
                log.info("FormItem: ${part.name} = ${part.value}")
                if (part.name == "bookid") bookId = part.value
            }
            part.dispose()
        }
        val book = DataManager.getBookWithId(bookId)
        DataManager.addBook(session, book)
        val cart = DataManager.cartForUser(session)
        call.respondHtmlTemplate(BookTemplate(cart, DataManager.allBooks())) {
            searchfilter {
                i { +"Book added to cart" }
            }
        }
    }
    post(Endpoints.REMOVE_FROM_CART.url) {
        val log = LoggerFactory.getLogger("Remove from cart")
        val multipart = call.receiveMultipart()
        val session = call.sessions.get<Session>()!!
        var bookId = ""
        while (true) {
            val part = multipart.readPart() ?: break
            if (part is PartData.FormItem) {
                log.info("FormItem: ${part.name} = ${part.value}")
                if (part.name == "bookid") bookId = part.value
            }
            part.dispose()
        }
        val book = DataManager.getBookWithId(bookId)
        DataManager.removeBook(session, book)
        val cart = DataManager.cartForUser(session)
        call.respondHtmlTemplate(CartTemplate(cart)) {}
    }

}