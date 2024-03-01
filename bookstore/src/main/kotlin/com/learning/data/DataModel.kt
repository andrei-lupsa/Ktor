package com.learning.data

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class BookData(@BsonId val id: ObjectId, val title: String, val author: String, val price: Float) {
    constructor(title: String, author: String, price: Float) : this(ObjectId(), title, author, price)

    fun toBook(): Book {
        return Book(id.toHexString(), title, author, price)
    }
}

data class Book(val id: String?, val title: String, val author: String, val price: Float) {
    constructor(title: String, author: String, price: Float) : this(null, title, author, price)

    fun toData(): BookData {
        return if (id == null)
            BookData(title, author, price)
        else
            BookData(if (ObjectId.isValid(id)) ObjectId(id) else ObjectId(), title, author, price)
    }
}

data class CartEntry(val book: BookData, var qty: Int, var sum: Float) {
    constructor(book: BookData) : this(book, 1, book.price)
}

data class Cart(
    @BsonId val id: ObjectId, val username: String, var qtyTotal: Int, var sum: Float,
    val entries: MutableList<CartEntry> = ArrayList()
) {
    constructor(username: String) : this(ObjectId(), username, 0, 0f)

    fun addBook(book: BookData) {
        val find = entries.find { it.book.id == book.id }
        if (find == null) {
            entries.add(CartEntry(book))
        } else {
            find.qty += 1
            find.sum += book.price
        }
        this.qtyTotal += 1
        this.sum += book.price
    }

    fun removeBook(book: BookData) {
        val find = entries.find { it.book.id == book.id } ?: return
        find.qty -= 1
        find.sum -= book.price
        if (find.qty <= 0) entries.remove(find)
        this.qtyTotal -= 1
        this.sum -= book.price
    }
}