package Services;

import DAO.ActorDAO;
import Entities.Actor;

import java.util.List;

public class ActorService {

    public static List<Actor> GetAll(){
        return ActorDAO.recherche_actjasser();
    }
    public static boolean authenticate(String mail, String pass) {
        return ActorDAO.authenticate(mail, pass);
    }

}
