package Services;

import DAO.VuesFilmDAO;
import Entities.Film;
import Entities.User;

public class VuesFilmService {
    public static boolean Vue_Exist(Film film , User user){
        return VuesFilmDAO.Vue_Exist(film, user);
    }
    public static boolean Add_Vues(Film film, User user) {
        return  VuesFilmDAO.Add_Vues(film, user);
    }

    public static int GetFilmVues(Film film){
        return VuesFilmDAO.GetFilmVues(film);
    }
}
