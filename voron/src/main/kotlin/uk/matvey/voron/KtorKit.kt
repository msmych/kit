package uk.matvey.voron

import freemarker.cache.ClassTemplateLoader
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.setBody
import io.ktor.http.Parameters
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.install
import io.ktor.server.freemarker.FreeMarker
import io.ktor.server.freemarker.FreeMarkerContent
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respond

object KtorKit {

    fun ApplicationCall.pathParamOrNull(name: String) = parameters[name]

    fun ApplicationCall.pathParam(name: String) = requireNotNull(pathParamOrNull(name))

    fun ApplicationCall.queryParamOrNull(name: String) = request.queryParameters[name]

    fun ApplicationCall.queryParam(name: String) = requireNotNull(queryParamOrNull(name))

    suspend fun ApplicationCall.receiveParamsMap() = receiveParameters().entries()
        .associate { (k, v) -> k to v.joinToString(";") }

    fun Application.installFreeMarker(path: String, configure: freemarker.template.Configuration.() -> Unit = {}) {
        install(FreeMarker) {
            templateLoader = ClassTemplateLoader(this::class.java.classLoader, path)
            configure()
        }
    }

    suspend fun ApplicationCall.respondFtl(template: String, model: Any? = null) {
        respond(FreeMarkerContent("$template.ftl", model))
    }

    suspend fun ApplicationCall.respondFtl(template: String, vararg values: Pair<String, Any?>) {
        respondFtl(template, values.toMap())
    }

    fun HttpRequestBuilder.setFormData(params: Map<String, String>) {
        setBody(
            FormDataContent(
                Parameters.build {
                    params.forEach { (k, v) -> append(k, v) }
                }
            )
        )
    }

    fun HttpRequestBuilder.setFormData(vararg params: Pair<String, String>) = setFormData(params.toMap())
}
