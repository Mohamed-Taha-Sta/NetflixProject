package Entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

import java.util.Objects;

public class Actor {
    private  long ID=9;
    private int x;
    protected String Name;
    protected String Prename;
    protected String Mail;
    protected String password;
    protected CheckBox select;

    public  void setID(long ID) {
        this.ID = ID;
    }



    public Actor(String name, String prename, String mail, String password) {
        Name = name;
        Prename = prename;
        Mail = mail;
        this.password = password;
        //this.select=new CheckBox();
       // this.select.setId("checkBox");
    }


   public Actor(long ID, String name, String prename, String mail, String password) {
       this.ID = ID;
       Name =name ;
       Prename = prename;
       Mail = mail;
       this.password = password;
/*       this.select=new CheckBox();
       this.select.setId("checkBox");*/
   }


    public  long getID() {
        return ID;
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

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
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

    @Override
    public String toString() {
        return "Actor{" +
                "Name='" + Name + '\'' +
                ", Prename='" + Prename + '\'' +
                ", Mail='" + Mail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
