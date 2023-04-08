package Entities;

import java.util.Objects;

public class Producer {
    Long id;
    String nom;
    String Prenom;
    String email;
    String password;

    public Producer(String nom, String prenom, String email, String password) {
        this.nom = nom;
        Prenom = prenom;
        this.email = email;
        this.password = password;
    }

    public Producer(Long id, String nom, String prenom, String email, String password) {
        this.id = id;
        this.nom = nom;
        Prenom = prenom;
        this.email = email;
        this.password = password;
    }

    public Producer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producer producer = (Producer) o;
        return email.equals(producer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "Producer{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", Prenom='" + Prenom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
