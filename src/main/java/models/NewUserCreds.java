package models;

public class NewUserCreds extends UserCreds{

    private final String emailNew;
    private final String nameNew;

    public NewUserCreds(String name, String email) {
        this.nameNew = name;
        this.emailNew = email;
    }
    public void setFieldsNew(String name, String email) {
        this.name = name;
        this.email =email;
    }
    @Override
    public String toString() {
        return "NewUserCreds{" +
                "name='" + nameNew + '\'' +
                ", email='" + emailNew + '\'' +
                '}';
    }
}
