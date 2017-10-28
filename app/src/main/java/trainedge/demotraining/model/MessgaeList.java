package trainedge.demotraining.model;


public class MessgaeList {
    public String receiverId;
    public String senderId;
    public String time;
    public String content;
    public String receiverlang;
    public String senderlang;


    public MessgaeList() {
    }

    public MessgaeList(String receiverId, String senderId, String time, String content, String receiverlang, String senderlang) {

        this.receiverId = receiverId;
        this.senderId = senderId;
        this.time = time;
        this.content = content;
        this.receiverlang = receiverlang;
        this.senderlang = senderlang;
    }


  }
