package Services;

import DAO.Avis_SeasonDAO;
import DAO.Avis_SerieDAO;
import Entities.Season;
import Entities.Serie;
import Entities.User;

import java.util.List;

public class Avis_SaisonService {
    public static boolean add_avis(Season s, User user, String avis){
        return Avis_SeasonDAO.add_avis(s,user,avis);
    }
    public static boolean modif_avis(Season s, User user, String avis){
        return Avis_SeasonDAO.modif_avis(s,user,avis);
    }
    public static boolean delete_avis(Season s, User user){
        return Avis_SeasonDAO.delete_avis(s,user);
    }
    public static String FIND_avis(Season s, User user){
        return Avis_SeasonDAO.affiche_avis(s,user);
    }
    public static List<String> FindAll(Season s){
        return Avis_SeasonDAO.FindAll(s);
    }
}
