package Services;

import DAO.UserDAO;
import Entities.User;

import java.time.LocalDate;


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

    public static boolean ChangingBirthday(LocalDate date){
        return UserDAO.changeBirthday(date);
    }

    public static boolean ChangingPass(String pass){
        return UserDAO.changePass(pass);
    }

    public static boolean ChangingActors(String actors){
        return UserDAO.changeActors(actors);
    }

    public static boolean ChangingGenres(String genres){
        return UserDAO.changeGenres(genres);
    }
    public static boolean authenticate(String mail, String pass) {
        return UserDAO.authenticate(mail, pass);
    }

    public static int ajout_User(User user) {
        return UserDAO.ajout_User(user);
    }

    public static boolean check_Mail(String mail) {
        return UserDAO.check_Mail(mail);
    }

}
