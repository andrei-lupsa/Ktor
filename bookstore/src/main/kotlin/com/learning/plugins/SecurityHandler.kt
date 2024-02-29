package com.learning.plugins

class SecurityHandler {
    fun isValid(username: String?, password: String?): Boolean {
        return !(username.isNullOrEmpty() || password.isNullOrEmpty())
    }
}