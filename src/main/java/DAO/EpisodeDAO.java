package DAO;

import Entities.Episode;
import Entities.Synopsis;
import Entities.Text;
import Utils.ConxDB;
import javafx.scene.image.Image;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EpisodeDAO {

    private static final Connection conn = ConxDB.getInstance();

    public static boolean ajout_episode(Episode episode) {
        boolean etat = true;
        PreparedStatement pstmt ;
        String sql ;
        String name = episode.getName();
        long SID = episode.getSeasonParentID();
        int num = episode.getNumber();
        java.sql.Date DebutDate = java.sql.Date.valueOf(episode.getDebutDate());
        java.sql.Date PremiereDate = java.sql.Date.valueOf(episode.getPremiereDate());
        File video = episode.getMedia();



        try {
            if(episode.getResume() instanceof Text)
//                sql = "INSERT INTO EPISODES (Name,SEASON_ID,NUM,DEBUT_DATE,PREMIERE_DATE,TEXTE,VIEW_NBR,SCORE,VOTES) VALUES ("+"'"+episode.getName()+"'"+","+episode.getSeasonParentID()+","+episode.getNumber()+","
//                        +Date.from((episode.getDebutDate().atStartOfDay(ZoneId.systemDefault()).toInstant()))+","+Date.from(episode.getPremiereDate().atStartOfDay(ZoneId.systemDefault()).toInstant())+","+"'"+episode.getResume() +"'"+","+episode.getVueNbr()+","+episode.getScore()+","+episode.getVotes()+")";

                //"+"'"+episode.getName()+"'"+","+episode.getSeasonParentID()+","+episode.getNumber()+","
                //                            +episode.getDebutDate()+","+episode.getPremiereDate()+","+"'"+episode.getResume() +"'"+","+episode.getVueNbr()+","+episode.getScore()+","+episode.getVotes()+"

//                System.out.println("HEHAEBAEB"+Date.from(episode.getDebutDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));

//                INSERT INTO EPISODES (Name,SEASON_ID,NUM,DEBUT_DATE,PREMIERE_DATE,TEXTE,VIEW_NBR,SCORE,VOTES)
//            VALUES ('Salah',7,4,TO_DATE('1989-12-09','YYYY-MM-DD'),TO_DATE('1989-12-09','YYYY-MM-DD'),'blabla',7,19,12)

//            {
//                    sql = "INSERT INTO EPISODES (Name,SEASON_ID,NUM,DEBUT_DATE,PREMIERE_DATE,TEXTE,VIEW_NBR,SCORE,VOTES) VALUES (?,?,?,?,?,?,?,?,?)";
//
//                    pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//
//                    if(episode.getName()!=null)
//                        pstmt.setString(1, episode.getName());
//                    else
//                        return false;
//                    pstmt.setLong(2, episode.getSeasonParentID());
//                    pstmt.setInt(3, episode.getNumber());
//                    pstmt.setDate(4, java.sql.Date.valueOf(episode.getDebutDate()));
//                    pstmt.setDate(5, java.sql.Date.valueOf(episode.getPremiereDate()));
//                    pstmt.setString(6, (episode.getResume()).toString());
//                    pstmt.setLong(7, episode.getVueNbr());
//                    pstmt.setLong(8, episode.getScore());
//                    pstmt.setLong(9, episode.getVotes());
//                }

            {


//                  sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES ("+num+","+str+")";
//                  sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES ("+num+" , "+str)";


                System.out.println("HEELLLOOO");
                String texte = ((Text) episode.getResume()).getTexte();
//                InputStream inputStream0 = new FileInputStream(video);
//                byte[] data = new byte[(int) video.length()];
//                int result = inputStream0.read(data);

                File videoFile = new File("C:\\Users\\Taha\\IdeaProjects\\NetflixProject1\\src\\main\\java\\Test\\VideoTest.mp4");
                FileInputStream inputStream = new FileInputStream(videoFile);
                byte[] videoData = new byte[(int) videoFile.length()];
                inputStream.read(videoData);


                sql = "INSERT INTO EPISODES (Name,SEASON_ID,NUM,DEBUT_DATE,PREMIERE_DATE,TEXTE,VIDEO) " +
                            "VALUES ('"+name+"',"+SID+","+num+","+"TO_DATE('"+DebutDate+"','YYYY-MM-DD')"+","+
                            "TO_DATE('"+PremiereDate+"','YYYY-MM-DD')"+",'"+texte+"',"+videoFile+")";







                pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);


            }

            else
            {
//                    sql = "INSERT INTO EPISODES (Name,SEASON_ID,NUM,DEBUT_DATE,PREMIERE_DATE,SYNOPSIS,VIEW_NBR,SCORE,VOTES) VALUES ("+"'"+episode.getName()+"'"+","+episode.getSeasonParentID()+","+episode.getNumber()+","
//                        +episode.getDebutDate()+","+episode.getPremiereDate()+","+"'"+episode.getResume() +"'"+","+episode.getVueNbr()+","+episode.getScore()+","+episode.getVotes()+")";

//                sql = "INSERT INTO EPISODES (Name,SEASON_ID,NUM,DEBUT_DATE,PREMIERE_DATE,SYNOPSIS,VIEW_NBR,SCORE,VOTES) VALUES (?,?,?,?,?,?,?,?,?)";

                File synopsis = ((Synopsis) episode.getResume()).getTinyVideo();
                InputStream inputStream = new FileInputStream(synopsis);
                byte[] dataSynop = new byte[(int) synopsis.length()];
                inputStream.read(dataSynop);

                InputStream inputStream0 = new FileInputStream(video);
                byte[] data = new byte[(int) video.length()];
                inputStream0.read(data);


                sql = "INSERT INTO EPISODES (Name,SEASON_ID,NUM,DEBUT_DATE,PREMIERE_DATE,SYNOPSIS,VIDEO) " +
                        "VALUES ("+"'"+name+"'"+","+SID+","+num+","+"TO_DATE("+"'"+DebutDate+"'"+","+"'YYYY-MM-DD')"+","+
                        "TO_DATE("+"'"+PremiereDate+"'"+","+"'YYYY-MM-DD')"
                        +","+"'"+dataSynop+"'"+","+"'"+data+"'"+")";



            }

            pstmt = conn.prepareStatement(sql);

            pstmt.executeUpdate();

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
            etat=false;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return etat;
    }

    public static List<Episode> FindAllEpisodes()
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Episode> episodeList =new ArrayList<>();
        try {
//            String sql="select ID,Name,SEASON_ID,NUM,DEBUT_DATE,PREMIERE_DATE,IMAGE,TEXTE,SYNOPSIS,VIEW_NBR,SCORE,VOTES,VIDEO from Episodes where Episodes.Name like '%Hal9a1%'";

            String sql = "SELECT ID, NAME, SEASON_ID, NUM, DEBUT_DATE, PREMIERE_DATE, IMAGE, TEXTE, SYNOPSIS, VIEW_NBR, SCORE, VOTES, VIDEO FROM EPISODES";
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

//            rs = pstmt.getGeneratedKeys();
//            System.out.println(rs.next());

            while (rs.next())
            {
                long id = rs.getLong(1);
                String Name = rs.getString(2);
                long Seasonid = rs.getLong(3);
                int num = rs.getInt(4);
                Date DebutDate = rs.getDate(5);
                Date PremiereDate = rs.getDate(6);
                Image image = (Image) rs.getBlob(7);
                String texte = rs.getString(8);
                Blob synopsis = rs.getBlob(9);
                long viewNum = rs.getInt(10);
                long score = rs.getLong(11);
                long votes = rs.getLong(12);
                Blob blob = rs.getBlob(13);

                File outputFile = new File("C:\\Users\\Taha\\IdeaProjects\\NetflixProject1\\src\\main\\java\\Test\\output.mp4");
                FileOutputStream outputStream = new FileOutputStream(outputFile);
                InputStream inputStream = blob.getBinaryStream();
                byte[] buffer = new byte[1024];
                while (inputStream.read(buffer) > 0) {
                    outputStream.write(buffer);
                }
                outputStream.close();
                inputStream.close();

//                InputStream videoBlob = rs.getBinaryStream(13);
//                byte[] bytes = videoBlob.readAllBytes();
//                String videoFilePath = "C:\\Users\\Taha\\IdeaProjects\\NetflixProject1\\src\\main\\java\\Test\\video.mp4"; // Replace with the desired file path and extension
//                FileOutputStream fos = new FileOutputStream(videoFilePath);
//                fos.write(bytes);
//                fos.close();


                Episode episode = new Episode(id,Seasonid,Name,num,DebutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        PremiereDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),image,viewNum,score,votes);

                System.out.println(episode);

                episodeList.add(episode);
            }

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return episodeList;

    }

    public static List<Episode> recherche_Episode(String EpisodeName) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String str = "Hal9a1";
        List<Episode> episodeList =new ArrayList<>();
        try {
//            String sql="select ID,Name,SEASON_ID,NUM,DEBUT_DATE,PREMIERE_DATE,IMAGE,TEXTE,SYNOPSIS,VIEW_NBR,SCORE,VOTES,VIDEO from Episodes where Episodes.Name like '%Hal9a1%'";

            String sql = "SELECT ID, Name, SEASON_ID, NUM, DEBUT_DATE, PREMIERE_DATE, IMAGE, TEXTE, SYNOPSIS, VIEW_NBR, SCORE, VOTES, VIDEO FROM EPISODES WHERE EPISODES.NAME like ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%"+EpisodeName+"%");

            rs = pstmt.executeQuery();

