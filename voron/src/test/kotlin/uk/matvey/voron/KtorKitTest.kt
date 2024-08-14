package uk.matvey.voron

import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.testing.testApplication
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.matvey.kit.random.RandomKit.randomAlphanumeric
import uk.matvey.voron.KtorKit.pathParam
import uk.matvey.voron.KtorKit.queryParam

class KtorKitTest {

    @Test
    fun `should access path param`() = testApplication {
        // given
        routing {
            get("/tests/{id}/path") {
                call.respond(call.pathParam("id"))
            }
            get("/tests/{id}/query") {
                call.respond(call.queryParam("q"))
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
    }
}
