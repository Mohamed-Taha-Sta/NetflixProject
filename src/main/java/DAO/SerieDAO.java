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
        long id=-1;


        String sql = "INSERT INTO SERIE (Name, DESCRIPTION, DEBUT_DATE, LANGUAGE, COUNTRY, Image, NUM_SEASONS, SYNOPSIS, ListeGenre,ID_PROD) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"ID_SERIE"});

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
        InsertMainActSerie(id,Serie.getIDMainactorList());
        InsertSuppActSerie(id,Serie.getIDSuppactorList());
        return id;
    }

    public static boolean InsertMainActSerie(long idSerie,List<Long> listMainAct) throws SQLException {

        String sql = "INSERT INTO SERIEACTORPRINC (ID_SERIE,ID_ACT) VALUES (?,?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
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

        PreparedStatement pstmt = conn.prepareStatement(sql);
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
            long ID_PROD = rs.getLong("ID_PROD");
            String DESCRIPTION = rs.getString("DESCRIPTION");
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

            serie = new Serie(ID,ID_PROD,SerieName,DESCRIPTION,DebutDate.toLocalDate(),Language,Country,genreList,fileThumbnail,numsSeasons,fileSynopsis,seasonList,ActorList);

            serieList.add(serie);
        }

        return serieList;
    }



    public static List<Serie> GetSerieByID(long ID) throws SQLException, IOException {

        Serie serie = null;

        List<Serie> serieList = new ArrayList<>();

        String sql = "SELECT * FROM SERIE WHERE ID_SERIE = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setLong(1, ID);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {

            String SerieName = rs.getString("Name");
            long ID_PROD = rs.getLong("ID_PROD");
            String DESCRIPTION = rs.getString("DESCRIPTION");
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

            serie = new Serie(ID,ID_PROD,SerieName,DESCRIPTION,DebutDate.toLocalDate(),Language,Country,genreList,fileThumbnail,numsSeasons,fileSynopsis,seasonList,ActorList);

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



    public static List<Serie> GetManySeries(long limit) throws SQLException, IOException {

        Serie serie = null;

        List<Serie> serieList = new ArrayList<>();

        String sql = "SELECT * " +
                "FROM (SELECT * FROM serie ORDER BY dbms_random.value)" +
                "WHERE rownum <= ?;";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setLong(1, limit);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {

            long ID = rs.getLong("ID_SERIE");
            String SerieName = rs.getString("NAME");
            long ID_PROD = rs.getLong("ID_PROD");
            String DESCRIPTION = rs.getString("DESCRIPTION");
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

            serie = new Serie(ID,ID_PROD,SerieName,DESCRIPTION,DebutDate.toLocalDate(),Language,Country,genreList,fileThumbnail,numsSeasons,fileSynopsis,seasonList,ActorList);

            serieList.add(serie);
        }

        return serieList;
    }






    ///////////////////////////////////////////////////// Fonction Fares
//
//    public static List<Serie> FindByproducer(Producer prod) {
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        List<Serie> list = new ArrayList<>();
//        List<Serie> list1 = new ArrayList<>();
//        long idprod = ProducerDAO.getprodId(prod.getNom(),prod.getPrenom());
//
//        String sql;
//        try {
//            sql = "SELECT ID_SERIE FROM SERIE WHERE id_prod = ?";
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setLong(1,idprod);
//            rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//
//                list = GetSerieByID(rs.getLong(1));
//                for (int i = 0; i < list.size(); i++) {
//                    list1.add(list.get(i));
//                }
//            }
//        }catch (Exception ex) {
//            System.out.println(ex.getMessage());
//        }
//        return list1;
//    }
//
//    public static List<Serie> FindByActor(Actor act) {
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        List<Serie> list = new ArrayList<>();
//        List<Serie> list1 = new ArrayList<>();
//        long idact=ActorDAO.getActId(act.getName(),act.getPrename());
//
//        String sql;
//        try {
//
//            try{sql = "SELECT ID_SERIE FROM SERIEACTORSUPP WHERE id_act = ?";
//                pstmt = conn.prepareStatement(sql);
//                pstmt.setLong(1,idact);
//                rs = pstmt.executeQuery();
//
//                while (rs.next()) {
//
//                    list = GetSerieByID(rs.getLong(1));
//                    for (int i = 0; i < list.size(); i++) {
//                        list1.add(list.get(i));
//                    }
//                }}catch (Exception e){
//                System.out.println("Serie n'a pas d'acteurs secandaires");
//            }
//
//            try{ sql = "SELECT ID_SERIE FROM SERIEACTORPRINC WHERE id_act = ?";
//                pstmt = conn.prepareStatement(sql);
//                pstmt.setLong(1,idact);
//
//                rs = pstmt.executeQuery();
//
//                while (rs.next()) {
//
//                    list = GetSerieByID(rs.getLong(1));
//                    for (int i = 0; i < list.size(); i++) {
//                        list1.add(list.get(i));
//                    }
//                }}catch (Exception e){
//                System.out.println("Serie n'a pas d'acteurs principales");
//            }
//
//        }catch (Exception ex) {
//            System.out.println(ex.getMessage());
//        }
//        return list1;
//
//    }



    public static boolean DeleteSerie(Serie serie) {
        PreparedStatement pstmt = null;
        String sql;

        try{
            sql = "DELETE FROM SERIE WHERE ID_SERIE = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,serie.getId());
            pstmt.executeUpdate();
            DeleteCorrespMainActorSerie(serie);
            DeleteCorrespSuppActorSerie(serie);
            return true;

        }catch (Exception e){
            System.out.println("Serie n'exite pas");
            return false;
        }
    }


    public static boolean DeleteCorrespMainActorSerie(Serie serie) {
        PreparedStatement pstmt = null;
        String sql;
        try{sql = "delete FROM SERIEACTORPRINC WHERE ID_SERIE = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,serie.getId());
            pstmt.executeUpdate();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean DeleteCorrespSuppActorSerie(Serie Serie) {
        PreparedStatement pstmt = null;
        String sql;
        try{sql = "delete FROM SERIEACTORSUPP WHERE ID_SERIE = ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,Serie.getId());

            pstmt.executeUpdate();
            return true;
        }catch (Exception e){
            return false;
        }
    }

//    public static boolean ModifVideoSerie(Serie serie,File NewVideo) {
//        PreparedStatement pstmt;
//        ResultSet rs;
//        String sql;
//
//        try {;
//            InputStream inputStreamSynopsisfilm=new FileInputStream(NewVideo);
//
//
//            sql = "UPDATE SERIE SET synopsis = '" + inputStreamSynopsisfilm + "' WHERE id_film = " + serie.getId();
//            pstmt = conn.prepareStatement(sql);
//            pstmt.executeQuery();
//
//            return true;
//        } catch (SQLException e) {
//            System.out.println("error dans la connection de la base");
//            return false;
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//
//    }
//
//






















}