//            rs = pstmt.getGeneratedKeys();


            while (rs.next())
            {
                long id = rs.getLong(1);
                String Name = rs.getString(2);
                long Seasonid = rs.getLong(3);
                int num = rs.getInt(4);
                java.sql.Date DebutDate = rs.getDate(5);
                java.sql.Date PremiereDate = rs.getDate(6);
                Image image = (Image) rs.getBlob(7);
                String texte = rs.getString(8);
                Blob synopsis = rs.getBlob(9);
                long viewNum = rs.getInt(10);
                long score = rs.getLong(11);
                long votes = rs.getLong(12);
                InputStream videoBlob = rs.getBinaryStream(13);
                byte[] bytes = videoBlob.readAllBytes();
                String videoFilePath = "C:\\Users\\Taha\\IdeaProjects\\NetflixProject1\\src\\main\\java\\Test\\video.mp4"; // Replace with the desired file path and extension
                FileOutputStream fos = new FileOutputStream(videoFilePath);
                fos.write(bytes);
                fos.close();


                Episode episode = new Episode(id,Seasonid,Name,num,DebutDate.toLocalDate(),
                        PremiereDate.toLocalDate(),image,viewNum,score,votes);

                System.out.println(episode);

                episodeList.add(episode);
            }

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return episodeList;
    }


}











