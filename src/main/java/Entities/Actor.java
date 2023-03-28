package Entities;

public class Actor {
    protected long ID;
    protected String Name;
    protected String Prename;

    public Actor() {
    }

    public Actor(long ID, String name, String prename) {
        this.ID = ID;
        Name = name;
        Prename = prename;
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
}
