package DAO;

import Entities.*;
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

    public static long AddSerie(Serie Serie) throws SQLException, FileNotFoundException {

        long SerieID=-1;

        String sql = "INSERT INTO SERIE (Name, DESCRIPTION, DEBUT_DATE, LANGUAGE, COUNTRY, Image, NUM_SEASONS, SYNOPSIS, ListeGenre,ID_PROD) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        InputStream inputStreamThumbnail = new FileInputStream(Serie.getImg());
        InputStream inputStreamSynopsis = new FileInputStream(Serie.getSynopsis());
        try {
            pstmt.setString(1,Serie.getNom());
            pstmt.setString(2,Serie.getDescription());
            pstmt.setDate(3,java.sql.Date.valueOf(Serie.getAnnerdesortie()));
            pstmt.setString(4,Serie.getLangue());
            pstmt.setString(5,Serie.getPaysorigine());
            pstmt.setBlob(6,inputStreamThumbnail);
            pstmt.setLong(7,Serie.getSeasonNumber());
            pstmt.setBlob(8,inputStreamSynopsis);
            pstmt.setString(9,String.join(",", Serie.getListegenre().stream().map(Object::toString).toArray(String[]::new)));
            pstmt.setLong(10,Serie.getID_PROD());
            int affectedRows = pstmt.executeUpdate();
//            ResultSet generatedKeys = pstmt.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                id = generatedKeys.getLong(1);
//            }

            SerieID = getSerieID(Serie);


        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
            return -1;
        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
            return -1;
        }
        InsertMainActSerie(SerieID,Serie.getIDMainactorList());
        InsertSuppActSerie(SerieID,Serie.getIDSuppactorList());
        return SerieID;
    }

    public static boolean InsertMainActSerie(long idSerie,List<Long> listMainAct) throws SQLException {

        String sql = "INSERT INTO SERIEACTORPRINC (ID_SERIE,ID_ACT) VALUES (?,?)";

        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        try {
        for (Long lo : listMainAct)
            {
                pstmt.setLong(1,idSerie);
                pstmt.setLong(2,lo);
                int affectedRows = pstmt.executeUpdate();

            }
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
    public static boolean InsertSuppActSerie(long idSerie,List<Long> listSuppAct) throws SQLException {

        String sql = "INSERT INTO SERIEACTORSUPP (ID_SERIE,ID_ACT) VALUES (?,?)";

        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        try {
        for (Long lo : listSuppAct)
            {
                pstmt.setLong(1,idSerie);
                pstmt.setLong(2,lo);
                int affectedRows = pstmt.executeUpdate();

            }
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


    public static long getSerieID(Serie Serie) throws SQLException {
        String sql = "Select * from SERIE where NAME = ? and ID_PROD = ? and LANGUAGE = ?";
        long SerieID=-1;
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pstmt.setString(1,Serie.getNom());
        pstmt.setLong(2,Serie.getID_PROD());
        pstmt.setString(3,Serie.getLangue());

        ResultSet rs = pstmt.executeQuery();

        if (rs.next())
            SerieID = rs.getLong("ID_SERIE");
        else
            System.out.println("Error getting SerieID");

        return SerieID;
    }


    public static List<Serie> GetSerieByName(String SerieName) throws SQLException, IOException {

        Serie serie = null;

        List<Serie> serieList = new ArrayList<>();

        String sql = "SELECT * FROM SERIE WHERE NAME like ?";

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
            int numsSeasons= rs.getInt("NUM_SEASONS");
            String StringGenre = rs.getString("LISTEGENRE");
            String[] genreArray = StringGenre.split(",");
            ArrayList<String> genreList = new ArrayList<>(Arrays.asList(genreArray));
            InputStream SerieThumbnail = Thumbnail.getBinaryStream();
            InputStream SerieSynopsis = rs.getBinaryStream("SYNOPSIS");

            //Converting Blob Image to Jpeg File
            File fileThumb = new File("src/main/java/Temp/ImgSerie"+ID+".jpeg");
            OutputStream outS = new FileOutputStream(fileThumb);
            byte[] bufferImg = new byte[1024];
            int length;
            while ((length = SerieThumbnail.read(bufferImg)) != -1) {
                outS.write(bufferImg, 0, length);
            }

            //Converting Blob Synopsis to video File .mp4
            Path outputFilePathSynopsis = Paths.get("src/main/java/Temp/SynopsisSerie"+ID+".mp4");
            try (OutputStream outputStreamSynopsis = Files.newOutputStream(outputFilePathSynopsis)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = SerieSynopsis.read(buffer)) != -1) {
                    outputStreamSynopsis.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                System.out.println("Error Handelling the Synopsis");
            }
            File fileSynopsis = new File("src/main/java/Temp/SynopsisSerie"+ID+".mp4");
            File fileThumbnail = new File("src/main/java/Temp/ImgSerie"+ID+".jpeg");

            List<Season> seasonList = SeasonDAO.FindSeasonSerieID(ID);

            List<Actor> ActorList = getPrincActorSerie(getPrincActorIDSerie(ID));
            List<Actor> SuppActorList = getSuppActorSerie(getSuppActorIDSerie(ID));

            ActorList.addAll(SuppActorList);

            serie = new Serie(ID,SerieName,Director,DebutDate.toLocalDate(),Language,Country,genreList,fileThumbnail,numsSeasons,fileSynopsis,seasonList,ActorList);

            serieList.add(serie);
        }

        return serieList;
    }

    public static List<Long> getPrincActorIDSerie(long IDSerie) throws SQLException {

        List<Long> listIDActor = new ArrayList<>();

        String sqlGetID = "SELECT ID_ACT FROM SERIEACTORPRINC WHERE ID_SERIE = ?";

        PreparedStatement pstmtGetID = conn.prepareStatement(sqlGetID);

        pstmtGetID.setLong(1, IDSerie);

        ResultSet rs = pstmtGetID.executeQuery();

        while (rs.next()) {
            long IDActeurPrinc = rs.getLong("ID_ACT");
            listIDActor.add(IDActeurPrinc);
        }
        System.out.println("Got Main Actors = "+listIDActor);
        return listIDActor;
    }

    public static List<Long> getSuppActorIDSerie(long IDSerie) throws SQLException {

        List<Long> listIDActor = new ArrayList<>();

        String sqlGetID = "SELECT ID_ACT FROM SERIEACTORSUPP WHERE ID_SERIE = ?";

        PreparedStatement pstmtGetID = conn.prepareStatement(sqlGetID);

        pstmtGetID.setLong(1, IDSerie);

        ResultSet rs = pstmtGetID.executeQuery();

        while (rs.next()) {
            long IDActeurSupp = rs.getLong("ID_ACT");
            listIDActor.add(IDActeurSupp);
        }
        System.out.println("Got Support Actors = "+listIDActor);
        return listIDActor;
    }

    public static List<Actor> getPrincActorSerie(List<Long> IDactors) throws SQLException, IOException
    {
        List<Actor> actorList = new ArrayList<>();

        String sql = "SELECT * FROM ACTOR WHERE ID_ACT = ?";

        PreparedStatement pstmtGetID = conn.prepareStatement(sql);

        for(Long lo : IDactors)
        {
            pstmtGetID.setLong(1, lo);
            ResultSet rs = pstmtGetID.executeQuery();

            if (rs.next())
            {
                Long IDactor = rs.getLong(1);
                String Nom = rs.getString(2);
                String Prenom = rs.getString(3);
                String Email = rs.getString(4);
                String Password = rs.getString(5);

                Actor actor = new MainActor(IDactor,Nom,Prenom,Email,Password);

                actorList.add(actor);
            }

        }

        return actorList;

    }

    public static List<Actor> getSuppActorSerie(List<Long> IDactors) throws SQLException
    {
        List<Actor> actorList = new ArrayList<>();

        String sql = "SELECT * FROM ACTOR WHERE ID_ACT = ?";

        PreparedStatement pstmtGetID = conn.prepareStatement(sql);

        for(Long lo : IDactors)
        {
            pstmtGetID.setLong(1, lo);
            ResultSet rs = pstmtGetID.executeQuery();

            if (rs.next())
            {
                Long IDactor = rs.getLong(1);
                String Nom = rs.getString(2);
                String Prenom = rs.getString(3);
                String Email = rs.getString(4);
                String Password = rs.getString(5);

                Actor actor = new Supportingactor(IDactor,Nom,Prenom,Email,Password);

                actorList.add(actor);
            }

        }

        return actorList;

    }




}