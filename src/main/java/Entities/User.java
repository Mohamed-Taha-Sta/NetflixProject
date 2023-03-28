package Entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class User {
   long ID;
   String Nom;
   String Prenom;
   LocalDate Birthday;

   ArrayList<Long> ActorsList;

   ArrayList<String> GenreList;

    public User(long ID, String nom, String prenom, LocalDate birthday, ArrayList<Long> actorsList, ArrayList<String> genreList) {
        this.ID = ID;
        Nom = nom;
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

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
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
