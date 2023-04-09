package Controllers;

import DAO.SeasonDAO;
import Entities.Season;
import Services.SeasonService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SeasonController {

    public static void main(String[] args) throws SQLException, IOException {
        SeasonDAO seasonDAO = new SeasonDAO();
        File file = new File("src/main/java/Test/VideoTest.mp4");
        File imageFile = new File("src/main/java/Test/LionTest.jpeg");

        Season season = new Season("SeasonKindaNormal",file,3,6,
                LocalDate.of(2022,12,15),imageFile);

//        System.out.println(seasonDAO.ajout_Season(season));

        List<Season> seasonList = seasonDAO.FindSeasonID(21L);

//        System.out.println(seasonDAO.FindSeasonName("SeasonKindaNormal"));
        System.out.println(seasonDAO.getScoreSeason(seasonList.get(0)));
    }


    public static long StreamAverageScore(List<Long> listScore)
    {
        return SeasonService.StreamAverageScore(listScore);
    }


}
