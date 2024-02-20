package com.learning.data

class DataManager {
    val books = hashMapOf<String, Book>()

    fun gimmeId(): String {
        return books.size.toString()
    }

    fun init() {
        newBook(Book(gimmeId(), "How to grow apples", "Mr. Appleton", 100f))
        newBook(Book(gimmeId(), "How to grow oranges", "Mr. Orangeton", 90f))
        newBook(Book(gimmeId(), "How to grow lemons", "Mr. Lemon", 110f))
        newBook(Book(gimmeId(), "How to grow pineapples", "Mr. Pineapple", 100f))
        newBook(Book(gimmeId(), "How to grow pears", "Mr. Pears", 110f))
        newBook(Book(gimmeId(), "How to grow coconuts", "Mr. Coconut", 130f))
        newBook(Book(gimmeId(), "How to grow bananas", "Mr. Appleton", 120f))
    }

    fun newBook(book: Book) {
        books[book.id] = book
    }

    fun updateBook(book: Book): Book? {
        books.replace(book.id, book)
        return books[book.id]
    }

    fun deleteBook(book: Book): Book? {
        return books.remove(book.id)
    }
}