package ktkindergarten.mappings

fun ktkindergarten.routes.Something.toService() = ktkindergarten.services.Something(this.id, this.description, this.innerThings.map { t -> t.toService() }.asSequence().toMutableList())

fun ktkindergarten.routes.AnotherThing.toService() = ktkindergarten.services.AnotherThing(this.id, this.description)

fun ktkindergarten.services.Something.toModel() = ktkindergarten.routes.Something(this.id, this.description, this.innerThings.toAnotherThingsModel())

fun Iterable<ktkindergarten.services.Something>.toThingsModel() = this.map { t -> t.toModel() }

fun ktkindergarten.services.AnotherThing.toModel() = ktkindergarten.routes.AnotherThing(this.id, this.description)

fun Iterable<ktkindergarten.services.AnotherThing>.toAnotherThingsModel() = this.map { it -> it.toModel() }