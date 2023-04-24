package Controllers;

import Entities.User;
import Services.UserService;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class UserController {

    public static boolean isEmail(String input) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return input.matches(emailRegex);
    }

    public static boolean Birthday(LocalDate date){
        return UserService.ChangingBirthday(date);
    }

    public static boolean Genres(String genres){
        return UserService.ChangingGenres(genres);
    }
    public static boolean Actors(String actors){
       return UserService.ChangingActors(actors);
    }
    public static boolean Password(String pass){
        return UserService.ChangingPass(pass);
    }
    public static boolean FirstName(String first){
        return UserService.ChangingFirstName(first);
    }
    public static boolean LastName(String last){
        return UserService.ChangingLastName(last);
    }

    public static boolean Mail(String mail){
        return  UserService.ChangingMail(mail);
    }

    public static boolean authenticate(String mail, String pass) {
        return UserService.authenticate(mail, pass);
    }

    public static int ajout_User(User user) {
        return UserService.ajout_User(user);
    }


    public static boolean check_Mail(String mail) {
        return UserService.check_Mail(mail);
    }

}
