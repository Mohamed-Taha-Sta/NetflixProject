package Services;

import DAO.Avis_EpisodeDAO;
import Entities.Episode;
import Entities.User;

public class Avis_EpisodeService {
    public static boolean add_avis(Episode ep, User user, String avis){
        return Avis_EpisodeDAO.add_avis(ep,user,avis);
    }
    public static boolean modif_avis(Episode ep, User user, String avis){
        return Avis_EpisodeDAO.modif_avis(ep,user,avis);
    }
    public static boolean delete_avis(Episode ep, User user){
        return Avis_EpisodeDAO.delete_avis(ep,user);
    }
    public static String FIND_avis(Episode ep, User user){
        return Avis_EpisodeDAO.affiche_avis(ep,user);
    }
}
