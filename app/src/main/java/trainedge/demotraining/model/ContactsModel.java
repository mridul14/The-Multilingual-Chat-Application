package trainedge.demotraining.model;


public class ContactsModel {
    public ContactsModel() {
    }

    public String tvUser;
    public String tvUserMail;

    public String getTvUser() {
        return tvUser;
    }

    public void setTvUser(String tvUser) {
        this.tvUser = tvUser;
    }

    public String getTvUserMail() {
        return tvUserMail;
    }

    public void setTvUserMail(String tvUserMail) {
        this.tvUserMail = tvUserMail;
    }

    public ContactsModel(String tvUser, String tvUserMail){
        this.tvUser=tvUser;
        this.tvUserMail=tvUserMail;
    }
}
