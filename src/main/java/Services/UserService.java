package Services;

import DAO.UserDAO;
import Entities.User;


public class UserService {

    public static boolean ChangingLastName(String lastName){
        return UserDAO.changeName(lastName);
    }

    public static boolean ChangingFirstName(String firstName) {
        return UserDAO.changePrename(firstName);
    }

    public static boolean ChangingMail(String mail){
        return  UserDAO.changeMail(mail);
    }

    public static boolean authenticate(String mail, String pass) {
        return UserDAO.authenticate(mail, pass);
    }

    public static boolean ajout_User(User user) {
        return UserDAO.ajout_User(user);
    }
}
