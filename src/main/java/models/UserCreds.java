package models;

public class UserCreds {
    protected String email;
    private String password;
    protected String name;

    public UserCreds(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserCreds() {
    }
    public void setEmailAndPass(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public static UserCreds fromUser(User user) {
        return new UserCreds(user.getEmail(), user.getPassword());
    }

    @Override
    public String toString() {
        return "UserCreds{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
