package Controllers;

import DAO.UserDAO;
import Entities.User;
import Services.UserService;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserController {

    public static void main(String[] args) {
        ArrayList<Long> listActors = new ArrayList<>();
        listActors.add(1L);
        listActors.add(2L);
        listActors.add(3L);
        ArrayList<String> listGenres = new ArrayList<>();
        listGenres.add("Action");
        listGenres.add("Drama");
        listGenres.add("Comedie");
        File imageFile = new File("src/main/resources/Images/miniProfile.png");

        User user = new User("Mohamed Taha","Sta","taha@gmail.com","123", LocalDate.of(2002,12,15),
                listActors,listGenres,LocalDate.now(),imageFile);
        ajout_User(user);
    }

    public static boolean isEmail(String input) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return input.matches(emailRegex);
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

    public static boolean ajout_User(User user) {
        return UserService.ajout_User(user);
    }


    public static boolean check_Mail(String mail) {
        return UserService.check_Mail(mail);
    }

}
