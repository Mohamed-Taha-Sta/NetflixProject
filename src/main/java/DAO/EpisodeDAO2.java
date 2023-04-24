package DAO;

import Entities.*;
import Utils.ConxDB;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EpisodeDAO2 {

    private static final Connection conn = ConxDB.getInstance();

    public EpisodeDAO2() {
    }

    public static long AddEpisode(Episode episode) throws FileNotFoundException {
        String sql = null;
        long id = -1;
        PreparedStatement pstmt = null;

        if (episode.getDescription() != null && episode.getSynopsis() != null) {
            sql = "INSERT INTO episodes (season_ID, NUM, name, DEBUT_DATE, premiere_Date,IMAGE,VIDEO,Description,Synopsis) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        }else if(episode.getDescription() != null){
            sql = "INSERT INTO episodes (season_ID, NUM, name, DEBUT_DATE, premiere_Date,IMAGE,VIDEO,Description) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        }else if (episode.getSynopsis() != null){
            sql = "INSERT INTO episodes (season_ID, NUM, name, DEBUT_DATE, premiere_Date,IMAGE,VIDEO,Synopsis) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        }

        InputStream inputStream = new FileInputStream(episode.getImage());
        InputStream inputStreamVideo = new FileInputStream(episode.getMedia());
        InputStream inputStreamSynopsis = null;
        if (episode.getSynopsis()!=null)
            inputStreamSynopsis = new FileInputStream(episode.getSynopsis());

        try {
            pstmt = conn.prepareStatement(sql, new String[]{"ID"});

            // Set the values for the parameters
            pstmt.setLong(1, episode.getSeasonParentID());
            pstmt.setInt(2, episode.getNumber());
            pstmt.setString(3, episode.getName());
            pstmt.setDate(4, java.sql.Date.valueOf(episode.getDebutDate()));
            pstmt.setDate(5, java.sql.Date.valueOf(episode.getPremiereDate()));
            pstmt.setBlob(6, inputStream);
            pstmt.setBlob(7, inputStreamVideo);
            pstmt.setLong(8, 0);
            pstmt.setLong(9, 0);
            pstmt.setLong(10, 0);
            if (episode.getDescription() != null && episode.getSynopsis() != null) {
                pstmt.setString(11, episode.getDescription());
                pstmt.setBlob(12, inputStreamSynopsis);
            } else if (episode.getDescription() != null) {
                pstmt.setString(11, episode.getDescription());
            } else if (episode.getSynopsis() != null)
                pstmt.setBlob(11, inputStreamSynopsis);
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
            }

        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
            return -1;
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
            return -1;
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return id;

    }

    public static List<Episode> FindEpisodeID(long ID) throws SQLException, IOException {
        List<Episode> episodeList = new ArrayList<>();
        Episode episode;
        String sql = "SELECT * FROM episodes WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, ID);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            // Retrieve the values from the ResultSet and store them in variables
            long seasonID = rs.getLong("season_ID");
            int episodeNumber = rs.getInt("NUM");
            String EpisodeName = rs.getString("Name");
            Date diffusionDate = rs.getDate("DEBUT_DATE");
            Date premiereDate = rs.getDate("premiere_Date");
            Blob episodeImageB = rs.getBlob("image");
            InputStream episodeImage = episodeImageB.getBinaryStream();
            String Description = rs.getString("Description");
            InputStream episodeSynopsis = rs.getBinaryStream("SYNOPSIS");
            InputStream episodeVideo = rs.getBinaryStream("video");

            //Converting Blob into An JPEG File
            File fileImg = new File("src/main/java/Temp/ImgEp" + ID + ".jpeg");
            OutputStream outS = new FileOutputStream(fileImg);
            byte[] bufferImg = new byte[1024];
            int length;
            while ((length = episodeImage.read(bufferImg)) != -1) {
                outS.write(bufferImg, 0, length);
            }

            //Handeling the Video, from inputStream
            Path outputFilePath = Paths.get("src/main/java/Temp/VideoEp" + ID + ".mp4");
            try (OutputStream outputStream = Files.newOutputStream(outputFilePath)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = episodeVideo.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                System.out.println("Error Handelling the video");
            }
            File file = new File("src/main/java/Temp/VideoEp" + ID + ".mp4");
            File fileImage = new File("src/main/java/Temp/ImgEp" + ID + ".jpeg");

            //Handeling Synopsis Export
            Path outputFilePathSynopsis = Paths.get("src/main/java/Temp/SynopsisEp" + ID + ".mp4");
            try (OutputStream outputStreamSynopsis = Files.newOutputStream(outputFilePathSynopsis)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = episodeSynopsis.read(buffer)) != -1) {
                    outputStreamSynopsis.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                System.out.println("Error Handelling the Synopsis");
            }
            File fileSynopsis = new File("src/main/java/Temp/SynopsisEp" + ID + ".mp4");

            episode = new Episode(ID, seasonID,Description, EpisodeName, episodeNumber, diffusionDate.toLocalDate(), premiereDate.toLocalDate(), fileImage, fileSynopsis, file);

            episodeList.add(episode);

        }
        rs.close();
        pstmt.close();
        return episodeList;
    }

    public static List<Episode> GetAllEpisodes() throws SQLException, IOException {
        Episode episode;
        List<Episode> episodeList = new ArrayList<>();
        String sql = "SELECT * FROM EPISODES";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            // Retrieve the values from the ResultSet and store them in variables
            long ID = rs.getLong("ID");
            String EpsisodeName = rs.getString("NAME");
            long SEASON_ID = rs.getLong("SEASON_ID");
            episode = new Episode(ID,EpsisodeName,SEASON_ID);
            episodeList.add(episode);
        }
        rs.close();
        pstmt.close();
        return episodeList;
    }


    public static List<Episode> FindEpisodeName(String EpisodeName) throws SQLException, IOException {
        List<Episode> episodeList = new ArrayList<>();
        Episode episode;
        String sql = "SELECT * FROM episodes WHERE NAME like ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "%" + EpisodeName + "%");
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            // Retrieve the values from the ResultSet and store them in variables
            long ID = rs.getLong("ID");
            Long seasonID = rs.getLong("season_ID");

            Date diffusionDate = rs.getDate("DEBUT_DATE");
            Date premiereDate = rs.getDate("premiere_Date");
            Blob episodeImageB = rs.getBlob("image");
            InputStream episodeImage = episodeImageB.getBinaryStream();

            //Converting Blob Image to Jpeg File
            File fileImg = new File("src/main/java/Temp/ImgEp" + ID + ".jpeg");
            OutputStream outS = new FileOutputStream(fileImg);
            byte[] bufferImg = new byte[1024];
            int length;
            while ((length = episodeImage.read(bufferImg)) != -1) {
                outS.write(bufferImg, 0, length);
            }
            episode = new Episode(ID, seasonID, EpisodeName, diffusionDate.toLocalDate(), premiereDate.toLocalDate(), fileImg);

            episodeList.add(episode);
        }
        rs.close();
        pstmt.close();
        return episodeList;
    }
    public static List<Episode> FindEpisodeSeasonID(long seasonID) throws SQLException, IOException {
        List<Episode> episodeList = new ArrayList<>();
        Episode episode;
        String sql = "SELECT * FROM episodes WHERE SEASON_ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, seasonID);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            long ID = rs.getLong("ID");
            String EpisodeName = rs.getString("NAME");
            Date diffusionDate = rs.getDate("DEBUT_DATE");
            Date premiereDate = rs.getDate("premiere_Date");
            Blob episodeImageB = rs.getBlob("image");
            InputStream episodeImage = episodeImageB.getBinaryStream();

            //Converting Blob Image to Jpeg File
            File fileImg = new File("src/main/java/Temp/ImgEp" + ID + ".jpeg");
            OutputStream outS = new FileOutputStream(fileImg);
            byte[] bufferImg = new byte[1024];
            int length;
            while ((length = episodeImage.read(bufferImg)) != -1) {
                outS.write(bufferImg, 0, length);
            }

            episode = new Episode(ID, seasonID, EpisodeName, diffusionDate.toLocalDate(), premiereDate.toLocalDate(), fileImg);

            episodeList.add(episode);

        }
        rs.close();
        pstmt.close();
        return episodeList;
    }


    public static boolean modifImg(Episode episode, File img) throws SQLException {
        PreparedStatement pstmt = null;
        String sql;

        try {
            // On lit le contenu du fichier dans un tableau de bytes
            byte[] imgBytes = Files.readAllBytes(img.toPath());

            // On prépare la requête SQL avec un paramètre pour le tableau de bytes
            sql = "UPDATE EPISODES SET IMAGE = ? WHERE ID = ?";
            pstmt = conn.prepareStatement(sql);

            // On affecte le paramètre avec le tableau de bytes
            pstmt.setBytes(1, imgBytes);
            pstmt.setLong(2, episode.getID());

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

    public static boolean modifSynopsis(Episode episode,File NewSynopsis) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs;
        String sql;

        try {
            InputStream inputStreamSynopsisEpisode = new FileInputStream(NewSynopsis);
            sql = "UPDATE EPISODES SET SYNOPSIS = ? WHERE ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setBlob(1, inputStreamSynopsisEpisode);
            pstmt.setLong(2,episode.getID());
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


    public static boolean modifVideo(Episode episode,File NewVideo) throws SQLException {
        PreparedStatement pstmt = null;
        String sql;

        try {
            InputStream inputStreamSynopsisEpisode = new FileInputStream(NewVideo);
            sql = "UPDATE EPISODES SET VIDEO = ? WHERE ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setBlob(1, inputStreamSynopsisEpisode);
            pstmt.setLong(2,episode.getID());
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


    public static boolean modifNom(Episode episode, String nom) throws SQLException {
        PreparedStatement pstmt = null;
        String sql;

        try {

            sql = "UPDATE EPISODES SET Name = ? WHERE ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,nom);
            pstmt.setLong(2,episode.getID());
            pstmt.executeQuery();


            return true;
        } catch (SQLException e) {
            System.out.println("error dans la connection de la base"+e.getMessage());
//            pstmt.close();
//            conn.close();
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


    public static boolean modifDebutDate(Episode episode, LocalDate date) throws SQLException {
        PreparedStatement pstmt = null;
        String sql;

        try {

            sql = "UPDATE EPISODES SET DEBUT_DATE = ? WHERE ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(date));
            pstmt.setLong(2,episode.getID());
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

    public static boolean modifPremiereDate(Episode episode, LocalDate date) throws SQLException {
        PreparedStatement pstmt = null;
        String sql;

        try {

            sql = "UPDATE EPISODES SET PREMIERE_DATE = ? WHERE ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(date));
            pstmt.setLong(2,episode.getID());
            pstmt.executeQuery();

            return true;
        } catch (SQLException e) {
            System.out.println("error dans la connection de la base"+e.getMessage());
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


    public static boolean modifDescription(Episode episode,String description) throws SQLException {
        PreparedStatement pstmt = null;
        String sql;

        try {
            sql = "UPDATE EPISODES SET description = ? WHERE ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,description);
            pstmt.setLong(2,episode.getID());
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
