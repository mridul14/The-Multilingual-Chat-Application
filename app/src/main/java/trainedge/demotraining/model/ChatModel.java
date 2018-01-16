package trainedge.demotraining.model;

/**
 * Created by dell on 16-01-2018.
 */

public class ChatModel {
    public String uid;
    public String name;
    public String photo;
    public String chatKey;

    public ChatModel(String uid, String name, String photo, String chatKey) {
        this.uid = uid;
        this.name = name;
        this.photo = photo;
        this.chatKey = chatKey;
    }
}
