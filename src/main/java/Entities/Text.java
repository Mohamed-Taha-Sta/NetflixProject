package Entities;

public class Text extends Resume{

    private String texte;

    public Text(String title, String texte) {
        super(title);
        this.texte = texte;
    }

    public Text() {
    }
}
