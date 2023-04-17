package Controllers;

import Services.UserService;

public class UserController {
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
}
