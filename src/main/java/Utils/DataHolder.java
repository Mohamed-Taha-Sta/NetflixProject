package Utils;

import Entities.User;

public  class DataHolder {
    private static String Name;
    private static String prename;

    private static String Email;
    private static String Birthday;
    private static String Password;
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
}
