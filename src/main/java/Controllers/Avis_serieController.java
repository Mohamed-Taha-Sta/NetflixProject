package Controllers;

import DAO.Avis_SerieDAO;
import Entities.Serie;
import Entities.User;
import Services.Avis_SerieService;

import java.util.List;

public class Avis_serieController {

    public static boolean Avis_Exist(Serie serie, User user){
        return Avis_SerieService.Avis_Exist(serie, user);
    }
    public static boolean add_avis(Serie serie, User user, String avis){
        return Avis_SerieService.add_avis(serie,user,avis);
    }
    public static boolean modif_avis(Serie serie, User user, String avis){
        return Avis_SerieService.modif_avis(serie,user,avis);
    }
    public static boolean delete_avis(Serie serie, User user){
        return Avis_SerieService.delete_avis(serie,user);
    }
    public static String FIND_avis(Serie serie, User user){
        return Avis_SerieService.FIND_avis(serie,user);
    }
    public static List<String> FindAvisAllSerie(Serie serie) {
        return Avis_SerieService.FindAvisAllSerie(serie);
    }
}
