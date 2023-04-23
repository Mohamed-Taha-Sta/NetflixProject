package Controllers;

import DAO.ScoreEpisodeDAO;
import Entities.Episode;
import Entities.User;
import Services.ScoreEpisodeService;

public class ScoreEpisodeController {



    public static int GetNumberVotesEpisode(Episode episode) {
        return ScoreEpisodeService.GetNumberVotesEpisode(episode);
    }
    public static boolean Score_Exist(Episode episode, User user){
        return ScoreEpisodeService.Score_Exist(episode, user);
    }
    public static double RetrieveUserScore(Episode episode, User user){
        return ScoreEpisodeService.RetrieveUserScore(episode, user);
    }
    public static boolean Add_Score(Episode episode, User user, double score){
        return ScoreEpisodeService.Add_Score(episode, user, score);
    }

    public static boolean Delete_Score(Episode episode, User user){
        return ScoreEpisodeService.Delete_Score(episode, user);
    }
    public static boolean Update_Score(Episode episode, User user, double score){
        return ScoreEpisodeService.Update_Score(episode, user, score);
    }
    public static double GetEpisodeScore(Episode episode){
        return ScoreEpisodeService.GetEpisodeScore(episode);
    }
}
