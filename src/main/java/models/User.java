package models;

public class User {
    private String name;
    private String email;
    private String password;
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public User withEmail(String email) {
        this.email = email;
        return this;
    }
    public User withPassword(String password) {
        this.password = password;
        return this;
    }
    public User withName(String name) {
        this.name = name;
        return this;
    }
    public void setFields(String name, String email, String password) {
        this.name = name;
        this.email =email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
