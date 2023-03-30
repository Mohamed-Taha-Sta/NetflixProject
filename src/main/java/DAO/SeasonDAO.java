package DAO;

import Entities.Episode;
import Utils.ConxDB;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

public class SeasonDAO {

    private static final Connection conn = ConxDB.getInstance();

    public static boolean ajout_Saison(Episode episode) throws SQLException, FileNotFoundException {

        String sql = "INSERT INTO Season (ID_SERIE, NUM, name, DEBUT_DATE, texte,IMAGE,VIDEO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";



        return false;
    }



    }
