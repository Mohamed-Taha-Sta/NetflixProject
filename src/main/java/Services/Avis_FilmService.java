package Services;

import DAO.Avis_FilmDAO;
import Entities.Film;
import Entities.User;

public class Avis_FilmService {
    public static boolean add_avis(Film film, User user, String avis){
       return Avis_FilmDAO.add_avis(film,user,avis);
    }
    public static boolean modif_avis(Film film, User user, String avis){
        return Avis_FilmDAO.modif_avis(film,user,avis);
    }
    public static boolean delete_avis(Film film, User user){
        return Avis_FilmDAO.delete_avis(film,user);
    }
    public static String FIND_avis(Film film, User user){
        return Avis_FilmDAO.affiche_avis(film,user);
    }

}
