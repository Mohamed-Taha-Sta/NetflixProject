package DAO;

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

    public static boolean ajout_Season(Season season) throws SQLException, FileNotFoundException  {

        String sql = "INSERT INTO Season (ID_SERIE, NUM, name, DEBUT_DATE,THUMBNAIL,SYNOPSIS) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

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

    public List<Season> FindSeasonName(String SeasonName) throws SQLException, IOException {

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
            int num = rs.getInt("NUM");
            Date DebutDate = rs.getDate("DEBUT_DATE");
            Blob ThumbnailBlob = rs.getBlob("THUMBNAIL");
            InputStream SeasonThumbnail = ThumbnailBlob.getBinaryStream();
            InputStream SeasonSynopsis = rs.getBinaryStream("SYNOPSIS");

            //Converting Blob Image to Jpeg File
            File fileThumb = new File("src/main/java/Test/ImgSeason"+ID+".jpeg");
            OutputStream outS = new FileOutputStream(fileThumb);
            byte[] bufferImg = new byte[1024];
            int length;
            while ((length = SeasonThumbnail.read(bufferImg)) != -1) {
                outS.write(bufferImg, 0, length);
            }

            //Converting Blob Synopsis to video File .mp4
            Path outputFilePathSynopsis = Paths.get("src/main/java/Test/SynopsisSeason"+ID+".mp4");
            try (OutputStream outputStreamSynopsis = Files.newOutputStream(outputFilePathSynopsis)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = SeasonSynopsis.read(buffer)) != -1) {
                    outputStreamSynopsis.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                System.out.println("Error Handelling the Synopsis");
            }
            File fileSynopsis = new File("src/main/java/Test/SynopsisSeason"+ID+".mp4");
            File fileThumbnail = new File("src/main/java/Test/ImgSeason"+ID+".jpeg");

            List<Episode> episodeList = EpisodeDAO2.FindEpisodeSeasonID(ID);

            season = new Season(ID,fileSynopsis,ID_SERIE,num,DebutDate.toLocalDate(),fileThumbnail,episodeList);

            SeasonList.add(season);

        }

        return SeasonList;

    }







}
