package Entities;

import javafx.scene.image.Image;

import java.io.File;
import java.sql.Blob;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class User {
    private long ID;
    private String Name;
    private String Prename;
    private String Mail;
    private String Password;
    private LocalDate Birthday;
    private ArrayList<Long> ActorsList;
    private ArrayList<String> GenreList;

    private LocalDate subscription;

    private File image;

    public User(String name, String prename, String mail, String password, LocalDate birthday, ArrayList<Long> actorsList, ArrayList<String> genreList) {
        Name = name;
        Prename = prename;
        Mail = mail;
        Password = password;
        Birthday = birthday;
        ActorsList = actorsList;
        GenreList = genreList;
    }

    public User(String name, String prename, String mail, String password, LocalDate birthday, ArrayList<Long> actorsList, ArrayList<String> genreList, LocalDate subscription, File image) {
        Name = name;
        Prename = prename;
        Mail = mail;
        Password = password;
        Birthday = birthday;
        ActorsList = actorsList;
        GenreList = genreList;
        this.subscription = subscription;
        this.image = image;
    }

    public User(long ID, String name, String prename, String mail, String password, LocalDate birthday, ArrayList<Long> actorsList, ArrayList<String> genreList, LocalDate subscription, File image) {
        this.ID = ID;
        Name = name;
        Prename = prename;
        Mail = mail;
        Password = password;
        Birthday = birthday;
        ActorsList = actorsList;
        GenreList = genreList;
        this.subscription = subscription;
        this.image = image;
    }



    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrename() {
        return Prename;
    }

    public void setPrename(String prename) {
        Prename = prename;
    }

    public LocalDate getBirthday() {
        return Birthday;
    }

    public void setBirthday(LocalDate birthday) {
        Birthday = birthday;
    }

    public ArrayList<Long> getActorsList() {
        return ActorsList;
    }

    public void setActorsList(ArrayList<Long> actorsList) {
        ActorsList = actorsList;
    }

    public ArrayList<String> getGenreList() {
        return GenreList;
    }

    public void setGenreList(ArrayList<String> genreList) {
        GenreList = genreList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Mail.equals(user.Mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Mail);
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public LocalDate getSubscription() {
        return subscription;
    }

    public void setSubscription(LocalDate subscription) {
        this.subscription = subscription;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", Name='" + Name + '\'' +
                ", Prename='" + Prename + '\'' +
                ", Birthday=" + Birthday +
                ", ActorsList=" + ActorsList +
                ", GenreList=" + GenreList +
                ", Password='" + Password + '\'' +
                ", Mail='" + Mail + '\'' +
                '}';
    }
}
