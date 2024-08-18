package uk.matvey.telek

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable

class TgBotTest {

    private val log = KotlinLogging.logger {}

    @Test
    @EnabledIfEnvironmentVariable(named = "TG_BOT_TOKEN", matches = ".*", disabledReason = "No token")
    fun `should get updates`() = runBlocking {
        // given
        val token = System.getenv("TG_BOT_TOKEN")
        val tgBot = TgBot(token, 5)

        // when / then
        val job = launch {
            tgBot.start {
                log.info { it }
                val m = tgBot.sendMessage(it.message().from().id, it.message().text())
                log.info { m }
            }
        }

        delay(30_000)
        job.cancel()
    }
}