package trainedge.demotraining.model;


public class User {

    public String name;
    public String email;
    public String id;
    public String photo;
    public String language;



    public User(String name, String email, String id, String photo, String language) {

        this.name = name;
        this.email = email;
        this.id = id;
        this.photo = photo;
        this.language= language;

    }

    public User() {
    }
}
