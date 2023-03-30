package Entities;

public class Text extends Resume{

    private String texte;

    public Text(String title, String texte) {
        super(title);
        this.texte = texte;
    }

    public Text() {
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    @Override
    public String toString() {
        return "Title: "+title+
                "Texte: "+texte;
    }
}
