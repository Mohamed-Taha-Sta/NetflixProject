package Controllers;

import Entities.Actor;
import DAO.ActorDAO;
import DAO.UserDAO;
import Services.ActorService;

import java.util.List;

public class ActorController {

    public static List<Actor> GetAllActors(String something){
        return ActorService.GetAll(something);
    }
    public static boolean authenticate(String mail, String pass) {
        return ActorService.authenticate(mail, pass);
    }

    public static boolean check_Mail(String mail) {
        return ActorService.check_Mail(mail);
    }


    public static void modifnom(Long id,String nom) {
        ActorService.modifnom(id,nom);
    }

    public static void modifprenom(Long id,String prenom) {
        ActorService.modifprenom(id,prenom);
    }

    public static void modifmail(Long id,String mail) {
        ActorService.modifmail(id,mail);
    }

    public static void modifpassword(Long id,String password) {
        ActorService.modifpassword(id,password);
    }

    public static boolean Add_Actor(Actor act) {
        return ActorService.ajout_acteur(act);
    }
}
