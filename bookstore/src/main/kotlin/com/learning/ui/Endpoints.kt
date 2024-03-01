package com.learning.ui

enum class Endpoints(val url: String) {
    LOGIN("/html/login"),
    LOGOUT("/html/logout"),
    HOME("/html/home"),
    BOOKS("/html/books"),
    BOOK_SEARCH("/html/books/search"),
    ADD_TO_CART("/html/cart/add"),
    REMOVE_FROM_CART("/html/cart/remove"),
    CART("/html/cart"),
    CHECKOUT("/html/checkout")
}