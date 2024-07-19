package service

import ai.tock.bot.api.client.ClientBus
import ai.tock.bot.api.model.message.bot.Action
import ai.tock.bot.api.model.message.bot.AttachmentType
import ai.tock.bot.api.model.message.bot.Card


fun pictureAttachement(imgPath: String): Pair<String, AttachmentType> {
    return Pair(imgPath, AttachmentType.image)
}

// message complet avec attachement et actions mais les elements peuvent etre passés a null si besoin
fun ClientBus.cardTitleSubtitleAction(type: String, title: String? = null, subtitle: String? = null, attachement: Pair<String, AttachmentType>? = null, actions: List<CharSequence>? = null) {
    val card: Card = newCard(
        title.orEmpty(),
        subtitle,
        newAttachment(attachement?.first.orEmpty(), attachement?.second),
        actions?.map { newAction(it) }.orEmpty()
    )

    cardByType(type, card)
}

fun ClientBus.cardByType(type: String, card: Card) {
    when (type) {
        "end" -> end(card)
        "send" -> send(card)
        else -> end("Veuillez contacter l'administrateur")
    }
}

fun ClientBus.cardTitleMapListAction(title: String, keyValueMap: Map<String, String>?, attachement: Pair<String, AttachmentType>?, actions: List<CharSequence>?): Card {

    return newCard(
        title,
        keyValueMap?.map { "<p><span style='font-weight:bold; color:LightSeaGreen '>${it.key}</span> : ${it.value}</p>" }?.joinToString(""),
        newAttachment(attachement?.first.orEmpty(), attachement?.second),
        actions?.map { newAction(it) }.orEmpty()
    )

}

private fun <E> List<E>.forEach(action: Action) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

