package Utils;

import Entities.Actor;
import Entities.Admin;
import Entities.Producer;
import Entities.User;

import java.io.File;

public  class DataHolder {
    private static String UserType;
    private static String Name;
    private static String prename;
    private static String Email;
    private static String Birthday;
    private static String Password;

    private static File image;
    private static User user;
    private static Actor actor;
    private static Producer producer;
    private static Admin admin;
    private static String selectedYear;
    private static int EpidodeId;

    public static String getSelectedYear() {
        return selectedYear;
    }

    public static void setSelectedYear(String selectedYear) {
        DataHolder.selectedYear = selectedYear;
    }

    public static Admin getAdmin() {
        return admin;
    }

    public static void setAdmin(Admin admin) {
        DataHolder.admin = admin;
    }


    public static Producer getProducer() {
        return producer;
    }

    public static void setProducer(Producer producer) {
        DataHolder.producer = producer;
    }

    public static Actor getActor() {
        return actor;
    }

    public static void setActor(Actor actor) {
        DataHolder.actor = actor;
    }

    public static int getEpidodeId() {
        return EpidodeId;
    }

    public static void setEpidodeId(int epidodeId) {
        EpidodeId = epidodeId;
    }

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

    public static String getUserType() {
        return UserType;
    }

    public static void setUserType(String userType) {
        UserType = userType;
    }
}
