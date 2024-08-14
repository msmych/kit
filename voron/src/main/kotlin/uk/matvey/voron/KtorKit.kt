package uk.matvey.voron

import io.ktor.server.application.ApplicationCall
import io.ktor.server.freemarker.FreeMarkerContent
import io.ktor.server.response.respond

object KtorKit {

    fun ApplicationCall.pathParamOrNull(name: String) = parameters[name]

    fun ApplicationCall.pathParam(name: String) = requireNotNull(pathParamOrNull(name))

    fun ApplicationCall.queryParamOrNull(name: String) = request.queryParameters[name]

    fun ApplicationCall.queryParam(name: String) = requireNotNull(queryParamOrNull(name))

    suspend fun ApplicationCall.respondFtl(template: String, model: suspend () -> Any? = { null }) {
        respond(FreeMarkerContent("$template.ftl", model()))
    }
}
