package DAO;

import Entities.Season;
import Utils.ConxDB;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SeasonDAO {

    private static final Connection conn = ConxDB.getInstance();

    public static long AddSeason(Season season) throws SQLException, FileNotFoundException  {

        long id=-1;

        String sql = "INSERT INTO Season (ID_SERIE, name, DEBUT_DATE,THUMBNAIL,SYNOPSIS,DESCRIPTION) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"ID"});

        InputStream inputStreamThumbnail = new FileInputStream(season.getThumbnail());
        InputStream inputStreamSynopsis = new FileInputStream(season.getSynopsis());

        try {
        pstmt.setLong(1,season.getSERIE_ID());
        pstmt.setString(2,season.getName());
        pstmt.setDate(3,java.sql.Date.valueOf(season.getDebutDate()));
        pstmt.setBlob(4,inputStreamThumbnail);
        pstmt.setBlob(5,inputStreamSynopsis);
        pstmt.setString(6,season.getDescription());

        int affectedRows = pstmt.executeUpdate();


        ResultSet generatedKeys = pstmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            id = generatedKeys.getLong(1);
        }


        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
            return -1;
        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
            return -1;
        }
        pstmt.close();
        return id;
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
            Blob ThumbnailBlob = rs.getBlob("THUMBNAIL");
            InputStream SeasonThumbnail = ThumbnailBlob.getBinaryStream();

            //Converting Blob Image to Jpeg File
            File fileThumb = new File("src/main/java/Temp/ImgSeason"+ID+".jpeg");
            OutputStream outS = new FileOutputStream(fileThumb);
            byte[] bufferImg = new byte[1024];
            int length;
            while ((length = SeasonThumbnail.read(bufferImg)) != -1) {
                outS.write(bufferImg, 0, length);
            }


            season = new Season(ID,SeasonName,ID_SERIE,fileThumb);

            SeasonList.add(season);

        }
        rs.close();
        pstmt.close();
        return SeasonList;

    }

    public static List<Season> GetAllSeasons() throws SQLException, IOException {


        Season season = null;

        List<Season> SeasonList = new ArrayList<>();

        String sql = "SELECT * FROM SEASON";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            // Retrieve the values from the ResultSet and store them in variables
            long ID = rs.getLong("ID");
            String SeasonName = rs.getString("NAME");
            long ID_SERIE = rs.getLong("ID_SERIE");
            season = new Season(ID,SeasonName,ID_SERIE);
            SeasonList.add(season);
        }
        rs.close();
        pstmt.close();
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

            season = new Season(ID,SeasonName,Description,fileSynopsis,ID_SERIE,num,DebutDate.toLocalDate(),fileThumbnail);

            SeasonList.add(season);
        }
        rs.close();
        pstmt.close();
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

            Blob ThumbnailBlob = rs.getBlob("THUMBNAIL");
            InputStream SeasonThumbnail = ThumbnailBlob.getBinaryStream();

            //Converting Blob Image to Jpeg File
            File fileThumb = new File("src/main/java/Temp/ImgSeason"+ID+".jpeg");
            OutputStream outS = new FileOutputStream(fileThumb);
            byte[] bufferImg = new byte[1024];
            int length;
            while ((length = SeasonThumbnail.read(bufferImg)) != -1) {
                outS.write(bufferImg, 0, length);
            }


            season = new Season(ID,name,SerieID,fileThumb);

            SeasonList.add(season);
        }
        rs.close();
        pstmt.close();
        return SeasonList;

    }



    public static boolean modifimg(Season season, File img) throws SQLException {
        PreparedStatement pstmt = null;
        String sql;

        try {
            // On lit le contenu du fichier dans un tableau de bytes
            byte[] imgBytes = Files.readAllBytes(img.toPath());

            // On prépare la requête SQL avec un paramètre pour le tableau de bytes
            sql = "UPDATE SEASON SET THUMBNAIL = ? WHERE ID = ?";
            pstmt = conn.prepareStatement(sql);

            // On affecte le paramètre avec le tableau de bytes
            pstmt.setBytes(1, imgBytes);
            pstmt.setLong(2, season.getID());

            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier : " + e.getMessage());
        }finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public static boolean ModifSynopsisSeason(Season season,File NewSynopsis) throws SQLException {
        PreparedStatement pstmt = null;
        String sql;

        try {
            InputStream inputStreamSynopsisSeason = new FileInputStream(NewSynopsis);
            sql = "UPDATE SEASON SET SYNOPSIS = ? WHERE ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setBlob(1, inputStreamSynopsisSeason);
            pstmt.setLong(2,season.getID());
            pstmt.executeQuery();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);

        }finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }



    public static boolean modifnom(Season season, String nom) throws SQLException {
        PreparedStatement pstmt = null;
        String sql;

        try {

            sql = "UPDATE SEASON SET Name = '" + nom + "' WHERE ID = " + season.getID();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeQuery();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static boolean modifAnnerdesoritie(Season season, LocalDate date) throws SQLException {
        PreparedStatement pstmt = null;
        String sql;

        try {

            sql = "UPDATE SEASON SET DEBUT_DATE = ? WHERE ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(date));
            pstmt.setLong(2,season.getID());
            pstmt.executeQuery();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static boolean modifdescription(Season season,String description) throws SQLException {
        PreparedStatement pstmt = null;
        String sql;

        try {
            sql = "UPDATE SEASON SET description = ? WHERE ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,description);
            pstmt.setLong(2,season.getID());
            pstmt.executeQuery();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
