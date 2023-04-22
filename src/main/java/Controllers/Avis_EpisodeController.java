package Controllers;

import DAO.Avis_EpisodeDAO;
import Entities.Episode;
import Entities.User;
import Services.Avis_EpisodeService;

import java.util.List;

public class Avis_EpisodeController {
    public static boolean add_avis(Episode ep, User user, String avis){
        return Avis_EpisodeService.add_avis(ep,user,avis);
    }
    public static boolean modif_avis(Episode ep, User user, String avis){
        return Avis_EpisodeService.modif_avis(ep,user,avis);
    }
    public static boolean delete_avis(Episode ep, User user){
        return Avis_EpisodeService.delete_avis(ep,user);
    }
    public static String FIND_avis(Episode ep, User user){
        return Avis_EpisodeService.FIND_avis(ep,user);
    }

    public static List<String> FindAll(Episode ep) {
        return Avis_EpisodeService.FindAll(ep);
    }
}
