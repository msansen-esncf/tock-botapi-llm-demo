import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.completion.CompletionRequest
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import com.aallam.openai.client.OpenAIHost
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import kotlin.time.Duration.Companion.seconds

/**
 * define OpenAI Settings
 */
object OpenAILocal {

    private val logger = KotlinLogging.logger {}
    /**
     * define OpenAI Settings
     */
    val host = OpenAIHost(
        baseUrl = System.getenv("OPENAI_API_BASE") //  pour LM Studio ou "http://localhost:4891/v1/" pour GTP4all
    )
    val config = OpenAIConfig(
                token = System.getenv("OPENAI_API_TOKEN") ?: "default",
                host = host,
                timeout = Timeout(socket = 50.seconds, connect = 50.seconds, request = 50.seconds),
                //loglevel = Loglevel.Body
        )
    val openAI = OpenAI(config)

    /**
     * askLocalLlmOpenAi calls the local openai platform with GTP4all or LM Studio
     */
    fun askLocalLlmOpenAi(text: String): String {
        val request = CompletionRequest (
            model = ModelId(System.getenv("OPENAI_API_MODEL") ?: "defaultModel"),
            prompt = text,
            maxTokens = 256,
            temperature = 0.4,
            topP = 0.4,
        )
        val response = runBlocking { openAI.completion(request) }
        // log the response
        //logger.debug("OpenAI response: ${response.choices.first().text.trim()}")
        return response.choices.first().text.trim()
    }

    /**
     * askLocalLlmOpenAiMap calls the local openai platform with GTP4all or LM Studio
     */
    fun askLocalLlmOpenAiMap(userMessage: ChatMessage, systemMessage: ChatMessage): ChatCompletion {
        val chatMessages = mutableListOf(
            systemMessage,
            userMessage
        )
        val request = ChatCompletionRequest (
            model = ModelId(System.getenv("OPENAI_API_MODEL") ?: "defaultModel"),
            messages = chatMessages,
            maxTokens = 256,
            temperature = 0.4,
            topP = 0.4,
        )

        val response = runBlocking { openAI.chatCompletion(request) }
        // log the response
        logger.info("Model: ${response.model} OpenAI response: ${response.choices.first().message}")
        return response


        //TODO prise en charge de l'echec d'appel au modele
        /*try {
            //code d'appelopenai
        } catch (e: InvalidRequestException) {
            return ChatCompletion(
                id = 0.toString(),
                created = Long.MAX_VALUE,
                model = ModelId("mistralljj"),
                choices = listOf(ChatChoice(
                    index = 0,
                    message = null,
                    finishReason = null,
                    logprobs = null
                ))
            )
        } finally {
            // optional finally block
        }*/



    }
}