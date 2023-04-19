package Controllers;

import DAO.SeasonDAO;
import Entities.Episode;
import Entities.Season;
import Services.SeasonService;
import Services.SerieService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SeasonController {

    public static void main(String[] args) throws SQLException, IOException {
        SeasonDAO seasonDAO = new SeasonDAO();
        File file = new File("src/main/java/Test/Synopsis.mp4");
        File imageFile = new File("src/main/java/Test/SaisonPlaceHolder.jpg");

        Season season = new Season("Season1",file,"Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
                "when an unknown printer took a galley of type and scrambled it to make a type specimen book.",42,1,
                LocalDate.of(2022,12,15),imageFile);

        AddSeason(season);

//        System.out.println(seasonDAO.AddSeason(season));

//        List<Season> seasonList = seasonDAO.FindSeasonID(21L);

//        System.out.println(seasonDAO.FindSeasonName("SeasonKindaNormal"));
//        System.out.println(seasonDAO.getScoreSeason(seasonList.get(0)));
//        System.out.println(seasonDAO.getViewNbrSeason(seasonList.get(0)));
//        List<Episode> episodeList = FindEpisodeSeasonID(seasonList.get(0));


    }


    public static long StreamAverageScore(Season season) throws SQLException, IOException {return SeasonService.StreamAverageScore(season);}

    public static long StreamSumViewNumber(Season season) throws SQLException, IOException {return SeasonService.StreamSumViewNumber(season);}

    public static List<Episode> FindEpisodeSeasonID(Season season) throws SQLException, IOException {return EpisodeController.FindEpisodeSeasonID(season.getID());}

    public static long getVotesSeason(Season season) throws SQLException, IOException {return SeasonService.getVotesSeason(season);}

    public static List<Season> FindSeasonSerieID(Long SerieID) throws SQLException, IOException {return SeasonService.FindSeasonSerieID(SerieID);}

    public static List<Season> FindSeasonID(Long SerieID) throws SQLException, IOException {return SeasonService.FindSeasonID(SerieID);}

    public static List<Season> FindSeasonName(String SerieName) throws SQLException, IOException {return SeasonService.FindSeasonName(SerieName);}

    public static boolean AddSeason(Season season) throws SQLException, IOException {return SeasonService.AddSeason(season);}











}
