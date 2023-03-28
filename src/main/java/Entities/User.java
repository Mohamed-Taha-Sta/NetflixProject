package Entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class User {
   private long ID;
   private String Name;
   private String Prenom;
   private LocalDate Birthday;
   private ArrayList<Long> ActorsList;
   private ArrayList<String> GenreList;

    public User(long ID, String name, String prenom, LocalDate birthday, ArrayList<Long> actorsList, ArrayList<String> genreList) {
        this.ID = ID;
        Name = name;
        Prenom = prenom;
        Birthday = birthday;
        ActorsList = actorsList;
        GenreList = genreList;
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

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
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
        return ID == user.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
