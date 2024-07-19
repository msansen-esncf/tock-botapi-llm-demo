package service;

import ai.dify.javaclient.ChatClient;
import ai.dify.javaclient.DifyClientException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.DifyReponse;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static ai.dify.javaclient.constants.DifyServerConstants.BASE_URL;

public class DifyService {

    private static final String API_KEY = System.getenv("DIFY_API_KEY");

    private String user;
    private String query;
    private String conversationId;
    private Map<String, String> inputMap;

    public DifyService(String user, String inputs, String query, String conversationId) {
        this.user=user;
        this.query=query;
        this.conversationId=conversationId;

        //Input is a string kv list "A:V, B:V" passée ensuite sous la forme d'une map
        this.inputMap = new HashMap<String, String>();
        String[] pairs = inputs.split(",");
        for (int i=0;i<pairs.length;i++) {
            String pair = pairs[i];
            String[] keyValue = pair.split(":");
            this.inputMap.put(keyValue[0], keyValue[1]);
        }
    }

    public DifyReponse completionClient() {

        DifyReponse difyReponse = null;
        try {

            // Create a chat client
            ChatClient chatClient = new ChatClient(API_KEY, BASE_URL);
            // Create a chat message
            Response chatResponse = null;
            try {
                chatResponse = chatClient.createChatMessage(this.inputMap, this.query, this.user, false, this.conversationId);
            } catch (DifyClientException ex) {
                throw new RuntimeException(ex);
            }
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                ResponseBody chatResponseBody = chatResponse.body();
                difyReponse = gson.fromJson(chatResponseBody.string(), DifyReponse.class);

                //System.out.println(difyReponse.toString());

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        } catch (Exception e) {
            e.printStackTrace();
            difyReponse = new DifyReponse("Echec de l'appel au modèle de donnée, merci de réessayer.");
        }
        return difyReponse;
    }

}
