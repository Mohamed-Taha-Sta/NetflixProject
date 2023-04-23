package Controllers;

import DAO.ScoreFilmDAO;
import Entities.Film;
import Entities.User;
import Services.ScoreFilmService;

public class ScoreFilmController {
    public static boolean Score_Exist(Film film, User user) {
        return ScoreFilmService.Score_Exist(film, user);
    }

    public static boolean Add_Score(Film film, User user, double score) {
        return ScoreFilmService.Add_Score(film, user, score);
    }

    public static boolean Delete_Score(Film film, User user) {
        return ScoreFilmService.Delete_Score(film, user);
    }

    public static boolean Update_Score(Film film, User user, double score) {
        return ScoreFilmService.Update_Score(film, user, score);
    }

    public static double  GetFilmScore(Film film){
        return ScoreFilmService.GetFilmScore(film);
    }
    public static double RetrieveUserScore(Film film, User user){
        return ScoreFilmDAO.RetrieveUserScore(film, user);
    }
}
