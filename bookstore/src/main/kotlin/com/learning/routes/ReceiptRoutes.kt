package com.learning.routes

import com.learning.data.DataManager
import com.learning.ui.Endpoints
import com.learning.ui.checkout.ReceiptTemplate
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Route.receiptRouting() {
    get(Endpoints.RECEIPT.url) {
        val session = call.sessions.get<Session>()!!
        call.respondHtmlTemplate(ReceiptTemplate(DataManager.cartForUser(session))) {}
    }
}