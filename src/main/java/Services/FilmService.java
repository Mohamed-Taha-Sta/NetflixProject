package Services;

import DAO.FilmDAO;
import Entities.Actor;
import Entities.Film;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilmService {
    public static boolean Add(Film film){
        return FilmDAO.Add(film);
    }
    public static List<Film> FindByID(Long filmid){
        return FilmDAO.FindByID(filmid);
    }
    public static ArrayList<Film> FindByName(String filmnom){
        return FilmService.FindByName(filmnom);
    }
    public static ArrayList<Film> FindByActor(Actor act){
        return FilmDAO.FindByActor(act);
    }
    public static List<Film> filterByGenre( String genreFilter) {
        List<Film> films=FilmDAO.FindByName("");

            return films.stream()
                    .filter(film -> film.getListegenre().toString().contains(genreFilter))
                    .collect(Collectors.toList());
        }

}
