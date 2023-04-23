package Controllers;

import DAO.Avis_SeasonDAO;
import Entities.Season;
import Entities.User;
import Services.Avis_SaisonService;

import java.util.List;

public class Avis_SaisonController {
    public static boolean Avis_Exist(Season season, User user){
        return Avis_SaisonService.Avis_Exist(season, user);
    }
    public static boolean add_avis(Season s, User user, String avis){
        return Avis_SaisonService.add_avis(s,user,avis);
    }
    public static boolean modif_avis(Season s, User user, String avis){
        return Avis_SaisonService.modif_avis(s,user,avis);
    }
    public static boolean delete_avis(Season s, User user){
        return Avis_SaisonService.delete_avis(s,user);
    }
    public static String FIND_avis(Season s, User user){
        return Avis_SaisonService.FIND_avis(s,user);
    }

    public static List<String> FindAll(Season s){
        return Avis_SaisonService.FindAll(s);
    }

}
