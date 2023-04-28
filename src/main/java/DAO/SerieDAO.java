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
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
            }

        } catch(SQLException se) {
            se.printStackTrace();
            return -1;
        } catch(Exception e) {
            e.printStackTrace();
            return -1;
        }
        InsertMainActSerie(id,Serie.getIDMainactorList());
        InsertSuppActSerie(id,Serie.getIDSuppactorList());
        pstmt.close();
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
                pstmt.executeUpdate();

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
        pstmt.close();
//        conn.close();
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
                pstmt.executeUpdate();

            }
        } catch(SQLException se) {
            se.printStackTrace();

            return false;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        pstmt.close();

        return true;
    }


    public static List<Serie> GetSerieByName(String SerieName) throws SQLException, IOException {

        Serie serie;

        List<Serie> serieList = new ArrayList<>();

        String sql = "SELECT * FROM SERIE WHERE NAME like ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, "%"+SerieName+"%");

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {

            long ID = rs.getLong("ID_SERIE");
            long ID_PROD = rs.getLong("ID_PROD");
            Date DebutDate = rs.getDate("DEBUT_DATE");
            Blob Thumbnail = rs.getBlob("IMAGE");
            String StringGenre = rs.getString("LISTEGENRE");
            String[] genreArray = StringGenre.split(",");
            ArrayList<String> genreList = new ArrayList<>(Arrays.asList(genreArray));
            InputStream SerieThumbnail = Thumbnail.getBinaryStream();

            //Converting Blob Image to Jpeg File
            File fileThumb = new File("src/main/java/Temp/ImgSerie"+ID+".jpeg");
            OutputStream outS = new FileOutputStream(fileThumb);
            byte[] bufferImg = new byte[1024];
            int length;
            while ((length = SerieThumbnail.read(bufferImg)) != -1) {
                outS.write(bufferImg, 0, length);
            }

            List<Actor> ActorList = getPrincActorSerie(getPrincActorIDSerie(ID));
            List<Actor> SuppActorList = getSuppActorSerie(getSuppActorIDSerie(ID));

            ActorList.addAll(SuppActorList);

            serie = new Serie(ID,ID_PROD,SerieName,fileThumb,genreList,DebutDate.toLocalDate(),ActorList);

            serieList.add(serie);
        }
        rs.close();
        pstmt.close();
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
        rs.close();
        pstmt.close();
//        conn.close();
        return serieList;
    }


    public static List<Serie> GetSerieByIDNoActors(long ID) throws SQLException, IOException {

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


            serie = new Serie(ID,ID_PROD,SerieName,DESCRIPTION,DebutDate.toLocalDate(),Language,Country,genreList,fileThumbnail,numsSeasons,fileSynopsis,seasonList,null);

            serieList.add(serie);
        }
        rs.close();
        pstmt.close();
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

        rs.close();
        pstmtGetID.close();
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

        rs.close();
        pstmtGetID.close();
        return listIDActor;
    }

    public static List<Actor> getPrincActorSerie(List<Long> IDactors) throws SQLException, IOException {
        List<Actor> actorList = new ArrayList<>();

        String sql = "SELECT * FROM ACTOR WHERE ID_ACT = ?";
        ResultSet rs = null;

        PreparedStatement pstmtGetID = conn.prepareStatement(sql);

        for(Long lo : IDactors)
        {
            pstmtGetID.setLong(1, lo);
            rs = pstmtGetID.executeQuery();

            if (rs.next())
            {
                long IDactor = rs.getLong(1);
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

    public static List<Actor> getSuppActorSerie(List<Long> IDactors) throws SQLException {
        List<Actor> actorList = new ArrayList<>();

        String sql = "SELECT * FROM ACTOR WHERE ID_ACT = ?";

        ResultSet rs = null;

        PreparedStatement pstmtGetID = conn.prepareStatement(sql);

        for(Long lo : IDactors)
        {
            pstmtGetID.setLong(1, lo);
            rs = pstmtGetID.executeQuery();

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


        pstmtGetID.close();
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
            Date DebutDate = rs.getDate("DEBUT_DATE");
            Blob Thumbnail = rs.getBlob("IMAGE");
            String StringGenre = rs.getString("LISTEGENRE");
            String[] genreArray = StringGenre.split(",");
            ArrayList<String> genreList = new ArrayList<>(Arrays.asList(genreArray));
            InputStream SerieThumbnail = Thumbnail.getBinaryStream();

            //Converting Blob Image to Jpeg File
            File fileThumb = new File("src/main/java/Temp/ImgSerie"+ID+".jpeg");
            OutputStream outS = new FileOutputStream(fileThumb);
            byte[] bufferImg = new byte[1024];
            int length;
            while ((length = SerieThumbnail.read(bufferImg)) != -1) {
                outS.write(bufferImg, 0, length);
            }

            List<Actor> ActorList = getPrincActorSerie(getPrincActorIDSerie(ID));
            List<Actor> SuppActorList = getSuppActorSerie(getSuppActorIDSerie(ID));

            ActorList.addAll(SuppActorList);

            serie = new Serie(ID,ID_PROD,SerieName,fileThumb,genreList,DebutDate.toLocalDate(),ActorList);

            serieList.add(serie);
        }
        rs.close();
        pstmt.close();

        return serieList;
    }




    public static List<Serie> GetAllSeries() throws SQLException, IOException {

        Serie serie;

        List<Serie> serieList = new ArrayList<>();

        String sql = "SELECT * FROM serie";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {

            long ID = rs.getLong("ID_SERIE");
            String SerieName = rs.getString("NAME");
            long ID_PROD = rs.getLong("ID_PROD");
            Date DebutDate = rs.getDate("DEBUT_DATE");
            Blob Thumbnail = rs.getBlob("IMAGE");
            String StringGenre = rs.getString("LISTEGENRE");
            String[] genreArray = StringGenre.split(",");
            ArrayList<String> genreList = new ArrayList<>(Arrays.asList(genreArray));
            InputStream SerieThumbnail = Thumbnail.getBinaryStream();

            //Converting Blob Image to Jpeg File
            File fileThumb = new File("src/main/java/Temp/ImgSerie"+ID+".jpeg");
            OutputStream outS = new FileOutputStream(fileThumb);
            byte[] bufferImg = new byte[1024];
            int length;
            while ((length = SerieThumbnail.read(bufferImg)) != -1) {
                outS.write(bufferImg, 0, length);
            }

            List<Actor> ActorList = getPrincActorSerie(getPrincActorIDSerie(ID));
            List<Actor> SuppActorList = getSuppActorSerie(getSuppActorIDSerie(ID));

            ActorList.addAll(SuppActorList);

            serie = new Serie(ID,ID_PROD,SerieName,fileThumb,genreList,DebutDate.toLocalDate(),ActorList);

            serieList.add(serie);
        }

        rs.close();
        pstmt.close();
        return serieList;

    }




    public static List<Serie> GetSeriesByProducer(Producer producer) throws SQLException, IOException {

        Serie serie ;

        List<Serie> serieList = new ArrayList<>();

        String sql = "SELECT * FROM serie where ID_PROD = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setLong(1, producer.getId());

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {

            long ID = rs.getLong("ID_SERIE");
            String SerieName = rs.getString("NAME");
            long ID_PROD = rs.getLong("ID_PROD");
            Date DebutDate = rs.getDate("DEBUT_DATE");
            Blob Thumbnail = rs.getBlob("IMAGE");
            String StringGenre = rs.getString("LISTEGENRE");
            String[] genreArray = StringGenre.split(",");
            ArrayList<String> genreList = new ArrayList<>(Arrays.asList(genreArray));
            InputStream SerieThumbnail = Thumbnail.getBinaryStream();

            //Converting Blob Image to Jpeg File
            File fileThumb = new File("src/main/java/Temp/ImgSerie"+ID+".jpeg");
            OutputStream outS = new FileOutputStream(fileThumb);
            byte[] bufferImg = new byte[1024];
            int length;
            while ((length = SerieThumbnail.read(bufferImg)) != -1) {
                outS.write(bufferImg, 0, length);
            }


            List<Actor> ActorList = getPrincActorSerie(getPrincActorIDSerie(ID));
            List<Actor> SuppActorList = getSuppActorSerie(getSuppActorIDSerie(ID));

            ActorList.addAll(SuppActorList);

            serie = new Serie(ID,ID_PROD,SerieName,fileThumb,genreList,DebutDate.toLocalDate(),ActorList);

            serieList.add(serie);
        }
        rs.close();
        pstmt.close();

        return serieList;
    }


    public static boolean DeleteSerie(Serie serie) throws SQLException {
        PreparedStatement pstmt = null;
        String sql;

        try{
            sql = "DELETE FROM SERIE WHERE ID_SERIE = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,serie.getId());
            pstmt.executeUpdate();
            DeleteCorrespMainActorSerie(serie);
            DeleteCorrespSuppActorSerie(serie);
            pstmt.close();
            return true;

        }catch (Exception e){
            e.printStackTrace();
            assert pstmt != null;
            pstmt.close();
            return false;
        }
    }


    public static boolean DeleteCorrespMainActorSerie(Serie serie) throws SQLException {
        PreparedStatement pstmt = null;
        String sql;
        try{sql = "delete FROM SERIEACTORPRINC WHERE ID_SERIE = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,serie.getId());
            pstmt.executeUpdate();
            return true;
        }catch (Exception e){
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

    public static boolean DeleteCorrespSuppActorSerie(Serie Serie) throws SQLException {
        PreparedStatement pstmt = null;
        String sql;
        try{sql = "delete FROM SERIEACTORSUPP WHERE ID_SERIE = ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,Serie.getId());

            pstmt.executeUpdate();
            return true;
        }catch (Exception e){
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

    public static boolean modifimg(Serie serie, File img) throws SQLException {
        PreparedStatement pstmt = null;
        String sql;

        try {
            // On lit le contenu du fichier dans un tableau de bytes
            byte[] imgBytes = Files.readAllBytes(img.toPath());

            // On prépare la requête SQL avec un paramètre pour le tableau de bytes
            sql = "UPDATE Serie SET IMAGE = ? WHERE ID_SERIE = ?";
            pstmt = conn.prepareStatement(sql);

            // On affecte le paramètre avec le tableau de bytes
            pstmt.setBytes(1, imgBytes);
            pstmt.setLong(2, serie.getId());

            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
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

    public static boolean ModifSynopsisSerie(Serie serie,File NewSynopsis) {
        PreparedStatement pstmt = null;
        String sql;

        try {
            InputStream inputStreamSynopsisSerie = new FileInputStream(NewSynopsis);
            sql = "UPDATE SERIE SET SYNOPSIS = ? WHERE ID_SERIE = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setBlob(1,inputStreamSynopsisSerie);
            pstmt.setLong(2,serie.getId());
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


    public static boolean modifnom(Serie Serie,String nom) throws SQLException {
        PreparedStatement pstmt = null;
        String sql;

        try {
            sql = "UPDATE Serie SET Name = '" + nom + "' WHERE ID_SERIE = " + Serie.getId();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeQuery();
            return true;
        } catch (SQLException e) {
            System.out.println("error dans la connection de la base");

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


    public static boolean modifdescription(Serie serie,String description) throws SQLException {
        PreparedStatement pstmt = null;
        String sql;

        try {
            sql = "UPDATE Serie SET description = ? WHERE ID_SERIE = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,description);
            pstmt.setLong(2,serie.getId());
            pstmt.executeQuery();

            return true;
        } catch (SQLException e) {
            System.out.println("error dans la connection de la base");

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

    public static boolean modiflangues(Serie Serie,String langue) throws SQLException {
        PreparedStatement pstmt = null;
        String sql;

        try {
            sql = "UPDATE Serie SET Language = '" + langue + "' WHERE ID_SERIE = " + Serie.getId();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeQuery();

            return true;
        } catch (SQLException e) {
            System.out.println("error dans la connection de la base");

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

    public static boolean modifpaysoregine(Serie Serie,String paysorgine) throws SQLException {
        PreparedStatement pstmt = null;
        String sql;

        try {
            sql = "UPDATE Serie SET Country = '" + paysorgine + "' WHERE ID_SERIE = " + Serie.getId();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeQuery();


            return true;
        } catch (SQLException e) {
            System.out.println("error dans la connection de la base");

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

    public static boolean modifAnnerdesoritie(Serie serie, LocalDate date) throws SQLException {
        PreparedStatement pstmt = null;
        String sql;
        try {

            sql = "UPDATE Serie SET DEBUT_DATE = ? WHERE ID_SERIE = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(date));
            pstmt.setLong(2,serie.getId());
            pstmt.executeQuery();

            return true;
        } catch (SQLException e) {
            System.out.println("error dans la connection de la base");

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

    public static boolean modiflistegenre(Serie serie,List<String> listegenre ) {
        PreparedStatement pstmt = null;
        String sql;

        try {
            String genreListString = String.join(",", listegenre.stream().map(Object::toString).toArray(String[]::new));
            sql = "UPDATE Serie SET listegenre = '" + genreListString + "' WHERE ID_SERIE = " + serie.getId();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeQuery();
            return true;
        } catch (SQLException e) {
            System.out.println("error dans la connection de la base");
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

    public static boolean deleteSerie_actsec(Serie serie,Actor act) {
        PreparedStatement pstmt = null;
        String sql;
        try {
            sql = "delete FROM SERIEACTORSUPP WHERE ID_SERIE = ? and id_act=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, serie.getId());
            pstmt.setLong(2, act.getID());
            pstmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("acteur n'exite pas");
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

    public static boolean deleteSerie_actprinc(Serie serie,Actor act) {
        PreparedStatement pstmt = null;
        String sql;
        try {
            sql = "delete FROM SERIEACTORPRINC WHERE ID_SERIE = ? and id_act=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, serie.getId());
            pstmt.setLong(2, act.getID());
            pstmt.executeUpdate();
            return true;
        } catch (Exception e) {

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

    public static boolean ajoutSerie_actprinc(Serie serie,Actor act) {
        PreparedStatement pstmt = null;
        String sql;

        try {
            sql = "INSERT INTO SERIEACTORPRINC (id_act,ID_SERIE)" +
                    " VALUES (?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, act.getID());

            pstmt.setLong(2, serie.getId());

            pstmt.executeUpdate();

            return true;

        } catch (Exception e) {

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
    public static boolean ajoutSerie_actsec(Serie serie,Actor act) {
        PreparedStatement pstmt = null;
        String sql;
        try {
            sql = "INSERT INTO SERIEACTORSUPP (id_act,ID_SERIE)" +
                    " VALUES (?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, act.getID());
            pstmt.setLong(2, serie.getId());
            pstmt.executeUpdate();

            return true;

        } catch (Exception e) {

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

    public static List<Serie> searchSeries(List<String> searchTerms) throws SQLException, IOException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM SERIE WHERE ");
        List<Serie> serieList = new ArrayList<>();
        for (int i = 0; i < searchTerms.size(); i++) {
            if (i > 0) {
                sqlBuilder.append(" AND ");
            }
            sqlBuilder.append("LISTEGENRE LIKE ?");
        }
        String sql = sqlBuilder.toString();

        PreparedStatement stmt = conn.prepareStatement(sql);
        for (int i = 0; i < searchTerms.size(); i++) {
            stmt.setString(i + 1, "%" + searchTerms.get(i) + "%");
        }

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            long ID = rs.getLong("ID_SERIE");
            long ID_PROD = rs.getLong("ID_PROD");
            String SerieName = rs.getString("NAME");
            Blob Thumbnail = rs.getBlob("IMAGE");
            String StringGenre = rs.getString("LISTEGENRE");
            String[] genreArray = StringGenre.split(",");
            ArrayList<String> genreList = new ArrayList<>(Arrays.asList(genreArray));
            Date DebutDate = rs.getDate("DEBUT_DATE");
            InputStream SerieThumbnail = Thumbnail.getBinaryStream();

            //Converting Blob Image to Jpeg File
            File fileThumb = new File("src/main/java/Temp/ImgSerie"+ID+".jpeg");
            OutputStream outS = new FileOutputStream(fileThumb);
            byte[] bufferImg = new byte[1024];
            int length;
            while ((length = SerieThumbnail.read(bufferImg)) != -1) {
                outS.write(bufferImg, 0, length);
            }

            List<Actor> ActorList = getPrincActorSerie(getPrincActorIDSerie(ID));
            List<Actor> SuppActorList = getSuppActorSerie(getSuppActorIDSerie(ID));

            ActorList.addAll(SuppActorList);

            Serie serie = new Serie(ID,ID_PROD,SerieName,fileThumb,genreList,DebutDate.toLocalDate(),ActorList);

            serieList.add(serie);
        }
        rs.close();
        stmt.close();
        return serieList;
    }


    public static List<Serie> getMostRecentSeries(int numSeries) throws SQLException, IOException {
        List<Serie> serieList = new ArrayList<>();
        String sql = "SELECT * FROM Serie ORDER BY DEBUT_DATE DESC FETCH FIRST ? ROWS ONLY";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, numSeries);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            long ID = rs.getLong("ID_SERIE");
            long ID_PROD = rs.getLong("ID_PROD");
            String SerieName = rs.getString("NAME");
            Blob Thumbnail = rs.getBlob("IMAGE");
            String StringGenre = rs.getString("LISTEGENRE");
            String[] genreArray = StringGenre.split(",");
            ArrayList<String> genreList = new ArrayList<>(Arrays.asList(genreArray));
            Date DebutDate = rs.getDate("DEBUT_DATE");
            InputStream SerieThumbnail = Thumbnail.getBinaryStream();

            //Converting Blob Image to Jpeg File
            File fileThumb = new File("src/main/java/Temp/ImgSerie"+ID+".jpeg");
            OutputStream outS = new FileOutputStream(fileThumb);
            byte[] bufferImg = new byte[1024];
            int length;
            while ((length = SerieThumbnail.read(bufferImg)) != -1) {
                outS.write(bufferImg, 0, length);
            }
            List<Actor> ActorList = getPrincActorSerie(getPrincActorIDSerie(ID));
            List<Actor> SuppActorList = getSuppActorSerie(getSuppActorIDSerie(ID));

            ActorList.addAll(SuppActorList);
            Serie serie = new Serie(ID,ID_PROD,SerieName,fileThumb,genreList,DebutDate.toLocalDate(),ActorList);
            serieList.add(serie);
            }
        rs.close();
        stmt.close();
        return serieList;
    }

    public static List<Serie> FindSeriesByActor(Actor act) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Serie> serieList=new ArrayList<>();
        long idact= act.getID();
        String sql;
        try {
            try{sql = "SELECT ID_SERIE FROM SerieActorSupp WHERE id_act = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setLong(1,idact);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    serieList.add(GetSerieByID(rs.getLong(1)).get(0));
                }

            }catch (Exception e){
                System.out.println("Error Retrieving as Supporting Actor");
            }

            try{ sql = "SELECT ID_SERIE FROM SerieActorPrinc WHERE id_act = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setLong(1,idact);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    serieList.add(GetSerieByID(rs.getLong(1)).get(0));
                }

            }catch (Exception e){
                System.out.println("Error Retrieving as Main Actor");
            }

        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return serieList;
    }

}