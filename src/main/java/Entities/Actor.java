package Entities;

import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class Actor {
     static protected  long ID=1;
    protected String Name;
    protected String Prename;
    protected String Mail;
    protected String password;


    public Actor(long ID, String name, String prename, String mail, String password) {
        this.ID = ID;
        Name =name ;
        Prename = prename;
        Mail = mail;
        this.password = password;
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

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return ID == actor.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
