package trainedge.demotraining.model;

/**
 * Created by dell on 27-08-2017.
 */

public class User {

    public String name;
    public String email;
    public String id;
    public String photo;
    public String lang;



    public User(String name, String email, String id, String photo, String lang) {

        this.name = name;
        this.email = email;
        this.id = id;
        this.photo = photo;
        this.lang= lang;

    }

    public User() {
    }
}
