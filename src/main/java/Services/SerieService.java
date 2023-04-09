package Services;

import DAO.SerieDAO;
import Entities.Serie;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SerieService {


    public static long AddSerie(Serie Serie) throws SQLException, FileNotFoundException {
        return SerieDAO.AddSerie(Serie);
    }

    public static List<Serie> GetSerieByName(String SerieName) throws SQLException, IOException {
        return SerieDAO.GetSerieByName(SerieName);
    }






}
