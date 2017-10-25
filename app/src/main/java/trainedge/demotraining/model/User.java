package trainedge.demotraining.model;

/**
 * Created by dell on 27-08-2017.
 */

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
