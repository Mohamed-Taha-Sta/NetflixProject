package Entities;

public class Resume {
    protected String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Resume(String title) {
        this.title = title;
    }

    public Resume() {
    }
}
