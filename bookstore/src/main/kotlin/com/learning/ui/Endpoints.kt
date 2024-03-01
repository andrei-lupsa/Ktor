package com.learning.ui

enum class Endpoints(val url: String) {
    LOGIN("/html/login"),
    LOGOUT("/html/logout"),
    DOLOGIN("/html/dologin"),
    HOME("/html/home"),
    BOOKS("/html/books"),
    DOBOOKSEARCH("/html/books/search"),
    CART("/html/cart")
}