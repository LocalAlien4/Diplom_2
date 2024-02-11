package models;

import com.github.javafaker.Faker;

public class UserData {
    static Faker faker= new Faker();
    public static String email = faker.internet().safeEmailAddress().toString();
    public static String password = faker.internet().password(6, 30);
    public static String name = faker.name().username().toString();
    public static User randomUser(){
        return new User()
                .withName(name)
                .withEmail(email)
                .withPassword(password);
    }
}
