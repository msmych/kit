package uk.matvey.voron

import io.ktor.server.application.ApplicationCall
import io.ktor.server.freemarker.FreeMarkerContent
import io.ktor.server.response.respond

object FreeMarkerKit {

    suspend fun ApplicationCall.respondFtl(template: String, model: suspend () -> Any? = { null }) {
        respond(FreeMarkerContent("$template.ftl", model()))
    }
}