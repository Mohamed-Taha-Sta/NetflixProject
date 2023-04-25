package Services;

import Controllers.EpisodeController;
import Controllers.ScoreEpisodeController;
import Controllers.SeasonController;
import Controllers.SerieController;
import DAO.SerieDAO;
import Entities.*;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SerieService {

    public static double StreamAverageScore(Serie serie) throws SQLException, IOException {
        List<Season> seasonList = SeasonController.FindSeasonSerieID(serie.getId());
        List<Double> scoreSeason = new ArrayList<>();
        for (Season season : seasonList) {
            scoreSeason.add(SeasonController.StreamAverageScore(season));
        }
        return Math.round(scoreSeason.stream()
                .collect(Collectors.averagingDouble(Double::doubleValue)));
    }

    public static long AddSerie(Serie Serie) throws SQLException, FileNotFoundException {
        return SerieDAO.AddSerie(Serie);
    }

    public static List<Serie> GetSerieByName(String SerieName) throws SQLException, IOException {
        return SerieDAO.GetSerieByName(SerieName);
    }

    public static List<Serie> GetManySeries(long limit) throws SQLException, IOException {
        return SerieDAO.GetManySeries(limit);
    }

    public static List<Serie> GetAllSeries() throws SQLException, IOException {
        return SerieDAO.GetAllSeries();
    }

    public static List<Serie> GetSeriesByProducer(Producer producer) throws SQLException, IOException {
        return SerieDAO.GetSeriesByProducer(producer);
    }


    public static boolean DeleteSerie(Serie serie) throws SQLException, IOException {
        return SerieDAO.DeleteSerie(serie);
    }


    public static boolean modifimg(Serie serie, File img) throws SQLException, IOException {
        return SerieDAO.modifimg(serie, img);
    }

    public static boolean ModifSynopsisSerie(Serie serie, File NewSynopsis) throws SQLException {
        return SerieDAO.ModifSynopsisSerie(serie, NewSynopsis);
    }

    public static boolean modifnom(Serie Serie, String nom) throws SQLException {
        return SerieDAO.modifnom(Serie, nom);
    }

    public static boolean modifdescription(Serie serie, String description) throws SQLException {
        return SerieDAO.modifdescription(serie, description);
    }

    public static boolean modiflangues(Serie Serie, String langue) throws SQLException {
        return SerieDAO.modiflangues(Serie, langue);
    }

    public static boolean modifpaysoregine(Serie Serie, String paysorgine) throws SQLException {
        return SerieDAO.modifpaysoregine(Serie, paysorgine);
    }

    public static boolean modifAnnerdesoritie(Serie serie, LocalDate date) throws SQLException {
        return SerieDAO.modifAnnerdesoritie(serie, date);
    }

    public static boolean modiflistegenre(Serie serie, List<String> listegenre) throws SQLException {
        return SerieDAO.modiflistegenre(serie, listegenre);
    }

    public static boolean deleteSerie_actsec(Serie serie, Actor act) throws SQLException {
        return SerieDAO.deleteSerie_actsec(serie, act);
    }

    public static boolean deleteSerie_actprinc(Serie serie, Actor act) throws SQLException {
        return SerieDAO.deleteSerie_actprinc(serie, act);
    }

    public static boolean ajoutSerie_actprinc(Serie serie, Actor act) throws SQLException {
        return SerieDAO.ajoutSerie_actprinc(serie, act);
    }

    public static boolean ajoutSerie_actsec(Serie serie, Actor act) throws SQLException {
        return SerieDAO.ajoutSerie_actsec(serie, act);
    }

    public static List<Serie> searchSeries(List<String> searchTerms) throws SQLException, IOException {
        return SerieDAO.searchSeries(searchTerms);
    }

    public static List<Serie> searchSeriesOR(List<String> searchTerms) throws SQLException, IOException {
        return SerieDAO.searchSeries(searchTerms);
    }

    public static List<Serie> GetSerieByID(long ID) throws SQLException, IOException {
        return SerieDAO.GetSerieByID(ID);
    }


    public static List<Serie> getMostRecentSeries(int numSeries) throws SQLException, IOException {
        return SerieDAO.getMostRecentSeries(numSeries);
    }

    public static List<Serie> FindSeriesByActor(Actor act) {
        return SerieDAO.FindSeriesByActor(act);
    }

    public static List<Serie> streamYear(List<Serie> uniqueSeries, LocalDate localDate) {
        return uniqueSeries.stream().filter(serie -> serie.getAnnerdesortie().isAfter(localDate))
                .collect(Collectors.toList());
    }


//    public static List<Serie> GetReleasedEpisode(List<Episode> episode, User user) {
//        return episode.stream()
//                .filter(episode1 -> ScoreEpisodeController.RetrieveUserScore(episode1, user) > 2.5)
//                .map(Episode::getSeasonParentID)
//                .map(seasonId -> {
//                    try {
//                        return SeasonController.FindSeasonID(seasonId);
//                    } catch (SQLException | IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .flatMap(List::stream)
//                .filter(season -> {
//                    try {
//                        return SeasonController.StreamAverageScore(season) > 50;
//                    } catch (SQLException | IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .map(Season::getSERIE_ID)
//                .map(serieId -> {
//                    try {
//                        return SerieController.GetSerieByID(serieId);
//                    } catch (SQLException | IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .flatMap(List::stream)
//                .filter(serie -> {
//                    try {
//                        return SerieController.StreamAverageScore(serie) > 50;
//                    } catch (SQLException | IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .map(serie -> {
//                    try {
//                        return SeasonController.FindSeasonID(serie.getId());
//                    } catch (SQLException | IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .flatMap(List::stream)
//                .map(season -> {
//                    try {
//                        return EpisodeController.FindEpisodeSeasonID(season.getID());
//                    } catch (SQLException | IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .flatMap(List::stream)
//                .filter(episode1 -> episode1.getPremiereDate().isAfter(LocalDate.now()) && episode1.getPremiereDate().isBefore(LocalDate.now().plusDays(14)))
//                .map(Episode::getSeasonParentID)
//                .map(seasonId -> {
//                    try {
//                        return SeasonController.FindSeasonID(seasonId);
//                    } catch (SQLException | IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .flatMap(List::stream)
//                .map(Season::getSERIE_ID)
//                .map(serieId -> {
//                    try {
//                        return SerieController.GetSerieByID(serieId);
//                    } catch (SQLException | IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .flatMap(List::stream)
//                .toList();
//
//    }

    public static List<Serie> GetSerieByIDNoActors(long ID) throws SQLException, IOException {
        return SerieDAO.GetSerieByIDNoActors(ID);
    }

    public static boolean SeriesWithHighScoresByUser(List<Season> seasons, User user) {
        long highSeasons = seasons.stream()
                .filter(season -> {
                    try {
                        return SeasonService.SeasonWithHighScoreByUser(SeasonController.FindEpisodeSeasonID(season), user);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .count();
        return highSeasons > (seasons.size() / 2);
    }


    public static List<Serie> GetReleasedEpisode(List<Serie> series, User user) throws SQLException, IOException {
        List<Serie> auxListSer = new ArrayList<>();

        double sumEpScore = 0;
        long cptEpScore = 0;

        double noteSeason = 0;
        double noteSerie = 0;
        double sumSeasonScore = 0;
        long cptSeasonScore = 0;

        for (Serie serie : series) {
            noteSeason = 0;
            sumSeasonScore = 0;
            cptSeasonScore = 0;
            List<Season> seasonList = SeasonController.FindSeasonSerieID(serie.getId());
            for (Season season : seasonList) {
                sumEpScore = 0;
                cptEpScore = 0;

                List<Episode> episodeList = EpisodeController.FindEpisodeSeasonID(season.getID());
                for (Episode episode : episodeList) {
                    if (((episode.getPremiereDate().isAfter(LocalDate.now()) || episode.getPremiereDate().isEqual(LocalDate.now())) &&
                            episode.getPremiereDate().isBefore(LocalDate.now().plusDays(14)))
                            && (ScoreEpisodeController.Score_Exist(episode, user)))
                    {
                        sumEpScore = sumEpScore + ScoreEpisodeController.RetrieveUserScore(episode, user);
                        cptEpScore = cptEpScore + 1;
                        System.out.println("Note episode "+episode.getName()+" "+sumEpScore);
                    }
                }
                noteSeason = sumEpScore / cptEpScore;
                System.out.println("Note seaosn "+noteSeason);

            }
            sumSeasonScore = sumSeasonScore + noteSeason;
            cptSeasonScore = cptSeasonScore + 1;

            noteSerie = sumSeasonScore / cptSeasonScore;
            if (noteSerie > 2.5) {
                auxListSer.add(serie);
                System.out.println("Note serie "+serie.getNom()+" "+noteSerie);
            }
        }
        System.out.println("here is the list "+auxListSer);
        return auxListSer;
    }
}

//
//
//    public static List<Serie> GetReleasedEpisode(List<Episode> episode, User user) {
//
//        return episode.stream()
//                .filter(episode1 -> ScoreEpisodeController.RetrieveUserScore(episode1, user) > 2.5)
//                .map(Episode::getSeasonParentID)
//                .map(seasonId -> {
//                    try {
//                        return SeasonController.FindSeasonID(seasonId);
//                    } catch (SQLException | IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .flatMap(List::stream)
//                .distinct()
//                .filter(season -> {
//                    try {
//                        return SeasonService.SeasonWithHighScoreByUser(SeasonController.FindEpisodeSeasonID(season), user);
//                    } catch (SQLException | IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .map(Season::getSERIE_ID)
//                .distinct()
//                .map(serieId -> {
//                    try {
//                        return GetSerieByIDNoActors(serieId);
//                    } catch (SQLException | IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .flatMap(List::stream)
//                .filter(serie -> SeriesWithHighScoresByUser(serie.getSeasonList(), user))
//                .map(serie -> {
//                    try {
//                        return SeasonController.FindSeasonID(serie.getId());
//                    } catch (SQLException | IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .flatMap(List::stream)
//                .map(season -> {
//                    try {
//                        return EpisodeController.FindEpisodeSeasonID(season.getID());
//                    } catch (SQLException | IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .flatMap(List::stream)
//                .filter(episode1 -> episode1.getPremiereDate().isAfter(LocalDate.now()) || episode1.getPremiereDate().isEqual(LocalDate.now()))
//                .map(Episode::getSeasonParentID)
//                .map(seasonId -> {
//                    try {
//                        return SeasonController.FindSeasonID(seasonId);
//                    } catch (SQLException | IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .flatMap(List::stream)
//                .map(Season::getSERIE_ID)
//                .map(serieId -> {
//                    try {
//                        return SerieService.GetSerieByIDNoActors(serieId);
//                    } catch (SQLException | IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .flatMap(List::stream)
//                .toList();
//
//    }
//}