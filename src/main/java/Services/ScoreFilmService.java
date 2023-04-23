package Services;

import DAO.ScoreFilmDAO;
import Entities.Film;
import Entities.User;

public class ScoreFilmService {

    public static boolean Score_Exist(Film film, User user) {
        return ScoreFilmDAO.Score_Exist(film, user);
    }

    public static boolean Add_Score(Film film, User user, double score) {
        return ScoreFilmDAO.Add_Score(film, user, score);
    }

    public static boolean Delete_Score(Film film, User user) {
        return ScoreFilmDAO.Delete_Score(film, user);
    }

    public static boolean Update_Score(Film film, User user, double score) {
        return ScoreFilmDAO.Update_Score(film, user, score);
    }

    public static double  GetFilmScore(Film film){
        return ScoreFilmDAO.GetFilmScore(film);
    }

    public static double RetrieveUserScore(Film film, User user){
        return ScoreFilmDAO.RetrieveUserScore(film, user);
    }
}
