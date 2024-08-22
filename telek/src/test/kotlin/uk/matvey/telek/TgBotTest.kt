package uk.matvey.telek

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable
import uk.matvey.kit.random.RandomKit.randomHttps
import uk.matvey.kit.random.RandomKit.randomStr

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
                val from = it.message().from()
                val m = tgBot.sendMessage(
                    chatId = from.id,
                    text = "${it.message().text()}, ${from.firstName}",
                    inlineKeyboard = listOf(
                        listOf(
                            TgInlineKeyboardButton.url("URL", randomHttps()),
                            TgInlineKeyboardButton.callbackData("Callback data", randomStr(12)),
                        )
                    ),
                )
                log.info { m }
            }
        }

        delay(50_000)
        job.cancel()
    }
}
