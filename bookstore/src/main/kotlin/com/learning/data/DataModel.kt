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

data class ShoppingCart(val id: String, val userId: String, val items: ArrayList<ShoppingItem>)
data class ShoppingItem(val bookId: String, val qty: Int)
data class User(val id: String, val name: String, val username: String, val password: String)