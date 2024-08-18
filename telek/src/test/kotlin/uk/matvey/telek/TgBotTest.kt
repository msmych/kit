package uk.matvey.telek

import kotlinx.coroutines.test.runTest
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable

class TgBotTest {

    private val log = KotlinLogging.logger {}

    @Test
    @EnabledIfEnvironmentVariable(named = "TG_BOT_TOKEN", matches = ".*", disabledReason = "No token")
    fun `should get updates`() = runTest {
        // given
        val token = System.getenv("TG_BOT_TOKEN")
        val tgBot = TgBot(token, 5)

        // when / then
        tgBot.start { log.info { it } }
    }
}