package uk.matvey.telek.sample

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import uk.matvey.kit.random.RandomKit.randomHttps
import uk.matvey.telek.TgBot
import uk.matvey.telek.TgInlineKeyboardButton

private val log = KotlinLogging.logger {}

fun main(args: Array<String>) = runBlocking {
    val bot = TgBot(
        token = args[0],
        longPollingSeconds = 60,
        onUpdateProcessException = { log.error(it) { "Failed to process update" } },
    )
    val job = CoroutineScope(Dispatchers.IO).launch {
        bot.start { update ->
            log.info { update }
            if (update.message?.text == "/start") {
                val from = update.message().from()
                bot.sendMessage(
                    chatId = from.id,
                    text = "Hello, ${from.firstName}",
                ).let { log.info { it } }
            } else if (update.message?.text == "/ikb") {
                val from = update.message().from()
                bot.sendMessage(
                    chatId = from.id,
                    text = "Inline keyboard",
                    inlineKeyboard = listOf(
                        listOf(TgInlineKeyboardButton.url("URL", randomHttps())),
                        listOf(TgInlineKeyboardButton.callbackData("Remove keyboard", "ikb/remove"))
                    ),
                ).let { log.info { it } }
            } else if (update.callbackQuery?.data == "ikb/remove") {
                val callbackQuery = update.callbackQuery()
                bot.updateMessageInlineKeyboard(callbackQuery.message, listOf()).let { log.info { it } }
                bot.answerCallbackQuery(callbackQuery.id).let { log.info { it } }
            }
        }
    }
    log.info { "Bot started" }
    job.join()
}
