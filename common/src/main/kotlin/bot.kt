/*
 * Copyright (C) 2017/2019 VSCT
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.tock.demo.common

import OpenAILocal
import ai.tock.bot.api.client.ClientBus
import ai.tock.bot.api.client.newBot
import ai.tock.bot.api.client.newStory
import ai.tock.bot.api.client.unknownStory
import ai.tock.bot.api.model.message.bot.Card
import ai.tock.bot.connector.web.webMessage
import ai.tock.bot.connector.web.webPostbackButton
import ai.tock.bot.definition.Intent
import ai.tock.shared.property
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.chat.chatMessage
import mu.KotlinLogging
import story.llmdify

private val logger = KotlinLogging.logger {}
val apiKey = property("tock_bot_api_key", System.getenv("TOCK_BOT_API_KEY") ?: "TOCKAPIKEY")

val bot = newBot(
    apiKey,
    listOf(
        llmdify,
        newStory("llmopenai") {
            logger.info("llmopenai" + message.toString())

            val userMessage = chatMessage {
                role = ChatRole.User
                content = message.toString()
            }

            val systemMessage = ChatMessage(
                role = ChatRole.System,
                content = System.getenv("OPENAI_SYSTEM_PROMPT") ?: "Tu réponds exclusivement en français de manière professionnelle et toujours positive."
            )


            val IaResponse = OpenAILocal.askLocalLlmOpenAiMap(userMessage, systemMessage)

            val message = IaResponse.choices.first().message
            val chatresponse = message.content.toString().trim()
            val chatmodel = IaResponse.model.id
            end(
                newCard(
                    chatresponse,
                    "source ${chatmodel}"
                )
            )
        },
        unknownStory {
            end {
                //custom model sample
                webMessage(
                    "Désolé, je ne suis pas en capacité de vous répondre",
                    webPostbackButton("Card", Intent("card")),
                    webPostbackButton("Carousel", Intent("carousel"))
                )
            }
        }
    ),

)

fun ClientBus.cardTimeout() {
    cardTitleOnly("end", "Désolé, je n'ai pas eu de réponse du serveur dans le temps imparti merci de renouveler votre demande")
}

fun ClientBus.cardNeedMoreInfos() {
    cardTitleOnly("send", "Il va me falloir plus d'informations.")
}

/**
 * Définition des types de cartes utilisables
 */
// message texte simple
fun ClientBus.cardTitleOnly(type: String, title: String) {
    when (type) {
        "end" -> end(title)
        "send" -> send(title)
        else -> end("Veuillez contacter l'administrateur")
    }
}

// message texte Titre et sous titre
fun ClientBus.cardTitleAndSubtitle(type: String, title: String, subtitle: String?) {

    val card: Card = newCard(
        title, subtitle
    )
    when (type) {
        "end" -> end(card)
        "send" -> send(card)
        else -> end("Veuillez contacter l'administrateur")
    }
}


