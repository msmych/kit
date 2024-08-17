package uk.matvey.voron

import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.testing.testApplication
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.matvey.kit.random.RandomKit.randomAlphanumeric
import uk.matvey.voron.KtorKit.installFreeMarker
import uk.matvey.voron.KtorKit.pathParam
import uk.matvey.voron.KtorKit.queryParam
import uk.matvey.voron.KtorKit.receiveParamsMap
import uk.matvey.voron.KtorKit.respondFtl
import uk.matvey.voron.KtorKit.setFormData

class KtorKitTest {

    @Test
    fun `should access params`() = testApplication {
        // given
        routing {
            get("/tests/{id}/path") {
                call.respond(call.pathParam("id"))
            }
            get("/tests/{id}/query") {
                call.respond(call.queryParam("q"))
            }
            post("/tests/{id}/receive") {
                val params = call.receiveParamsMap()
                call.respond(params["param1"] + params["param2"])
            }
        }

        // when
        val pathParam = randomAlphanumeric(8)

        var rs = client.get("/tests/$pathParam/path")

        // then
        assertThat(rs.status).isEqualTo(OK)
        assertThat(rs.bodyAsText()).isEqualTo(pathParam)

        // when
        val queryParam = randomAlphanumeric(8)

        rs = client.get("/tests/$pathParam/query?q=$queryParam")

        // then
        assertThat(rs.status).isEqualTo(OK)
        assertThat(rs.bodyAsText()).isEqualTo(queryParam)

        // when
        val param1 = randomAlphanumeric(8)
        val param2 = randomAlphanumeric(8)

        rs = client.submitForm("/tests/$pathParam/receive") {
            setFormData("param1" to param1, "param2" to param2)
        }

        // then
        assertThat(rs.status).isEqualTo(OK)
        assertThat(rs.bodyAsText()).isEqualTo(param1 + param2)
    }

    @Test
    fun `should respond ftl`() = testApplication {
        // given
        application {
            installFreeMarker("templates")
        }
        routing {
            get("/greet") {
                data class UserRs(val name: String)
                call.respondFtl("index", UserRs(call.queryParam("name")))
            }
        }

        // when
        val name = randomAlphanumeric(8)

        val rs = client.get("/greet?name=$name")

        // then
        assertThat(rs.status).isEqualTo(OK)
        assertThat(rs.bodyAsText()).isEqualTo("<h1>Hello, $name!</h1>")
    }
}
