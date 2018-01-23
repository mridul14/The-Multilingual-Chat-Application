package trainedge.demotraining.model;


public class MessageList {
    public String receiverId;
    public String senderId;
    public Long Time;
    public String content;
    public String receiverlang;
    public String senderlang;
    public String translated="";

    public MessageList() {
    }

    public MessageList(String receiverId, String senderId, Long Time, String content, String receiverlang, String senderlang) {

        this.receiverId = receiverId;
        this.senderId = senderId;
        this.Time = Time;
        this.content = content;
        this.receiverlang = receiverlang;
        this.senderlang = senderlang;
    }


    public void setTranslated(String translated) {
        this.translated = translated;
    }
}
