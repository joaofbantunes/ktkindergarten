package ktkindergarten.routes

import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get

fun Route.status() {
    get("/status") {
        call.respondText("All good!")
    }
}