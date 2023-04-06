package DAO;

import Entities.Season;
import Entities.Serie;
import Utils.ConxDB;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SerieDAO {

    private static final Connection conn = ConxDB.getInstance();

    public static boolean ajout_Serie(Serie Serie) throws SQLException, FileNotFoundException {

        String sql = "INSERT INTO Season (Name, Director, DEBUT_DATE, LANGUAGE, COUNTRY, Image, NUMS_SEASONS, SYNOPSIS, ListeGenre) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        InputStream inputStreamThumbnail = new FileInputStream(Serie.getImg());
        InputStream inputStreamSynopsis = new FileInputStream(Serie.getSynopsis());
        try {
            pstmt.setString(1,Serie.getNom());
            pstmt.setString(2,Serie.getRealisateur());
            pstmt.setDate(3,java.sql.Date.valueOf(Serie.getAnnerdesortie()));
            pstmt.setString(4,Serie.getLangue());
            pstmt.setString(5,Serie.getPaysorigine());
            pstmt.setBlob(6,inputStreamThumbnail);
            pstmt.setLong(7,Serie.getSeasonNumber());
            pstmt.setBlob(8,inputStreamSynopsis);
            pstmt.setString(9,String.join(",", Serie.getActorList().stream().map(Object::toString).toArray(String[]::new)));
            int affectedRows = pstmt.executeUpdate();

        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
            return false;
        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public List<Serie> FindSeasonName(String SerieName) throws SQLException, IOException {

        Serie serie = null;

        List<Serie> serieList = new ArrayList<>();

        String sql = "SELECT * FROM SEASON WHERE NAME like ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, "%"+SerieName+"%");

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {

            long ID = rs.getLong("ID_SERIE");
            String Name = rs.getString("NAME");
            String Director = rs.getString("DIRECTOR");
            Date DebutDate = rs.getDate("DEBUT_DATE");
            String Language = rs.getString("ID_SERIE");



        }

        return serieList;
    }



}