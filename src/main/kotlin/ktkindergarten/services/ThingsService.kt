package ktkindergarten.services

class ThingsService {
    private var thingsId = 0
    private var anotherThingsId = 0
    private val things = mutableListOf<Something>()

    fun getThings() = things

    fun getThingById(id: Int) = things.find { t -> t.id == id }

    fun addThing(thing: Something): Something {
        things += Something(thingsId++, thing.description)
        return things.last()
    }

    fun getInnerThings(thingId: Int) = things.find { t -> t.id == thingId }?.innerThings ?: emptyList<AnotherThing>()

    fun addAnotherThing(thingId: Int, anotherThing: AnotherThing): AnotherThing? {
        val thing = if (thingId != null) things.find { t -> t.id == thingId } else null
        return if (thing != null) {
            thing.innerThings += AnotherThing(anotherThingsId++, anotherThing.description)
            thing.innerThings.last()
        } else {
            null
        }
    }
}

data class Something(val id: Int, val description: String, val innerThings: MutableList<AnotherThing> = mutableListOf())

data class AnotherThing(val id: Int, val description: String)