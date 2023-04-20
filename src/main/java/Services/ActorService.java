package Services;

import DAO.ActorDAO;
import Entities.Actor;

import java.util.List;

public class ActorService {

    public static List<Actor> GetAll(String something){
        return ActorDAO.recherche_actjasser(something);
    }
    public static boolean authenticate(String mail, String pass) {
        return ActorDAO.authenticate(mail, pass);
    }

    public static boolean check_Mail(String mail) {
        return ActorDAO.check_Mail(mail);
    }


}
