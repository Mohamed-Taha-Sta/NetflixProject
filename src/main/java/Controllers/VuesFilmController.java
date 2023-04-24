package Controllers;

import Entities.Film;
import Entities.User;
import Services.VuesFilmService;

public class VuesFilmController {

    public static boolean Vue_Exist(Film film , User user){
        return VuesFilmService.Vue_Exist(film, user);
    }
    public static boolean Add_Vues(Film film, User user) {
        return  VuesFilmService.Add_Vues(film, user);
    }

    public static int GetFilmVues(Film film){
        return VuesFilmService.GetFilmVues(film);
    }
}
