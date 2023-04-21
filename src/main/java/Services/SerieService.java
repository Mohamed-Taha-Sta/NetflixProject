package Services;

import Controllers.SeasonController;
import Controllers.SerieController;
import DAO.SerieDAO;
import Entities.Episode;
import Entities.Producer;
import Entities.Season;
import Entities.Serie;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SerieService {



    public static long StreamAverageScore(Serie serie) throws SQLException, IOException {
        List<Season> seasonList = SeasonController.FindSeasonSerieID(serie.getId());
        List<Long> scoreSeason = new ArrayList<>();
        for(Season season : seasonList)
        {
            scoreSeason.add(SeasonController.StreamAverageScore(season));
        }
        return Math.round(scoreSeason.stream()
                .collect(Collectors.averagingLong(Long::longValue)));
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





}
