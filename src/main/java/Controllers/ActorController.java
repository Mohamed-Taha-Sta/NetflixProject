package Controllers;

import Services.ActorService;

public class ActorController {

    public static boolean authenticate(String mail, String pass) {
        return ActorService.authenticate(mail, pass);
    }

}
