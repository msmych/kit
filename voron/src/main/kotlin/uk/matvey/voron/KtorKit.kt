package uk.matvey.voron

import freemarker.cache.ClassTemplateLoader
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.install
import io.ktor.server.freemarker.FreeMarker
import io.ktor.server.freemarker.FreeMarkerContent
import io.ktor.server.response.respond

object KtorKit {

    fun ApplicationCall.pathParamOrNull(name: String) = parameters[name]

    fun ApplicationCall.pathParam(name: String) = requireNotNull(pathParamOrNull(name))

    fun ApplicationCall.queryParamOrNull(name: String) = request.queryParameters[name]

    fun ApplicationCall.queryParam(name: String) = requireNotNull(queryParamOrNull(name))

    fun Application.installFtl(path: String, configure: freemarker.template.Configuration.() -> Unit = {}) {
        install(FreeMarker) {
            templateLoader = ClassTemplateLoader(this::class.java.classLoader, path)
            configure()
        }
    }

    suspend fun ApplicationCall.respondFtl(template: String, model: Any? = null) {
        respond(FreeMarkerContent("$template.ftl", model))
    }
}
