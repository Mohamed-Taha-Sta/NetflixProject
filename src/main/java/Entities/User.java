package Entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class User {
   private long ID;
   private String Name;
   private String Prename;
   private LocalDate Birthday;
   private ArrayList<Long> ActorsList;
   private ArrayList<String> GenreList;
   private String Password;

   private String Mail;

    public User() {
    }

    public User(long ID, String name, String prename, LocalDate birthday, ArrayList<Long> actorsList, ArrayList<String> genreList, String password, String mail) {
        this.ID = ID;
        Name = name;
        Prename = prename;
        Birthday = birthday;
        ActorsList = actorsList;
        GenreList = genreList;
        Password = password;
        Mail = mail;
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
