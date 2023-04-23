package Services;

import DAO.ScoreEpisodeDAO;
import Entities.Episode;
import Entities.User;

public class ScoreEpisodeService {

    public static  double GetEpisodeAvgScore(Episode episode){
        return GetEpisodeScore(episode)/GetNumberVotesEpisode(episode)*100;
    }

    public static int GetNumberVotesEpisode(Episode episode) {
        return ScoreEpisodeDAO.GetNumberVotesEpisode(episode);
    }
    public static boolean Score_Exist(Episode episode, User user){
        return ScoreEpisodeDAO.Score_Exist(episode, user);
    }
    public static double RetrieveUserScore(Episode episode, User user){
        return ScoreEpisodeDAO.RetrieveUserScore(episode, user);
    }
    public static boolean Add_Score(Episode episode, User user, double score){
        return ScoreEpisodeDAO.Add_Score(episode, user, score);
    }

    public static boolean Delete_Score(Episode episode, User user){
        return ScoreEpisodeDAO.Delete_Score(episode, user);
    }
    public static boolean Update_Score(Episode episode, User user, double score){
        return ScoreEpisodeDAO.Update_Score(episode, user, score);
    }
    public static double GetEpisodeScore(Episode episode){
        return ScoreEpisodeDAO.GetEpisodeScore(episode);
    }
}
