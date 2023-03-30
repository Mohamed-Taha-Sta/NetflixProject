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
    public static boolean ajout_episode(Episode episode) throws SQLException, FileNotFoundException {

        String sql;
        InputStream inputStreamSynopsis = null;

        if (episode.getResume() instanceof Text) {
            sql = "INSERT INTO episodes (season_ID, NUM, name, DEBUT_DATE, premiere_Date, texte,IMAGE,VIDEO) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        }
        else
        {
            inputStreamSynopsis = new FileInputStream(((Synopsis) episode.getResume()).getTinyVideo());

            sql = "INSERT INTO episodes (season_ID, NUM, name, DEBUT_DATE, premiere_Date, SYNOPSIS,IMAGE,VIDEO) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        }

        InputStream inputStream = new FileInputStream("C:\\Users\\Taha\\IdeaProjects\\NetflixProject1\\src\\main\\java\\Test\\LionTest.jpeg");
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
            pstmt.setString(6, ((Text)episode.getResume()).getTexte());// replace "Episode description" with the actual episode text value
        else {
            pstmt.setBlob(6, inputStreamSynopsis); // replace "Episode description" with the actual episode text value
        }
        pstmt.setBlob(7, inputStream); // replace inputStream with the actual image data
        pstmt.setBlob(8, inputStreamVideo); // replace "https://example.com/video.mp4" with the actual video URL value
            int affectedRows = pstmt.executeUpdate();

        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
            return false;
        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
            return false;
        }  // end finally try

        return true;

    }

    public List<Episode> FindEpisodeID(Long ID) throws SQLException, IOException {

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
            Blob episodeImage = rs.getBlob("image");
            String episodeText = rs.getString("texte");
            InputStream episodeSynopsis = rs.getBinaryStream("SYNOPSIS");
            InputStream episodeVideo = rs.getBinaryStream("video");

            //Converting Blob into An Image
            byte[] imageData = episodeImage.getBytes(1, (int) episodeImage.length());
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageData));
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);

            //DO NOT DELETE THIS CODE
            //            File outputFile = new File("C:\\Users\\Taha\\IdeaProjects\\NetflixProject1\\src\\main\\java\\Test\\test.jpeg");
            //
            //            try {
            //                ImageIO.write(bufferedImage, "jpeg", outputFile);
            //            } catch (IOException e) {
            //                // Handle the exception
            //            }

            //Handeling the Video, from inputStream
            Path outputFilePath = Paths.get("C:\\Users\\Taha\\IdeaProjects\\NetflixProject1\\src\\main\\java\\Test\\"+EpisodeName+".mp4");
            try (OutputStream outputStream = Files.newOutputStream(outputFilePath)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = episodeVideo.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                System.out.println("Error Handelling the video");
            }
            File file = new File("C:\\Users\\Taha\\IdeaProjects\\NetflixProject1\\src\\main\\java\\Test\\" + EpisodeName + ".mp4");

            if (episodeSynopsis == null) {
                resume = new Text(EpisodeName+" Resume", episodeText);

                episode = new Episode(ID, seasonID, EpisodeName, episodeNumber, diffusionDate.toLocalDate(), premiereDate.toLocalDate(), image, resume, file, episodeViews, episodeScore, episodeVotes);
            }
            else
            {
                Path outputFilePathSynopsis = Paths.get("C:\\Users\\Taha\\IdeaProjects\\NetflixProject1\\src\\main\\java\\Test\\"+EpisodeName+"Synopsis.mp4");
                try (OutputStream outputStreamSynopsis = Files.newOutputStream(outputFilePathSynopsis)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = episodeVideo.read(buffer)) != -1) {
                        outputStreamSynopsis.write(buffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    System.out.println("Error Handelling the Synopsis");
                }
                File fileSynopsis = new File("C:\\Users\\Taha\\IdeaProjects\\NetflixProject1\\src\\main\\java\\Test\\" + EpisodeName + "Synopsis.mp4");
                resume = new Synopsis(EpisodeName+" Synopsis",fileSynopsis);
            }
            episode = new Episode(ID, seasonID, EpisodeName, episodeNumber, diffusionDate.toLocalDate(), premiereDate.toLocalDate(), image, resume, file, episodeViews, episodeScore, episodeVotes);

            episodeList.add(episode);

        }

        return episodeList;
    }

        public List<Episode> FindEpisodeName(String EpisodeName) throws SQLException, IOException {

                List<Episode> episodeList = new ArrayList<>();

                Episode episode=null;

                Resume resume=null;

                String sql = "SELECT * FROM episodes WHERE NAME like ?";

                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, "%"+EpisodeName+"%");

                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    // Retrieve the values from the ResultSet and store them in variables
                    Long seasonID = rs.getLong("season_ID");
                    int episodeNumber = rs.getInt("NUM");
                    long episodeViews = rs.getInt("VIEW_NBR");
                    long episodeScore = rs.getInt("SCORE");
                    long episodeVotes = rs.getInt("VOTES");
                    long ID = rs.getLong("ID");
                    Date diffusionDate = rs.getDate("DEBUT_DATE");
                    Date premiereDate = rs.getDate("premiere_Date");
                    Blob episodeImage = rs.getBlob("image");
                    String episodeText = rs.getString("texte");
                    InputStream episodeSynopsis = rs.getBinaryStream("SYNOPSIS");
                    InputStream episodeVideo = rs.getBinaryStream("video");

                    //Converting Blob into An Image
                    byte[] imageData = episodeImage.getBytes(1, (int) episodeImage.length());
                    BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageData));
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);

                    //DO NOT DELETE THIS CODE
        //            File outputFile = new File("C:\\Users\\Taha\\IdeaProjects\\NetflixProject1\\src\\main\\java\\Test\\test.jpeg");
        //
        //            try {
        //                ImageIO.write(bufferedImage, "jpeg", outputFile);
        //            } catch (IOException e) {
        //                // Handle the exception
        //            }

                    //Handeling the Video, from inputStream
                    Path outputFilePath = Paths.get("C:\\Users\\Taha\\IdeaProjects\\NetflixProject1\\src\\main\\java\\Test\\"+EpisodeName+".mp4");
                    try (OutputStream outputStream = Files.newOutputStream(outputFilePath)) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = episodeVideo.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    } catch (IOException e) {
                        System.out.println("Error Handelling the video");
                    }
                    File file = new File("C:\\Users\\Taha\\IdeaProjects\\NetflixProject1\\src\\main\\java\\Test\\" + EpisodeName + ".mp4");

                    if (episodeSynopsis == null) {
                        resume = new Text(EpisodeName+" Resume", episodeText);

                        episode = new Episode(ID, seasonID, EpisodeName, episodeNumber, diffusionDate.toLocalDate(), premiereDate.toLocalDate(), image, resume, file, episodeViews, episodeScore, episodeVotes);
                    }
                    else
                    {
                        Path outputFilePathSynopsis = Paths.get("C:\\Users\\Taha\\IdeaProjects\\NetflixProject1\\src\\main\\java\\Test\\"+EpisodeName+"Synopsis.mp4");
                        try (OutputStream outputStreamSynopsis = Files.newOutputStream(outputFilePathSynopsis)) {
                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            while ((bytesRead = episodeVideo.read(buffer)) != -1) {
                                outputStreamSynopsis.write(buffer, 0, bytesRead);
                            }
                        } catch (IOException e) {
                            System.out.println("Error Handelling the Synopsis");
                        }
                        File fileSynopsis = new File("C:\\Users\\Taha\\IdeaProjects\\NetflixProject1\\src\\main\\java\\Test\\" + EpisodeName + "Synopsis.mp4");
                        resume = new Synopsis(EpisodeName+" Synopsis",fileSynopsis);
                    }
                    episode = new Episode(ID, seasonID, EpisodeName, episodeNumber, diffusionDate.toLocalDate(), premiereDate.toLocalDate(), image, resume, file, episodeViews, episodeScore, episodeVotes);

                    episodeList.add(episode);

                }

                return episodeList;
            }


}
