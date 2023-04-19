package DAO;

import Controllers.SeasonController;
import Entities.Episode;
import Entities.Season;
import Utils.ConxDB;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeasonDAO {

    private static final Connection conn = ConxDB.getInstance();

    public static boolean AddSeason(Season season) throws SQLException, FileNotFoundException  {

        String sql = "INSERT INTO Season (ID_SERIE, NUM, name, DEBUT_DATE,THUMBNAIL,SYNOPSIS,DESCRIPTION) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        InputStream inputStreamThumbnail = new FileInputStream(season.getThumbnail());
        InputStream inputStreamSynopsis = new FileInputStream(season.getSynopsis());

        try {
        pstmt.setLong(1,season.getSERIE_ID());
        pstmt.setInt(2,season.getNumber());
        pstmt.setString(3,season.getName());
        pstmt.setDate(4,java.sql.Date.valueOf(season.getDebutDate()));
        pstmt.setBlob(5,inputStreamThumbnail);
        pstmt.setBlob(6,inputStreamSynopsis);
        pstmt.setString(7,season.getDescription());

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

    public static List<Season> FindSeasonName(String SeasonName) throws SQLException, IOException {

        Season season = null;

        List<Season> SeasonList = new ArrayList<>();

        String sql = "SELECT * FROM SEASON WHERE NAME like ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, "%"+SeasonName+"%");

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            // Retrieve the values from the ResultSet and store them in variables
            long ID = rs.getLong("ID");
            long ID_SERIE = rs.getLong("ID_SERIE");
            String name = rs.getString("NAME");
            String Description = rs.getString("DESCRIPTION");
            int num = rs.getInt("NUM");
            Date DebutDate = rs.getDate("DEBUT_DATE");
            Blob ThumbnailBlob = rs.getBlob("THUMBNAIL");
            InputStream SeasonThumbnail = ThumbnailBlob.getBinaryStream();
            InputStream SeasonSynopsis = rs.getBinaryStream("SYNOPSIS");

            //Converting Blob Image to Jpeg File
            File fileThumb = new File("src/main/java/Temp/ImgSeason"+ID+".jpeg");
            OutputStream outS = new FileOutputStream(fileThumb);
            byte[] bufferImg = new byte[1024];
            int length;
            while ((length = SeasonThumbnail.read(bufferImg)) != -1) {
                outS.write(bufferImg, 0, length);
            }

            //Converting Blob Synopsis to video File .mp4
            Path outputFilePathSynopsis = Paths.get("src/main/java/Temp/SynopsisSeason"+ID+".mp4");
            try (OutputStream outputStreamSynopsis = Files.newOutputStream(outputFilePathSynopsis)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = SeasonSynopsis.read(buffer)) != -1) {
                    outputStreamSynopsis.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                System.out.println("Error Handelling the Synopsis");
            }
            File fileSynopsis = new File("src/main/java/Temp/SynopsisSeason"+ID+".mp4");
            File fileThumbnail = new File("src/main/java/Temp/ImgSeason"+ID+".jpeg");

            List<Episode> episodeList = EpisodeDAO2.FindEpisodeSeasonID(ID);

            season = new Season(ID,SeasonName,Description,fileSynopsis,ID_SERIE,num,DebutDate.toLocalDate(),fileThumbnail,episodeList);

            SeasonList.add(season);

        }

        return SeasonList;

    }

    public static List<Season> FindSeasonID(long ID) throws SQLException, IOException {

        Season season = null;

        List<Season> SeasonList = new ArrayList<>();

        String sql = "SELECT * FROM SEASON WHERE ID = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setLong(1, ID);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            // Retrieve the values from the ResultSet and store them in variables
//            long ID = rs.getLong("ID");
            long ID_SERIE = rs.getLong("ID_SERIE");
            String SeasonName = rs.getString("NAME");
            String Description = rs.getString("Description");
            int num = rs.getInt("NUM");
            Date DebutDate = rs.getDate("DEBUT_DATE");
            Blob ThumbnailBlob = rs.getBlob("THUMBNAIL");
            InputStream SeasonThumbnail = ThumbnailBlob.getBinaryStream();
            InputStream SeasonSynopsis = rs.getBinaryStream("SYNOPSIS");

            //Converting Blob Image to Jpeg File
            File fileThumb = new File("src/main/java/Temp/ImgSeason"+ID+".jpeg");
            OutputStream outS = new FileOutputStream(fileThumb);
            byte[] bufferImg = new byte[1024];
            int length;
            while ((length = SeasonThumbnail.read(bufferImg)) != -1) {
                outS.write(bufferImg, 0, length);
            }

            //Converting Blob Synopsis to video File .mp4
            Path outputFilePathSynopsis = Paths.get("src/main/java/Temp/SynopsisSeason"+ID+".mp4");
            try (OutputStream outputStreamSynopsis = Files.newOutputStream(outputFilePathSynopsis)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = SeasonSynopsis.read(buffer)) != -1) {
                    outputStreamSynopsis.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                System.out.println("Error Handelling the Synopsis");
            }
            File fileSynopsis = new File("src/main/java/Temp/SynopsisSeason"+ID+".mp4");
            File fileThumbnail = new File("src/main/java/Temp/ImgSeason"+ID+".jpeg");

            List<Episode> episodeList = EpisodeDAO2.FindEpisodeSeasonID(ID);

            season = new Season(ID,SeasonName,Description,fileSynopsis,ID_SERIE,num,DebutDate.toLocalDate(),fileThumbnail,episodeList);

            SeasonList.add(season);
        }

        return SeasonList;

    }


    public static List<Season> FindSeasonSerieID(Long SerieID) throws SQLException, IOException {

        List<Season> SeasonList = new ArrayList<>();

        Season season = null;

        String sql = "SELECT * FROM SEASON WHERE ID_SERIE = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setLong(1, SerieID);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            long ID = rs.getLong("ID");
            String name = rs.getString("NAME");
            int num = rs.getInt("NUM");
            Date DebutDate = rs.getDate("DEBUT_DATE");
            Blob ThumbnailBlob = rs.getBlob("THUMBNAIL");
            InputStream SeasonThumbnail = ThumbnailBlob.getBinaryStream();
            InputStream SeasonSynopsis = rs.getBinaryStream("SYNOPSIS");

            //Converting Blob Image to Jpeg File
            File fileThumb = new File("src/main/java/Temp/ImgSeason"+ID+".jpeg");
            OutputStream outS = new FileOutputStream(fileThumb);
            byte[] bufferImg = new byte[1024];
            int length;
            while ((length = SeasonThumbnail.read(bufferImg)) != -1) {
                outS.write(bufferImg, 0, length);
            }

            //Converting Blob Synopsis to video File .mp4
            Path outputFilePathSynopsis = Paths.get("src/main/java/Temp/SynopsisSeason"+ID+".mp4");
            try (OutputStream outputStreamSynopsis = Files.newOutputStream(outputFilePathSynopsis)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = SeasonSynopsis.read(buffer)) != -1) {
                    outputStreamSynopsis.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                System.out.println("Error Handelling the Synopsis");
            }
            File fileSynopsis = new File("src/main/java/Temp/SynopsisSeason"+ID+".mp4");
            File fileThumbnail = new File("src/main/java/Temp/ImgSeason"+ID+".jpeg");

            List<Episode> episodeList = EpisodeDAO2.FindEpisodeSeasonID(ID);

            season = new Season(ID,fileSynopsis,SerieID,num,DebutDate.toLocalDate(),fileThumbnail,episodeList);

            SeasonList.add(season);
        }

    return SeasonList;

    }

//
//    Was Moved to SeasonService and further optimised
//
//    public static long getScoreSeason(Season season) throws SQLException, IOException {
//
//        List<Episode> episodeList = EpisodeDAO2.FindEpisodeSeasonID(season.getID());
//
//        List<Long> listScore = new ArrayList<>();
//
//        for(Episode episode : episodeList)
//        {
//            long score = EpisodeDAO2.getScoreEpisode(episode);
//            listScore.add(score);
//        }
//
//        return SeasonController.StreamAverageScore(listScore);
//
//    }


//
//    Was Moved to SeasonService and further optimised
//
//    public static long getViewNbrSeason(Season season) throws SQLException, IOException {
//
//        List<Episode> episodeList = EpisodeDAO2.FindEpisodeSeasonID(season.getID());
//
//        return SeasonController.StreamSumViewNumber(episodeList);
//    }
//




}
