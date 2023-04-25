package Services;

import DAO.ActorDAO;
import Entities.Actor;

import java.util.List;

public class ActorService {

    public static List<Actor> GetAll(String something) {
        return ActorDAO.recherche_actjasser(something);
    }

    public static boolean authenticate(String mail, String pass) {
        return ActorDAO.authenticate(mail, pass);
    }

    public static boolean check_Mail(String mail) {
        return ActorDAO.check_Mail(mail);
    }


    public static void modifnom(Long id, String nom) {
        ActorDAO.modifnom(id, nom);
    }

    public static void modifprenom(Long id, String prenom) {
        ActorDAO.modifprenom(id, prenom);
    }

    public static void modifmail(Long id, String mail) {
        ActorDAO.modifmail(id, mail);
    }

    public static void modifpassword(Long id, String password) {
        ActorDAO.modifpassword(id, password);
    }

    public static boolean ajout_acteur(Actor act) {
        return ActorDAO.ajout_acteur(act);
    }

}
