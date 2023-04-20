package Controllers;

import Entities.Actor;
import Services.ActorService;

import java.util.List;

public class ActorController {

    public static List<Actor> GetAllActors(){
        return ActorService.GetAll();
    }
    public static boolean authenticate(String mail, String pass) {
        return ActorService.authenticate(mail, pass);
    }

}
