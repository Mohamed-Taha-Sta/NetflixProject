package Services;

import DAO.UserDAO;


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
}
