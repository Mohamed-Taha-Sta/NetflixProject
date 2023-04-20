package Controllers;

import Entities.Actor;
import DAO.ActorDAO;
import DAO.UserDAO;
import Services.ActorService;

import java.util.List;

public class ActorController {

    public static List<Actor> GetAllActors(){
        return ActorService.GetAll();
    }
    public static boolean authenticate(String mail, String pass) {
        return ActorService.authenticate(mail, pass);
    }

    public static boolean check_Mail(String mail) {
        return ActorService.check_Mail(mail);
    }


}
