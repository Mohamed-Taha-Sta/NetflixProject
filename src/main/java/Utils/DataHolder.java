package Utils;

import Entities.User;
import javafx.scene.image.Image;

import java.io.File;

public  class DataHolder {
    private static String Name;
    private static String prename;
    private static String Email;
    private static String Birthday;
    private static String Password;

    private static File image;
    private static User user;



    public static String getName() {
        return Name;
    }

    public static void setName(String name) {
        Name = name;
    }

    public static String getPrename() {
        return prename;
    }

    public static void setPrename(String prename) {
        DataHolder.prename = prename;
    }

    public static String getEmail() {
        return Email;
    }

    public static void setEmail(String email) {
        Email = email;
    }

    public static String getBirthday() {
        return Birthday;
    }

    public static void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public static String getPassword() {
        return Password;
    }

    public static void setPassword(String password) {
        Password = password;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        DataHolder.user = user;
    }

    public static File getImage() {
        return image;
    }

    public static void setImage(File image) {
        DataHolder.image = image;
    }
}
