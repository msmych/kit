package uk.matvey.voron

import io.ktor.client.request.get
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.testing.testApplication
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.junit.jupiter.api.Test
import uk.matvey.voron.KtorKit.pathParam
import uk.matvey.voron.KtorKit.queryParam

class KtorKitTest {

    @Test
    fun `should access request`() = testApplication {
        application {
            routing {
                get("/tests/{id}/check") {
                    call.respond(
                        buildJsonObject {
                            put("pathParam", call.pathParam("id"))
                            put("queryParam", call.queryParam("query"))
                        }
                    )
                }
            }
        }

        client.get("/tests/1/check?query=abc")
    }
}
