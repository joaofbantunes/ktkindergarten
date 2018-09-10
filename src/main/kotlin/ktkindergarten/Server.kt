package ktkindergarten

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson

fun main(args: Array<String>) {
    var requests = 0
    println("Starting server...")
    embeddedServer(Netty, 8080) {
        install(ContentNegotiation) {
            jackson {}
        }
        routing {
            get("/") {
                val requestNumber = ++requests
                call.respond(SomeResponse(
                        requestNumber,
                        if (requestNumber == 1) "Hellooooooooooooooo!" else "Hello again - ${requestNumber} requests!"))
            }
        }
    }.start(wait = true)
}

data class SomeResponse(val requestNumber: Int, val greeting: String)