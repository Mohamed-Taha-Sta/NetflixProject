package Services;

import DAO.Avis_SerieDAO;
import Entities.Serie;
import Entities.User;

import java.util.List;

public class Avis_SerieService {
    public static boolean add_avis(Serie serie, User user, String avis){
        return Avis_SerieDAO.add_avis(serie,user,avis);
    }
    public static boolean modif_avis(Serie serie, User user, String avis){
        return Avis_SerieDAO.modif_avis(serie,user,avis);
    }


    public static boolean Avis_Exist(Serie serie, User user){
        return Avis_SerieDAO.Avis_Exist(serie, user);
    }
    public static boolean delete_avis(Serie serie, User user){
        return Avis_SerieDAO.delete_avis(serie,user);
    }
    public static String FIND_avis(Serie serie, User user){
        return Avis_SerieDAO.affiche_avis(serie,user);
    }
    public static List<String> FindAvisAllSerie(Serie serie) {
        return Avis_SerieDAO.FindAvisAllSerie(serie);
    }
}
