package com.learning.routes

import com.learning.data.Book
import com.learning.data.DataManager
import com.learning.ui.Endpoints
import com.learning.ui.books.BookTemplate
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.html.i
import org.slf4j.LoggerFactory

@Resource("/list")
data class BookListResource(val sortby: String, val asc: Boolean)

@Resource("/api/book/list")
data class AuthBookListResource(val sortby: String, val asc: Boolean)

fun Route.bookRouting() {
    post(Endpoints.DOBOOKSEARCH.url) {
        val log = LoggerFactory.getLogger("LoginView")
        val multipart = call.receiveMultipart()
        var search = ""
        while (true) {
            val part = multipart.readPart() ?: break
            if (part is PartData.FormItem) {
                log.info("FormItem: ${part.name} = ${part.value}")
                if (part.name == "search") search = part.value
            }
            part.dispose()
        }
        val searchBooks = DataManager.searchBooks(search)
        call.respondHtmlTemplate(BookTemplate(call.sessions.get<Session>(), searchBooks)) {
            searchfilter {
                i { +"Search filter: $search" }
            }
        }
    }
    get(Endpoints.BOOKS.url) {
        call.respondHtmlTemplate(BookTemplate(call.sessions.get<Session>(), DataManager.allBooks())) {}
    }
    authenticate("bookStoreAuth") {
        get<AuthBookListResource> { bookList ->
            call.respond(DataManager.sortedBooks(bookList.sortby, bookList.asc))
        }
    }
    route("/book") {
        get {//get all books at GET /book
            call.respond(DataManager.allBooks())
        }
        get<BookListResource> { bookList ->
            call.respond(DataManager.sortedBooks(bookList.sortby, bookList.asc))
        }
        get("/{id}") {//get book by id
            val bookId = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val book = DataManager.findBook(bookId) ?: return@get call.respond(HttpStatusCode.NotFound)
            call.respond(book)
        }
        post("/{id}") {//update book
            val bookId = call.parameters["id"] ?: return@post call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val book = call.receive<Book>()
            require(bookId == (book.id ?: bookId)) { "Inconsistent book id" }
            val updatedBook = DataManager.updateBook(book.copy(id = bookId))
                ?: return@post call.respond(HttpStatusCode.NotFound)
            call.respond(updatedBook)
        }
        put {//create book
            val book = call.receive<Book>()
            val newBook = DataManager.newBook(book) ?: throw IllegalStateException("No book was saved")
            call.respond(newBook)
        }
        delete("/{id}") {
            val bookId = call.parameters["id"] ?: return@delete call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val deletedBook = DataManager.deleteBook(bookId) ?: return@delete call.respond(HttpStatusCode.NotFound)
            call.respond(deletedBook)
        }
    }
}