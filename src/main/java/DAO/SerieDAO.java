package DAO;

import Entities.Episode;
import Entities.Season;
import Entities.Serie;
import Utils.ConxDB;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
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
            String Director = rs.getString("DIRECTOR");
            Date DebutDate = rs.getDate("DEBUT_DATE");
            String Language = rs.getString("LANGUAGE");
            String Country = rs.getString("COUNTRY");
            Blob Thumbnail = rs.getBlob("IMAGE");
            int numsSeasons= rs.getInt("NUMS_SEASONS");
            String StringGenre = rs.getString("LISTEGENRE");
            String[] genreArray = StringGenre.split(",");
            ArrayList<String> genreList = new ArrayList<>(Arrays.asList(genreArray));
            InputStream SerieThumbnail = Thumbnail.getBinaryStream();
            InputStream SerieSynopsis = rs.getBinaryStream("SYNOPSIS");

            //Converting Blob Image to Jpeg File
            File fileThumb = new File("src/main/java/Test/ImgSerie"+ID+".jpeg");
            OutputStream outS = new FileOutputStream(fileThumb);
            byte[] bufferImg = new byte[1024];
            int length;
            while ((length = SerieThumbnail.read(bufferImg)) != -1) {
                outS.write(bufferImg, 0, length);
            }

            //Converting Blob Synopsis to video File .mp4
            Path outputFilePathSynopsis = Paths.get("src/main/java/Test/SynopsisSerie"+ID+".mp4");
            try (OutputStream outputStreamSynopsis = Files.newOutputStream(outputFilePathSynopsis)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = SerieSynopsis.read(buffer)) != -1) {
                    outputStreamSynopsis.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                System.out.println("Error Handelling the Synopsis");
            }
            File fileSynopsis = new File("src/main/java/Test/SynopsisSerie"+ID+".mp4");
            File fileThumbnail = new File("src/main/java/Test/ImgSerie"+ID+".jpeg");

            List<Season> seasonList = SeasonDAO.FindSeasonSerieID(ID);

//            serie = new Serie(ID,SerieName,Director,DebutDate,Language,Country,genreList,fileThumbnail,numsSeasons,fileSynopsis,seasonList,);

        }

        return serieList;
    }



}