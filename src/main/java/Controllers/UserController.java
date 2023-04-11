package Controllers;

import Services.UserService;

public class UserController {

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
