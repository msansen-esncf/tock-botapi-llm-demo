package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import service.Metadata;

public class DifyReponse {

    @SerializedName("event")
    @Expose
    public String event;
    @SerializedName("task_id")
    @Expose
    public String taskId;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("message_id")
    @Expose
    public String messageId;
    @SerializedName("conversation_id")
    @Expose
    public String conversationId;
    @SerializedName("mode")
    @Expose
    public String mode;
    @SerializedName("answer")
    @Expose
    public String answer;
    @SerializedName("metadata")
    @Expose
    public Metadata metadata;
    @SerializedName("created_at")
    @Expose
    public Integer createdAt;

    public DifyReponse(String answer) {
        this.answer = answer;
    }
}
