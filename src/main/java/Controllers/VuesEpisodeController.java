package Controllers;

import DAO.VuesEpisodeDAO;
import Entities.Episode;
import Entities.User;

public class VuesEpisodeController {

    public static boolean Vue_Exist(Episode episode , User user){
        return VuesEpisodeDAO.Vue_Exist(episode, user);
    }
    public static boolean Add_Vues(Episode episode, User user){
        return VuesEpisodeDAO.Add_Vues(episode,user);
    }

    public static int GetEpisodeVues(Episode episode){
        return VuesEpisodeDAO.GetEpisodeVues(episode);
    }
}
