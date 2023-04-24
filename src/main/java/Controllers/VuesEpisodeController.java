package Controllers;

import DAO.VuesEpisodeDAO;
import Entities.Episode;
import Entities.User;
import Services.VuesEpisodeService;

public class VuesEpisodeController {

    public static boolean Vue_Exist(Episode episode , User user){
        return VuesEpisodeService.Vue_Exist(episode, user);
    }
    public static boolean Add_Vues(Episode episode, User user){
        return VuesEpisodeService.Add_Vues(episode,user);
    }

    public static int GetEpisodeVues(Episode episode){
        return VuesEpisodeService.GetEpisodeVues(episode);
    }
}
