package Entities;

import java.util.Objects;

public class Admin {
    private long ID;
    private String name;
    private String prename;
    private String Mail;
    private String Password;

    public Admin(long ID, String name, String prename, String mail, String password) {
        this.ID = ID;
        this.name = name;
        this.prename = prename;
        Mail = mail;
        Password = password;
    }

    public Admin(String name, String prename, String mail, String password) {
        this.name = name;
        this.prename = prename;
        Mail = mail;
        Password = password;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Mail.equals(admin.Mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Mail);
    }
}
