package ktkindergarten.routes

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route

var thingsId = 0
var anotherThingsId = 0
val things = mutableListOf<Something>()

fun Route.things() {
    route("/somethings"){
        get {
            call.respond(things)
        }
        post {
            val thing = call.receive<Something>()
            things += Something(thingsId++, thing.description)
            call.respond(things.last())
        }
        route("{thingId}") {
            get {
                val thingId = call.parameters["thingId"]?.toInt()
                val thing = if(thingId != null) things.find { t -> t.id == thingId } else null
                if(thing != null) {
                    call.respond(thing)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
            route("/innerthings"){
                get {
                    val thingId = call.parameters["thingId"]?.toInt()
                    val thing = if(thingId != null) things.find { t -> t.id == thingId } else null
                    if(thing != null) {
                        call.respond(thing.innerThings)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                }
                post {
                    val anotherThing = call.receive<AnotherThing>()
                    val thingId = call.parameters["thingId"]?.toInt()
                    val thing = if(thingId != null) things.find { t -> t.id == thingId } else null
                    if(thing != null) {
                        thing.innerThings += AnotherThing(anotherThingsId++, anotherThing.description)
                        call.respond(thing.innerThings.last())
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                }
            }
        }
    }
}

data class Something(val id: Int, val description: String, val innerThings: MutableList<AnotherThing> = mutableListOf())

data class AnotherThing(val id: Int, val description: String)