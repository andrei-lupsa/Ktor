package com.learning.routes

import com.learning.data.Book
import com.learning.data.DataManager
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@Resource("/list")
data class BookListResource(val sortby: String, val asc: Boolean)

fun Route.bookRouting() {
    route("/book") {
        get {//get all books at GET /book
            call.respond(DataManager.books.values)
        }
        get<BookListResource> { bookList ->
            call.respond(DataManager.sortedBooks(bookList.sortby, bookList.asc))
        }
        get("/{id}") {//get book by id
            val bookId = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val book = DataManager.books[bookId] ?: return@get call.respond(HttpStatusCode.NotFound)
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
            val newBook = DataManager.newBook(book)
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