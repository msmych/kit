package uk.matvey.telek.sample

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import uk.matvey.kit.random.RandomKit.randomHttps
import uk.matvey.kit.random.RandomKit.randomNumeric
import uk.matvey.telek.TgBot
import uk.matvey.telek.TgInlineKeyboardButton

private val log = KotlinLogging.logger {}

fun main(args: Array<String>) = runBlocking {
    val bot = TgBot(
        token = args[0],
        longPollingSeconds = 60,
        onUpdatesRetrievalException = { log.error(it) { "Failed to retrieve updates" } },
        onUpdateProcessingException = { log.error(it) { "Failed to process update" } },
    )
    val job = CoroutineScope(Dispatchers.IO).launch {
        bot.start { update ->
            log.info { update }
            if (update.message?.text == "/start") {
                val message = update.message()
                bot.sendMessage(
                    chatId = message.chat.id,
                    text = "Hello, ${message.from().firstName}",
                ).let { log.info { it } }
            } else if (update.message?.text == "/ikb") {
                val from = update.message().from()
                bot.sendMessage(
                    chatId = from.id,
                    text = "Inline keyboard",
                    inlineKeyboard = listOf(
                        listOf(TgInlineKeyboardButton.url("Go to random URL", randomHttps())),
                        listOf(TgInlineKeyboardButton.callbackData("Update text", "ikb/update-message-text")),
                        listOf(TgInlineKeyboardButton.callbackData("Update markup", "ikb/row")),
                        listOf(TgInlineKeyboardButton.callbackData("Remove markup", "ikb/remove-markup")),
                    ),
                ).let { log.info { it } }
            } else if (update.callbackQuery?.data == "ikb") {
                val callbackQuery = update.callbackQuery()
                bot.editMessage(
                    callbackQuery.message, inlineKeyboard = listOf(
                        listOf(TgInlineKeyboardButton.url("Go to random URL", randomHttps())),
                        listOf(TgInlineKeyboardButton.callbackData("Update text", "ikb/update-message-text")),
                        listOf(TgInlineKeyboardButton.callbackData("Update markup", "ikb/row")),
                        listOf(TgInlineKeyboardButton.callbackData("Remove markup", "ikb/remove-markup")),
                    )
                )
            } else if (update.callbackQuery?.data == "ikb/update-message-text") {
                val callbackQuery = update.callbackQuery()
                bot.editMessage(callbackQuery.message, text = "Updated text: ${randomNumeric(3)}")
                    .let { log.info { it } }
                bot.answerCallbackQuery(callbackQuery.id).let { log.info { it } }
            } else if (update.callbackQuery?.data == "ikb/row") {
                val callbackQuery = update.callbackQuery()
                bot.editMessage(
                    callbackQuery.message, inlineKeyboard = listOf(
                        listOf(TgInlineKeyboardButton.url("Go to random URL", randomHttps())),
                        listOf(TgInlineKeyboardButton.callbackData("Update text", "ikb/update-message-text")),
                        listOf(
                            TgInlineKeyboardButton.callbackData("Update markup", "ikb"),
                            TgInlineKeyboardButton.callbackData("Remove markup", "ikb/remove-markup"),
                        )
                    )
                )
                    .let { log.info { it } }
                bot.answerCallbackQuery(callbackQuery.id).let { log.info { it } }
            } else if (update.callbackQuery?.data == "ikb/remove-markup") {
                val callbackQuery = update.callbackQuery()
                bot.editMessage(callbackQuery.message, inlineKeyboard = listOf()).let { log.info { it } }
                bot.answerCallbackQuery(callbackQuery.id).let { log.info { it } }
            }
        }
    }
    log.info { "Bot started" }
    job.join()
}
