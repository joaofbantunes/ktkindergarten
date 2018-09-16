package ktkindergarten

import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import ktkindergarten.routes.status
import ktkindergarten.routes.things

fun main(args: Array<String>) {

    println("Starting server...")

    embeddedServer(Netty, 8080) {
        install(ContentNegotiation) {
            jackson {}
        }
        routing {
            status()
            things()
        }
    }.start(wait = true)
}