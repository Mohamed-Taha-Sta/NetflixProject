package Services;

import DAO.ActorDAO;

public class ActorService {

    public static boolean authenticate(String mail, String pass) {
        return ActorDAO.authenticate(mail, pass);
    }

}
