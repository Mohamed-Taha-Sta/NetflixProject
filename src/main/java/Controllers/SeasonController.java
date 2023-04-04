package Controllers;

import DAO.SeasonDAO;
import Entities.Season;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class SeasonController {

    public static void main(String[] args) throws SQLException, IOException {
        SeasonDAO seasonDAO = new SeasonDAO();
        File file = new File("src/main/java/Test/VideoTest.mp4");
        File imageFile = new File("src/main/java/Test/LionTest.jpeg");

        Season season = new Season("SeasonKindaNormal",file,3,6,
                LocalDate.of(2022,12,15),imageFile);

//        System.out.println(seasonDAO.ajout_Season(season));

        System.out.println(seasonDAO.FindSeasonName("SeasonKindaNormal"));
    }



}
