package com.learning.data

import io.ktor.util.logging.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.reflect.full.declaredMemberProperties

object DataManager {
    private val log = KtorSimpleLogger(DataManager.javaClass.name)

    private val bookId = AtomicInteger()
    val books = hashMapOf<String, Book>()

    init {
        newBook(Book(gimmeId(), "How to grow apples", "Mr. Appleton", 100f))
        newBook(Book(gimmeId(), "How to grow oranges", "Mr. Orangeton", 90f))
        newBook(Book(gimmeId(), "How to grow lemons", "Mr. Lemon", 110f))
        newBook(Book(gimmeId(), "How to grow pineapples", "Mr. Pineapple", 100f))
        newBook(Book(gimmeId(), "How to grow pears", "Mr. Pears", 110f))
        newBook(Book(gimmeId(), "How to grow coconuts", "Mr. Coconut", 130f))
        newBook(Book(gimmeId(), "How to grow bananas", "Mr. Appleton", 120f))
    }

    private fun gimmeId(): String {
        return bookId.getAndIncrement().toString()
    }

    fun newBook(book: Book): Book {
        val bookId = book.id ?: gimmeId()
        books[bookId] = book.copy(id = bookId)
        return books[bookId]!!
    }

    fun updateBook(book: Book): Book? {
        val bookId = book.id!!
        val oldBook = books.replace(bookId, book)
        //return new updated book or null if book was not updated
        return if (oldBook == null) null else book;
    }

    fun deleteBook(bookId: String): Book? {
        return books.remove(bookId)
    }

    fun sortedBooks(sortby: String, asc: Boolean): Collection<Book> {
        val member = Book::class.declaredMemberProperties.find { it.name == sortby } ?: return books.values.also {
            log.warn("The field to sort by does not exist")
        }
        return if (asc) {
            books.values.sortedBy { member.get(it).toString() }
        } else {
            books.values.sortedByDescending { member.get(it).toString() }
        }
    }
}