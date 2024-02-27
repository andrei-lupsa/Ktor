package com.learning.data

import com.mongodb.client.model.Filters.empty
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.FindOneAndReplaceOptions
import com.mongodb.client.model.ReturnDocument
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoCollection
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.bson.Document
import org.bson.types.ObjectId

object DataManager {
    private val bookCollection: MongoCollection<BookData>

    init {
        val mongoClient = MongoClient.create() //default mongodb://localhost
        val database = mongoClient.getDatabase("bookstore")
        bookCollection = database.getCollection("books")
        runBlocking {
            bookCollection.deleteMany(empty())
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
        return bookCollection.find().map { it.toBook() }.toList()
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
}