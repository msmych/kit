package uk.matvey.voron

import io.ktor.server.routing.Route

interface Resource {

    fun Route.routing()
}