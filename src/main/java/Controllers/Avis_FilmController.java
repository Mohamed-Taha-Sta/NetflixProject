package Controllers;

import DAO.Avis_FilmDAO;
import Entities.Film;
import Entities.User;
import Services.Avis_FilmService;

public class Avis_FilmController {

    public static boolean add_avis(Film film, User user, String avis){
        return Avis_FilmService.add_avis(film,user,avis);
    }
    public static boolean modif_avis(Film film, User user, String avis){
        return Avis_FilmService.modif_avis(film,user,avis);
    }
    public static boolean delete_avis(Film film, User user){
        return Avis_FilmService.delete_avis(film,user);
    }
    public static String FIND_avis(Film film, User user){
        return Avis_FilmService.FIND_avis(film,user);
    }

    public static boolean Avis_Exist(Film film, User user){
        return Avis_FilmService.Avis_Exist(film,user);
    }
}
