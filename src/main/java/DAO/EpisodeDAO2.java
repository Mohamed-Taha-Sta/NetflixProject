package DAO;

import Entities.Episode;
import Entities.Resume;
import Entities.Synopsis;
import Entities.Text;
import Utils.ConxDB;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EpisodeDAO2 {

    private static final Connection conn = ConxDB.getInstance();

    public EpisodeDAO2() throws SQLException {
    }

    public static boolean AddEpisode(Episode episode) throws SQLException, FileNotFoundException {
        String sql;
        InputStream inputStreamSynopsis = null;

        if (episode.getResume() instanceof Text) {
            sql = "INSERT INTO episodes (season_ID, NUM, name, DEBUT_DATE, premiere_Date, texte,IMAGE,VIDEO,VOTES,SCORE,VIEW_NBR) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            inputStreamSynopsis = new FileInputStream(((Synopsis) episode.getResume()).getTinyVideo());

            sql = "INSERT INTO episodes (season_ID, NUM, name, DEBUT_DATE, premiere_Date, SYNOPSIS,IMAGE,VIDEO,VOTES,SCORE,VIEW_NBR) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        }

//        InputStream inputStream = new FileInputStream("C:\\Users\\Taha\\IdeaProjects\\NetflixProject1\\src\\main\\java\\Test\\LionTest.jpeg");
        InputStream inputStream = new FileInputStream(episode.getImage());
        InputStream inputStreamVideo = new FileInputStream(episode.getMedia());

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Set the values for the parameters
            pstmt.setLong(1, episode.getSeasonParentID()); // replace 1 with the actual seasonID value
            pstmt.setInt(2, episode.getNumber()); // replace 1 with the actual episode number value
            pstmt.setString(3, episode.getName()); // replace "Episode Title" with the actual episode name value
            pstmt.setDate(4, java.sql.Date.valueOf(episode.getDebutDate())); // replace "2023-03-30" with the actual diffusion date value
            pstmt.setDate(5, java.sql.Date.valueOf(episode.getPremiereDate())); // replace "2023-03-30" with the actual premiere date value
            if (episode.getResume() instanceof Text)
                pstmt.setString(6, ((Text) episode.getResume()).getTexte());// replace "Episode description" with the actual episode text value
            else {
                pstmt.setBlob(6, inputStreamSynopsis); // replace "Episode description" with the actual episode text value
            }
            pstmt.setBlob(7, inputStream); // replace inputStream with the actual image data
            pstmt.setBlob(8, inputStreamVideo); // replace "https://example.com/video.mp4" with the actual video URL value
            pstmt.setLong(9, 0); // replace "https://example.com/video.mp4" with the actual video URL value
            pstmt.setLong(10, 0); // replace "https://example.com/video.mp4" with the actual video URL value
            pstmt.setLong(11, 0); // replace "https://example.com/video.mp4" with the actual video URL value
            int affectedRows = pstmt.executeUpdate();

        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
            return false;
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
            return false;
        }  // end finally try

        return true;

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
            String episodeText = rs.getString("texte");
            InputStream episodeSynopsis = rs.getBinaryStream("SYNOPSIS");
            InputStream episodeVideo = rs.getBinaryStream("video");

            //Converting Blob into An Image
//            byte[] imageData = episodeImage.getBytes(1, (int) episodeImage.length());
//            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageData));
//            Image image = SwingFXUtils.toFXImage(bufferedImage, null);


            //Converting Blob into An JPEG File
            File fileImg = new File("src/main/java/Test/ImgEp" + ID + ".jpeg");
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
            Path outputFilePath = Paths.get("src/main/java/Test/VideoEp" + ID + ".mp4");
            try (OutputStream outputStream = Files.newOutputStream(outputFilePath)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = episodeVideo.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                System.out.println("Error Handelling the video");
            }
            File file = new File("src/main/java/Test/VideoEp" + ID + ".mp4");
            File fileImage = new File("src/main/java/Test/ImgEp" + ID + ".jpeg");

            System.out.println(episodeSynopsis);

            if (episodeSynopsis == null) {
                resume = new Text(EpisodeName + " Resume", episodeText);

                episode = new Episode(ID, seasonID, EpisodeName, episodeNumber, diffusionDate.toLocalDate(), premiereDate.toLocalDate(), fileImage, resume, file, episodeViews, episodeScore, episodeVotes);
            } else {
                Path outputFilePathSynopsis = Paths.get("src/main/java/Test/SynopsisEp" + ID + ".mp4");
                try (OutputStream outputStreamSynopsis = Files.newOutputStream(outputFilePathSynopsis)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = episodeSynopsis.read(buffer)) != -1) {
                        outputStreamSynopsis.write(buffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    System.out.println("Error Handelling the Synopsis");
                }
                File fileSynopsis = new File("src/main/java/Test/SynopsisEp" + ID + ".mp4");
                resume = new Synopsis(EpisodeName + " Synopsis", fileSynopsis);
            }
            episode = new Episode(ID, seasonID, EpisodeName, episodeNumber, diffusionDate.toLocalDate(), premiereDate.toLocalDate(), fileImage, resume, file, episodeViews, episodeScore, episodeVotes);

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
            Long seasonID = rs.getLong("season_ID");
            int episodeNumber = rs.getInt("NUM");
            long episodeViews = rs.getLong("VIEW_NBR");
            long episodeScore = rs.getLong("SCORE");
            long episodeVotes = rs.getLong("VOTES");
            long ID = rs.getLong("ID");
            Date diffusionDate = rs.getDate("DEBUT_DATE");
            Date premiereDate = rs.getDate("premiere_Date");
            Blob episodeImageB = rs.getBlob("image");
            InputStream episodeImage = episodeImageB.getBinaryStream();
            String episodeText = rs.getString("texte");
            InputStream episodeSynopsis = rs.getBinaryStream("SYNOPSIS");
            InputStream episodeVideo = rs.getBinaryStream("video");

            //Converting Blob into An Image
//                    byte[] imageData = episodeImage.getBytes(1, (int) episodeImage.length());
//                    BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageData));
//                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);

            //Converting Blob Image to Jpeg File
            File fileImg = new File("src/main/java/Test/ImgEp" + ID + ".jpeg");
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
            Path outputFilePath = Paths.get("src/main/java/Test/VideoEp" + ID + ".mp4");
            try (OutputStream outputStream = Files.newOutputStream(outputFilePath)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = episodeVideo.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                System.out.println("Error Handelling the video");
            }
            File file = new File("src/main/java/Test/VideoEp" + ID + ".mp4");
            File fileImage = new File("src/main/java/Test/ImgEp" + ID + ".jpeg");


            if (episodeSynopsis == null) {
                resume = new Text(EpisodeName + " Resume", episodeText);

                episode = new Episode(ID, seasonID, EpisodeName, episodeNumber, diffusionDate.toLocalDate(), premiereDate.toLocalDate(), fileImage, resume, file, episodeViews, episodeScore, episodeVotes);
            } else {
                Path outputFilePathSynopsis = Paths.get("src/main/java/Test/SynopsisEp" + ID + ".mp4");
                try (OutputStream outputStreamSynopsis = Files.newOutputStream(outputFilePathSynopsis)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = episodeSynopsis.read(buffer)) != -1) {
                        outputStreamSynopsis.write(buffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    System.out.println("Error Handelling the Synopsis");
                }
                File fileSynopsis = new File("src/main/java/Test/SynopsisEp" + ID + ".mp4");
                resume = new Synopsis(EpisodeName + " Synopsis", fileSynopsis);
            }
            episode = new Episode(ID, seasonID, EpisodeName, episodeNumber, diffusionDate.toLocalDate(), premiereDate.toLocalDate(), fileImage, resume, file, episodeViews, episodeScore, episodeVotes);

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
            int episodeNumber = rs.getInt("NUM");
            long episodeViews = rs.getInt("VIEW_NBR");
            long episodeScore = rs.getInt("SCORE");
            long episodeVotes = rs.getInt("VOTES");
            String EpisodeName = rs.getString("NAME");
            Date diffusionDate = rs.getDate("DEBUT_DATE");
            Date premiereDate = rs.getDate("premiere_Date");
            Blob episodeImageB = rs.getBlob("image");
            InputStream episodeImage = episodeImageB.getBinaryStream();
            String episodeText = rs.getString("texte");
            InputStream episodeSynopsis = rs.getBinaryStream("SYNOPSIS");
            InputStream episodeVideo = rs.getBinaryStream("video");

            //Converting Blob Image to Jpeg File
            File fileImg = new File("src/main/java/Test/ImgEp" + ID + ".jpeg");
            OutputStream outS = new FileOutputStream(fileImg);
            byte[] bufferImg = new byte[1024];
            int length;
            while ((length = episodeImage.read(bufferImg)) != -1) {
                outS.write(bufferImg, 0, length);
            }
            //Handeling the Video, from inputStream
            Path outputFilePath = Paths.get("src/main/java/Test/VideoEp" + ID + ".mp4");
            try (OutputStream outputStream = Files.newOutputStream(outputFilePath)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = episodeVideo.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                System.out.println("Error Handelling the video");
            }

            File file = new File("src/main/java/Test/VideoEp" + ID + ".mp4");
            File fileImage = new File("src/main/java/Test/ImgEp" + ID + ".jpeg");


            if (episodeSynopsis == null) {
                resume = new Text(EpisodeName + " Resume", episodeText);

                episode = new Episode(ID, seasonID, EpisodeName, episodeNumber, diffusionDate.toLocalDate(), premiereDate.toLocalDate(), fileImage, resume, file, episodeViews, episodeScore, episodeVotes);
            } else {
                Path outputFilePathSynopsis = Paths.get("src/main/java/Test/SynopsisEp" + ID + ".mp4");
                try (OutputStream outputStreamSynopsis = Files.newOutputStream(outputFilePathSynopsis)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = episodeSynopsis.read(buffer)) != -1) {
                        outputStreamSynopsis.write(buffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    System.out.println("Error Handelling the Synopsis");
                }
                File fileSynopsis = new File("src/main/java/Test/SynopsisEp" + ID + ".mp4");
                resume = new Synopsis(EpisodeName + " Synopsis", fileSynopsis);
            }
            episode = new Episode(ID, seasonID, EpisodeName, episodeNumber, diffusionDate.toLocalDate(), premiereDate.toLocalDate(), fileImage, resume, file, episodeViews, episodeScore, episodeVotes);

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








}
