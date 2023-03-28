package Entities;

public class Admin {
    private long ID;
    private String Mail;
    private String Password;

    public Admin(long ID, String mail, String password) {
        this.ID = ID;
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
}
