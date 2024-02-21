package com.learning.data

data class Book(val id: String?, val title: String, val author: String, val price: Float)
data class ShoppingCart(val id: String, val userId: String, val items: ArrayList<ShoppingItem>)
data class ShoppingItem(val bookId: String, val qty: Int)
data class User(val id: String, val name: String, val username: String, val password: String)