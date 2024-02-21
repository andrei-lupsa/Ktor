package com.learning.data

import java.util.concurrent.atomic.AtomicInteger

object DataManager {
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
}