package ktkindergarten.routes

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import ktkindergarten.mappings.*
import ktkindergarten.services.ThingsService

private val thingsService = ThingsService()

fun Route.things() {
    route("/somethings"){
        get {
            call.respond(thingsService.getThings().toThingsModel())
        }
        post {
            val thing = call.receive<Something>()
            val addedThing = thingsService.addThing(thing.toService())
            call.respond(addedThing.toModel())
        }
        route("{thingId}") {
            get {
                val thingId = call.parameters["thingId"]?.toInt()
                val thing = if(thingId != null) thingsService.getThingById(thingId) else null
                if(thing != null) {
                    call.respond(thing.toModel())
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
            route("/innerthings"){
                get {
                    val thingId = call.parameters["thingId"]?.toInt()
                    val innerThings = if(thingId != null) thingsService.getInnerThings(thingId) else emptyList()
                    if(innerThings != null) {
                        call.respond(innerThings.toAnotherThingsModel())
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                }
                post {
                    val anotherThing = call.receive<AnotherThing>()
                    val thingId = call.parameters["thingId"]?.toInt()
                    val addedAnotherThing = if(thingId != null) thingsService.addAnotherThing(thingId, anotherThing.toService()) else null
                    if(addedAnotherThing != null) {
                        call.respond(addedAnotherThing.toModel())
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                }
            }
        }
    }
}

data class Something(val id: Int, val description: String, val innerThings: Iterable<AnotherThing> = emptyList())

data class AnotherThing(val id: Int, val description: String)