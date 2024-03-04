package com.learning.data

import com.learning.routes.Session
import com.mongodb.client.model.Filters.*
import com.mongodb.client.model.FindOneAndReplaceOptions
import com.mongodb.client.model.ReturnDocument
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoCollection
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.bson.Document
import org.bson.types.ObjectId
import org.slf4j.LoggerFactory

object DataManager {
    private val bookCollection: MongoCollection<BookData>
    private val cartCollection: MongoCollection<Cart>
    private val log = LoggerFactory.getLogger(DataManager.javaClass)

    init {
        val mongoClient = MongoClient.create("mongodb://mymongo")
        val database = mongoClient.getDatabase("bookstore")
        bookCollection = database.getCollection("books")
        cartCollection = database.getCollection("cart")
        runBlocking {
            bookCollection.deleteMany(empty())
            cartCollection.deleteMany(empty())
            newBook(Book("How to grow apples", "Mr. Appleton", 100f))
            newBook(Book("How to grow oranges", "Mr. Orangeton", 90f))
            newBook(Book("How to grow lemons", "Mr. Lemon", 110f))
            newBook(Book("How to grow pineapples", "Mr. Pineapple", 100f))
            newBook(Book("How to grow pears", "Mr. Pears", 120f))
            newBook(Book("How to grow coconuts", "Mr. Coconut", 130f))
            newBook(Book("How to grow bananas", "Mr. Appleton", 120f))
        }
    }

    suspend fun findBook(bookId: String): Book? {
        return bookCollection.find(eq("_id", ObjectId(bookId))).singleOrNull()?.toBook()
    }

    suspend fun newBook(book: Book): Book? {
        return bookCollection.insertOne(book.toData())
            .insertedId?.let { bookCollection.find(eq("_id", it)).first().toBook() }
    }

    suspend fun updateBook(book: Book): Book? {
        val bookData = book.toData()
        return bookCollection.findOneAndReplace(
            eq("_id", bookData.id), bookData,
            FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER)
        )?.toBook()
    }

    suspend fun deleteBook(bookId: String): Book? {
        return bookCollection.findOneAndDelete(eq("_id", ObjectId(bookId)))?.toBook()
    }

    suspend fun allBooks(): List<Book> {
        return bookCollection.find()
            .sort(Document(mapOf("title" to 1, "_id" to -1)))
            .map { it.toBook() }
            .toList()
    }

    suspend fun sortedBooks(sortby: String, asc: Boolean): List<Book> {
        val pageno = 1
        val pageSize = 1000
        val ascInt = if (asc) 1 else -1

        return bookCollection.find()
            .sort(Document(mapOf(sortby to ascInt, "_id" to -1)))
            .skip(pageSize * (pageno - 1))
            .limit(pageSize)
            .map { it.toBook() }
            .toList()
    }

    suspend fun searchBooks(str: String): List<Book> {
        return bookCollection.find(
            or(
                regex("title", ".*$str.*"),
                regex("author", ".*$str.*")
            )
        )
            .sort(Document(mapOf("title" to 1, "_id" to -1)))
            .map { it.toBook() }
            .toList()
    }

    suspend fun updateCart(cart: Cart) {
        val replaceOne = cartCollection.replaceOne(eq("username", cart.username), cart)
        log.info("Update result: $replaceOne")
    }

    suspend fun addBook(session: Session, book: BookData) {
        val cartForUser = cartForUser(session)
        cartForUser.addBook(book)
        updateCart(cartForUser)
    }

    suspend fun cartForUser(session: Session): Cart {
        val find = cartCollection.find(eq("username", session.username))
        if (find.count() == 0) {
            val cart = Cart(session.username)
            return cartCollection.insertOne(cart).insertedId!!
                .let { cartCollection.find(eq("_id", it)).first() }
        } else return find.first()
    }

    suspend fun getBookWithId(bookId: String): BookData {
        log.info("Get book with id: $bookId")
        return bookCollection.find(eq("_id", ObjectId(bookId))).first()
    }

    suspend fun removeBook(session: Session, book: BookData) {
        val cartForUser = cartForUser(session)
        cartForUser.removeBook(book)
        updateCart(cartForUser)
    }
}