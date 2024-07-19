package story

import ai.tock.bot.api.client.newStory
import service.DifyService

val llmdify = newStory("llmdify") {

    val inputs = "key:value"
    println(botId)
    val difyservice = DifyService(userId.id,inputs, message.toString(),botId.id)

    val difyReponse = difyservice.completionClient()
    end(
        newCard(
            difyReponse.answer,
            "source LLM on Dify"
        )
    )

}
