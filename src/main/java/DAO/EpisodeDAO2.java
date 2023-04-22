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

    public EpisodeDAO2() throws SQLException {
    }

    public static long AddEpisode(Episode episode) throws SQLException, FileNotFoundException {
        String sql = null;
        long id = -1;

        if (episode.getDescription() != null && episode.getSynopsis() != null) {
            sql = "INSERT INTO episodes (season_ID, NUM, name, DEBUT_DATE, premiere_Date,IMAGE,VIDEO,VOTES,SCORE,VIEW_NBR,Description,Synopsis) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        }else if(episode.getDescription() != null){
            sql = "INSERT INTO episodes (season_ID, NUM, name, DEBUT_DATE, premiere_Date,IMAGE,VIDEO,VOTES,SCORE,VIEW_NBR,Description) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        }else if (episode.getSynopsis() != null){
            sql = "INSERT INTO episodes (season_ID, NUM, name, DEBUT_DATE, premiere_Date,IMAGE,VIDEO,VOTES,SCORE,VIEW_NBR,Synopsis) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        }

//        InputStream inputStream = new FileInputStream("C:\\Users\\Taha\\IdeaProjects\\NetflixProject1\\src\\main\\java\\Test\\LionTest.jpeg");
        InputStream inputStream = new FileInputStream(episode.getImage());
        InputStream inputStreamVideo = new FileInputStream(episode.getMedia());
        InputStream inputStreamSynopsis = null;
        if (episode.getSynopsis()!=null)
            inputStreamSynopsis = new FileInputStream(episode.getSynopsis());

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"ID"});

            // Set the values for the parameters
            pstmt.setLong(1, episode.getSeasonParentID());
            pstmt.setInt(2, episode.getNumber());
            pstmt.setString(3, episode.getName());
            pstmt.setDate(4, java.sql.Date.valueOf(episode.getDebutDate()));
            pstmt.setDate(5, java.sql.Date.valueOf(episode.getPremiereDate()));
            pstmt.setBlob(6, inputStream); // replace inputStream with the actual image data
            pstmt.setBlob(7, inputStreamVideo); // replace "https://example.com/video.mp4" with the actual video URL value
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
            int affectedRows = pstmt.executeUpdate();


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
        }  // end finally try

        return id;

    }

    public static List<Episode> FindEpisodeID(Long ID) throws SQLException, IOException {

        List<Episode> episodeList = new ArrayList<>();

        Episode episode = null;

        Resume resume = null;

        String sql = "SELECT * FROM episodes WHERE id = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setLong(1, ID);

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            // Retrieve the values from the ResultSet and store them in variables
            Long seasonID = rs.getLong("season_ID");
            int episodeNumber = rs.getInt("NUM");
            long episodeViews = rs.getInt("VIEW_NBR");
            long episodeScore = rs.getInt("SCORE");
            long episodeVotes = rs.getInt("VOTES");
            String EpisodeName = rs.getString("Name");
            Date diffusionDate = rs.getDate("DEBUT_DATE");
            Date premiereDate = rs.getDate("premiere_Date");
            Blob episodeImageB = rs.getBlob("image");
            InputStream episodeImage = episodeImageB.getBinaryStream();
//            InputStream episodeImage = rs.getBinaryStream("image");
            String Description = rs.getString("Description");
            InputStream episodeSynopsis = rs.getBinaryStream("SYNOPSIS");
            InputStream episodeVideo = rs.getBinaryStream("video");

            //Converting Blob into An Image
//            byte[] imageData = episodeImage.getBytes(1, (int) episodeImage.length());
//            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageData));
//            Image image = SwingFXUtils.toFXImage(bufferedImage, null);


            //Converting Blob into An JPEG File
            File fileImg = new File("src/main/java/Temp/ImgEp" + ID + ".jpeg");
            OutputStream outS = new FileOutputStream(fileImg);
            byte[] bufferImg = new byte[1024];
            int length;
            while ((length = episodeImage.read(bufferImg)) != -1) {
                outS.write(bufferImg, 0, length);
            }

            //DO NOT DELETE THIS CODE
            //            File outputFile = new File("C:\\Users\\Taha\\IdeaProjects\\NetflixProject1\\src\\main\\java\\Test\\test.jpeg");
            //
            //            try {
            //                ImageIO.write(bufferedImage, "jpeg", outputFile);
            //            } catch (IOException e) {
            //                // Handle the exception
            //            }

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

//            System.out.println(episodeSynopsis);

            File fileSynopsis = null;
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
            fileSynopsis = new File("src/main/java/Temp/SynopsisEp" + ID + ".mp4");

            episode = new Episode(ID, seasonID,Description, EpisodeName, episodeNumber, diffusionDate.toLocalDate(), premiereDate.toLocalDate(), fileImage, fileSynopsis, file, episodeViews, episodeScore, episodeVotes);

            episodeList.add(episode);

        }

        return episodeList;
    }

    public static List<Episode> FindEpisodeName(String EpisodeName) throws SQLException, IOException {

        List<Episode> episodeList = new ArrayList<>();

        Episode episode = null;

        Resume resume = null;

        String sql = "SELECT * FROM episodes WHERE NAME like ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, "%" + EpisodeName + "%");

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            // Retrieve the values from the ResultSet and store them in variables
            long ID = rs.getLong("ID");
            Long seasonID = rs.getLong("season_ID");
//            int episodeNumber = rs.getInt("NUM");
//            long episodeViews = rs.getLong("VIEW_NBR");
//            long episodeScore = rs.getLong("SCORE");
//            long episodeVotes = rs.getLong("VOTES");
            Date diffusionDate = rs.getDate("DEBUT_DATE");
            Date premiereDate = rs.getDate("premiere_Date");
            Blob episodeImageB = rs.getBlob("image");
            InputStream episodeImage = episodeImageB.getBinaryStream();
//            String Description = rs.getString("Description");
//            InputStream episodeSynopsis = rs.getBinaryStream("SYNOPSIS");
//            InputStream episodeVideo = rs.getBinaryStream("video");

            //Converting Blob into An Image
//                    byte[] imageData = episodeImage.getBytes(1, (int) episodeImage.length());
//                    BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageData));
//                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);

            //Converting Blob Image to Jpeg File
            File fileImg = new File("src/main/java/Temp/ImgEp" + ID + ".jpeg");
            OutputStream outS = new FileOutputStream(fileImg);
            byte[] bufferImg = new byte[1024];
            int length;
            while ((length = episodeImage.read(bufferImg)) != -1) {
                outS.write(bufferImg, 0, length);
            }

            //DO NOT DELETE THIS CODE
            //            File outputFile = new File("C:\\Users\\Taha\\IdeaProjects\\NetflixProject1\\src\\main\\java\\Test\\test.jpeg");
            //
            //            try {
            //                ImageIO.write(bufferedImage, "jpeg", outputFile);
            //            } catch (IOException e) {
            //                // Handle the exception
            //            }

//            //Handeling the Video, from inputStream
//            Path outputFilePath = Paths.get("src/main/java/Temp/VideoEp" + ID + ".mp4");
//            try (OutputStream outputStream = Files.newOutputStream(outputFilePath)) {
//                byte[] buffer = new byte[4096];
//                int bytesRead;
//                while ((bytesRead = episodeVideo.read(buffer)) != -1) {
//                    outputStream.write(buffer, 0, bytesRead);
//                }
//            } catch (IOException e) {
//                System.out.println("Error Handelling the video");
//            }
//            File file = new File("src/main/java/Temp/VideoEp" + ID + ".mp4");
//            File fileImage = new File("src/main/java/Temp/ImgEp" + ID + ".jpeg");

//
//
//            File fileSynopsis = null;
//            //Handeling Synopsis Export
//            Path outputFilePathSynopsis = Paths.get("src/main/java/Temp/SynopsisEp" + ID + ".mp4");
//            try (OutputStream outputStreamSynopsis = Files.newOutputStream(outputFilePathSynopsis)) {
//                byte[] buffer = new byte[4096];
//                int bytesRead;
//                while ((bytesRead = episodeSynopsis.read(buffer)) != -1) {
//                    outputStreamSynopsis.write(buffer, 0, bytesRead);
//                }
//            } catch (IOException e) {
//                System.out.println("Error Handelling the Synopsis");
//            }
//            fileSynopsis = new File("src/main/java/Temp/SynopsisEp" + ID + ".mp4");

            episode = new Episode(ID, seasonID, EpisodeName, diffusionDate.toLocalDate(), premiereDate.toLocalDate(), fileImg);

            episodeList.add(episode);
        }
        return episodeList;
    }

    public static List<Episode> FindEpisodeSeasonID(Long seasonID) throws SQLException, IOException {

        List<Episode> episodeList = new ArrayList<>();

        Episode episode = null;

        Resume resume = null;

        String sql = "SELECT * FROM episodes WHERE SEASON_ID = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setLong(1, seasonID);

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Long ID = rs.getLong("ID");
//            int episodeNumber = rs.getInt("NUM");
//            long episodeViews = rs.getInt("VIEW_NBR");
//            long episodeScore = rs.getInt("SCORE");
//            long episodeVotes = rs.getInt("VOTES");
            String EpisodeName = rs.getString("NAME");
            Date diffusionDate = rs.getDate("DEBUT_DATE");
            Date premiereDate = rs.getDate("premiere_Date");
            Blob episodeImageB = rs.getBlob("image");
            InputStream episodeImage = episodeImageB.getBinaryStream();
//            String Description = rs.getString("Description");
//            InputStream episodeSynopsis = rs.getBinaryStream("SYNOPSIS");
//            InputStream episodeVideo = rs.getBinaryStream("video");

            //Converting Blob Image to Jpeg File
            File fileImg = new File("src/main/java/Temp/ImgEp" + ID + ".jpeg");
            OutputStream outS = new FileOutputStream(fileImg);
            byte[] bufferImg = new byte[1024];
            int length;
            while ((length = episodeImage.read(bufferImg)) != -1) {
                outS.write(bufferImg, 0, length);
            }

//            //Handeling the Video, from inputStream
//            Path outputFilePath = Paths.get("src/main/java/Temp/VideoEp" + ID + ".mp4");
//            try (OutputStream outputStream = Files.newOutputStream(outputFilePath)) {
//                byte[] buffer = new byte[4096];
//                int bytesRead;
//                while ((bytesRead = episodeVideo.read(buffer)) != -1) {
//                    outputStream.write(buffer, 0, bytesRead);
//                }
//            } catch (IOException e) {
//                System.out.println("Error Handelling the video");
//            }
//
//            File file = new File("src/main/java/Temp/VideoEp" + ID + ".mp4");
//            File fileImage = new File("src/main/java/Temp/ImgEp" + ID + ".jpeg");
//

//            File fileSynopsis = null;
            //Handeling Synopsis Export
//            Path outputFilePathSynopsis = Paths.get("src/main/java/Temp/SynopsisEp" + ID + ".mp4");
//            try (OutputStream outputStreamSynopsis = Files.newOutputStream(outputFilePathSynopsis)) {
//                byte[] buffer = new byte[4096];
//                int bytesRead;
//                while ((bytesRead = episodeSynopsis.read(buffer)) != -1) {
//                    outputStreamSynopsis.write(buffer, 0, bytesRead);
//                }
//            } catch (IOException e) {
//                System.out.println("Error Handelling the Synopsis");
//            }
//            fileSynopsis = new File("src/main/java/Temp/SynopsisEp" + ID + ".mp4");

            episode = new Episode(ID, seasonID, EpisodeName, diffusionDate.toLocalDate(), premiereDate.toLocalDate(), fileImg);

            episodeList.add(episode);

        }

        return episodeList;
    }

    public static long getVote(Episode ep){

        PreparedStatement pstmt = null;

        long nbrVotes=-1;

        ResultSet rs = null;

        try {
            String sql="SELECT VOTES FROM EPISODES WHERE ID=?";

            pstmt = conn.prepareStatement(sql);

            pstmt.setLong(1, ep.getID());

            rs=pstmt.executeQuery();

            if (rs.next())
                nbrVotes = rs.getLong(1);

        }catch (Exception e){
            System.out.println("Episode "+ep.getID()+" vote retrieval failed");
            return -1;
        }
        return nbrVotes;
    }


    public static long getScore(Episode ep){

        PreparedStatement pstmt = null;

        long score=-1;

        ResultSet rs = null;

        try {
            String sql="SELECT SCORE FROM EPISODES WHERE ID = ?";

            pstmt = conn.prepareStatement(sql);

            pstmt.setLong(1,ep.getID());

            rs=pstmt.executeQuery();
            if (rs.next())
                score = rs.getLong(1);
        }catch (Exception e){
            System.out.println("Episode "+ep.getID()+" score retrieval failed");
            return -1;
        }
        return score;
    }

    public static long getViewNbrEpisode(Episode ep){

        PreparedStatement pstmt = null;

        long nbr_view = -1;

        ResultSet rs = null;

        try {
            String sql="select VIEW_NBR from EPISODES where ID = ?";

            pstmt = conn.prepareStatement(sql);

            pstmt.setLong(1,ep.getID());

            rs=pstmt.executeQuery();

            if (rs.next())
                nbr_view = rs.getLong(1);
        }catch (Exception e){
            System.out.println("Episode "+ep.getID()+" view number retrieval failed");
            return -1;
        }
        return nbr_view;
    }


    public static boolean UpdateViewNbrEpisode(Episode ep) throws SQLException {
        String sql = "update EPISODES set VIEW_NBR = VIEW_NBR + 1 where ID = ?";

        PreparedStatement pstmt = null;

        pstmt = conn.prepareStatement(sql);

        pstmt.setLong(1,ep.getID());

        try {
            int affectedRows = pstmt.executeUpdate();
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    public static boolean UpdateVoteEpisode(Episode ep) throws SQLException {
        String sql = "update EPISODES set VOTES = VOTES + 1 where ID = ?";

        PreparedStatement pstmt = null;

        pstmt = conn.prepareStatement(sql);

        pstmt.setLong(1,ep.getID());

        try {
            int affectedRows = pstmt.executeUpdate();
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean UpdateNegativeScoreEpisode(Episode ep) throws SQLException {

        try {
            UpdateVoteEpisode(ep);
        }catch (Exception e)
        {
            System.out.println("Error updating Negative Score");
            return false;
        }

        return true;
    }

    public static long getScoreEpisode(Episode ep) throws SQLException {

        long score = -1;

        String sql = "SELECT SCORE from EPISODES WHERE ID = ?";

        PreparedStatement pstmt = null;

        pstmt = conn.prepareStatement(sql);

        pstmt.setLong(1,ep.getID());

        ResultSet rs = null;

        try {
            rs = pstmt.executeQuery();
            if (rs.next()) {
                score = rs.getLong("SCORE");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
        return score;
    }

    public static long getVotesEpisode(Episode ep) throws SQLException {

        long votes = -1;

        String sql = "SELECT VOTES from EPISODES WHERE ID = ?";

        PreparedStatement pstmt = null;

        pstmt = conn.prepareStatement(sql);

        pstmt.setLong(1,ep.getID());

        ResultSet rs = null;

        try {
            rs = pstmt.executeQuery();

            if (rs.next()) {
                votes = rs.getLong("VOTES");
            }
        }catch (Exception e)
        {
            return -1;
        }
        return votes;
    }


    public static boolean UpdatePositiveScoreEpisode(Episode ep) throws SQLException {

        long score = getScoreEpisode(ep);
        long votes = getVotesEpisode(ep);


        if (score == -1)
        {
            System.out.println("Error retrieving Score in UpdatePositiveScoreEpisode Function ");
            return false;
        }
        if (votes == -1)
        {
            System.out.println("Error retrieving Votes in UpdatePositiveScoreEpisode Function ");
            return false;
        }

//        long posVotes = (long) Math.ceil(((score/100)*votes));
        long posVotes = (long) Math.ceil(((float)score/100.0)*votes);
        System.out.println("PosVotes Before Increase "+ posVotes);
        votes++;
        posVotes++;
        System.out.println("PosVotes After Increase "+ posVotes);


//        long Newscore = (long) Math.ceil(((float)(posVotes+1)/(votes+1))*100);
        long Newscore = (long) (((float)posVotes*100.0)/votes);

        UpdateVoteEpisode(ep);

//        System.out.println("New score is "+Newscore);

        String sql = "update EPISODES set SCORE = ? where ID = ?";

        PreparedStatement pstmt = null;

        pstmt = conn.prepareStatement(sql);

        pstmt.setLong(1,Newscore);
        pstmt.setLong(2,ep.getID());

        ResultSet rs = null;

        try {
            rs = pstmt.executeQuery();

        }catch (Exception e)
        {
            System.out.println("Error Setting Score in UpdatePositiveScoreEpisode Function ");
            return false;
        }

        return true;

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
            //     pstmt.close();
            //     conn.close();

            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
//            pstmt.close();
//            conn.close();
            return false;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier : " + e.getMessage());
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
//            pstmt.close();
//            conn.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error dans la connection de la base"+e.getMessage());
//            pstmt.close();
//            conn.close();
            return false;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);

        }
    }


    public static boolean modifVideo(Episode episode,File NewVideo) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs;
        String sql;

        try {
            InputStream inputStreamSynopsisEpisode = new FileInputStream(NewVideo);
            sql = "UPDATE EPISODES SET VIDEO = ? WHERE ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setBlob(1, inputStreamSynopsisEpisode);
            pstmt.setLong(2,episode.getID());
            pstmt.executeQuery();
//            pstmt.close();
//            conn.close();

            return true;
        } catch (SQLException e) {
            System.out.println("error dans la connection de la base"+e.getMessage());
//            pstmt.close();
//            conn.close();
            return false;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);

        }
    }


    public static boolean modifNom(Episode episode, String nom) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs;
        String sql;

        try {

            sql = "UPDATE EPISODES SET Name = ? WHERE ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,nom);
            pstmt.setLong(2,episode.getID());
            pstmt.executeQuery();
//            pstmt.close();
//            conn.close();

            return true;
        } catch (SQLException e) {
            System.out.println("error dans la connection de la base"+e.getMessage());
//            pstmt.close();
//            conn.close();
            return false;
        }
    }


    public static boolean modifDebutDate(Episode episode, LocalDate date) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs;
        String sql;

        try {

            sql = "UPDATE EPISODES SET DEBUT_DATE = ? WHERE ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(date));
            pstmt.setLong(2,episode.getID());
            pstmt.executeQuery();

            return true;
        } catch (SQLException e) {
            System.out.println("error dans la connection de la base"+e.getMessage());

//            pstmt.close();
//            conn.close();
            return false;
        }
    }



    public static boolean modifPremiereDate(Episode episode, LocalDate date) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs;
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
//            pstmt.close();
//            conn.close();
            return false;
        }
    }


    public static boolean modifDescription(Episode episode,String description) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs;
        String sql;

        try {
            sql = "UPDATE EPISODES SET description = ? WHERE ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,description);
            pstmt.setLong(2,episode.getID());
            pstmt.executeQuery();
//            pstmt.close();
//            conn.close();

            return true;
        } catch (SQLException e) {
            System.out.println("error dans la connection de la base"+e.getMessage());
//            pstmt.close();
//            conn.close();
            return false;
        }
    }






}
