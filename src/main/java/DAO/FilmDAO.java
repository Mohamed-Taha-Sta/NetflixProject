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

import static DAO.SerieDAO.getPrincActorSerie;
import static DAO.SerieDAO.getSuppActorSerie;

public class FilmDAO {

    private static final Connection conn = ConxDB.getInstance();

    public static boolean Add(Film film) {
        boolean etat = true;
        PreparedStatement pstmt = null;
        //java.sql.Time sqlTime = java.sql.Time.valueOf(film.getDuree());
        ResultSet rs;
        String sql3;

        /*    public Film(String nom, String realisateur, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre, File img, LocalTime duree, ArrayList<Actor> acteur, , long vueNbr, long score, long votes, File file, File synopsis, File film) {
         */
        try {
           // String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (3, 'Jesser')";
            String genreListString = String.join(",", film.getListegenre().stream().map(Object::toString).toArray(String[]::new));
            String sql = "INSERT INTO Film (nom,description,annee_sortie,langue,paysorigine,listegenre,img,duree,vuenbr,score,vote,synopsis,film,id_prod)" +
                    " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            String sql1 = "SELECT id_film FROM Film WHERE Film.nom='" + film.getNom() + "' AND Film.id_prod ='" + film.getId_realisateur() + "'";
            pstmt = conn.prepareStatement(sql);
            InputStream inputStreamSynopsisimg = null;
            InputStream inputStreamSynopsissynops = null;
            InputStream inputStreamSynopsisfilm= null;

            inputStreamSynopsisimg = new FileInputStream(film.getImg());

            inputStreamSynopsisfilm=new FileInputStream(film.getFilm());

            inputStreamSynopsissynops=new FileInputStream(film.getSynopsis());

            pstmt.setString(1,film.getNom());
            pstmt.setString(2,film.getDescription());
            pstmt.setDate(3,java.sql.Date.valueOf(film.getAnnerdesortie()));
            pstmt.setString(4,film.getLangue());
            pstmt.setString(5,film.getPaysorigine());
            pstmt.setString(6,genreListString);
            pstmt.setBlob(7,inputStreamSynopsisimg);
            pstmt.setString(8, film.getDuree());
            pstmt.setLong(9,film.getVueNbr());
            pstmt.setLong(10,film.getScore());
            pstmt.setLong(11,film.getVotes());
            pstmt.setBlob(12,inputStreamSynopsissynops);
            pstmt.setBlob(13,inputStreamSynopsisfilm);
            pstmt.setLong(14,film.getId_realisateur());
            System.out.println(1);
            pstmt.executeUpdate();
            System.out.println(1);

            pstmt = conn.prepareStatement(sql1);

            rs = pstmt.executeQuery();
            rs.next();

            int idfilm=rs.getInt(1);

            List<Actor> act=film.getActeur();

            for(int i=0;i<act.size();i++){

                if(act.get(i) instanceof MainActor ){
                    sql3 = "INSERT INTO Acteurprinc_Film (id_act, id_film) VALUES ('" + Long.toString(act.get(i).getID()) + "', '" + Integer.toString(idfilm) + "')";
                    pstmt = conn.prepareStatement(sql3);
                    pstmt.executeUpdate();
//                    ActorDAO.ajout_acteur(act.get(i));
                }else{
                    sql3 = "INSERT INTO acteursec_film (id_act, id_film) VALUES ('" + Long.toString(act.get(i).getID()) + "', '" + Integer.toString(idfilm) + "')";
                    pstmt = conn.prepareStatement(sql3);
                    pstmt.executeUpdate();
//                    ActorDAO.ajout_acteur(act.get(i));
                }

            }

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
            etat=false;
        } catch (FileNotFoundException e) {

        }
        return etat;
    }


  /*  public static List<String> recherche_film(String filmname) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<String>list=new ArrayList<>();
        try {
            // String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (3, 'Jesser')";
            String sql="select id_film,nom,realisateur,annerdesortie,langue,paysorigine,duree from Film where Film.nom=%"+filmname+"%";
            pstmt = conn.prepareStatement(sql);

            pstmt.executeQuery();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
            list.add(rs.getString(1));
            list.add(rs.getString(2));
            list.add(rs.getString(3));
            list.add(rs.getString(4));
            list.add(rs.getString(5));
            list.add(rs.getString(6));
            list.add(rs.getString(7));}

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


        return list;
    }
    public static void recherche_filmact(Actor act) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Long idact=ActorDAO.getactid(act.getName(),act.getPrename());
        try {

            // String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (3, 'Jesser')";
            String sql="select id_film from relation where relation.id_act="+Long.toString(idact);
            pstmt = conn.prepareStatement(sql);

            pstmt.executeQuery();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
            recherche_film(rs.getLong(1));
            }

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


    }*/
    public static List<Film> FindByID(Long filmid) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Film>list=new ArrayList<>();
        try {
            // String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (3, 'Jesser')";
            String sql="select id_film,nom,description,ANNEE_SORTIE,langue,paysorigine,listegenre,img,duree,vuenbr,score,vote,synopsis,film,id_prod from Film where Film.id_film="+filmid;
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                Long id=rs.getLong(1);
                String nom=rs.getString(2);
                String description= rs.getString(3);
                Date annerdesortie=rs.getDate(4);
                String langue=rs.getString(5);
                String paysorigine=rs.getString(6);
                String listegenre= rs.getString(7);
                Blob img=rs.getBlob(8);
                String duree=rs.getString(9);
                Long vunbr=rs.getLong(10);
                Long score=rs.getLong(11);
                Long vote=rs.getLong(12);
                InputStream image=img.getBinaryStream();
                InputStream synop=rs.getBinaryStream(13);
                InputStream vedio=rs.getBinaryStream(14);
                Long idrealisateur=rs.getLong(15);
                //Converting Blob Image to Jpeg File
                File fileImg = new File("src/main/java/Temp/ImgFilm"+id+".jpeg");
                OutputStream outS = new FileOutputStream(fileImg);
                byte[] bufferImg = new byte[1024];
                int length;
                while ((length = image.read(bufferImg)) != -1) {
                    outS.write(bufferImg, 0, length);
                }

                //Handeling the Video, from inputStream
                Path outputFilePath = Paths.get("src/main/java/Temp/VideoSynopsis"+id+".mp4");
                try (OutputStream outputStream = Files.newOutputStream(outputFilePath)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = synop.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    System.out.println("Error Handelling the video");
                }
                //Handeling the Video, from inputStream
                Path outputFilePath1 = Paths.get("src/main/java/Temp/VideoFilm"+id+".mp4");
                try (OutputStream outputStream1 = Files.newOutputStream(outputFilePath1)) {
                    byte[] bufferved = new byte[4096];
                    int bytesReadved;
                    while ((bytesReadved = vedio.read(bufferved)) != -1) {
                        outputStream1.write(bufferved, 0, bytesReadved);
                    }
                } catch (IOException e) {
                    System.out.println("Error Handelling the video");
                }

                File filesynop = new File("src/main/java/Temp/SynopsisFilm"+ id +".mp4");
                File fileImage = new File("src/main/java/Temp/ImgFilm"+ id +".jpeg");
                File filmvedio=new File("src/main/java/Temp/VideoFilm"+ id +".mp4");


                ArrayList<String> genrelist = new ArrayList<>();
                String[] array = listegenre.split(",");
                for (String s : array) {
                    genrelist.add(s);
                }


                Film filmm=new Film(filmid,nom,description,annerdesortie.toLocalDate(),langue,paysorigine,genrelist,fileImage,duree,vunbr,score,vote,filesynop,filmvedio,idrealisateur);
                list.add(filmm);
            }

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public static ArrayList<Film> FindByName(String filmnom) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Film>list=new ArrayList<>();
        try {
            // String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (3, 'Jesser')";
            //String sql="select id_film,nom,realisateur,annerdesortie,langue,paysorigine,listegenre,img,duree,vuenbr,score,vote,synopsis,film from Film where Film.nom like %"+filmnom+"%";
            String sql="SELECT id_film, nom, description, ANNEE_SORTIE, langue, paysorigine, listegenre, img, duree, vuenbr, score, vote, synopsis, film,id_prod " +
                    "FROM Film " +
                    "WHERE Film.nom LIKE '%" + filmnom + "%'";
            pstmt = conn.prepareStatement(sql);


            rs = pstmt.executeQuery();
            while (rs.next()) {
                Long id=rs.getLong(1);
                String nom=rs.getString(2);
                String decription= rs.getString(3);
                Date annerdesortie=rs.getDate(4);
                String langue=rs.getString(5);
                String paysorigine=rs.getString(6);
                String listegenre= rs.getString(7);
                Blob img=rs.getBlob(8);
                String duree=rs.getString(9);
                Long vunbr=rs.getLong(10);
                Long score=rs.getLong(11);
                Long vote=rs.getLong(12);
                InputStream image=img.getBinaryStream();
                InputStream synop=rs.getBinaryStream(13);
                InputStream vedio=rs.getBinaryStream(14);
                Long idrealisateur=rs.getLong(15);

                //Converting Blob Image to Jpeg File
                File fileImg = new File("src/main/java/Temp/ImgFilm"+id+".jpeg");
                OutputStream outS = new FileOutputStream(fileImg);
                byte[] bufferImg = new byte[1024];
                int length;
                while ((length = image.read(bufferImg)) != -1) {
                    outS.write(bufferImg, 0, length);
                }

                //Handeling the Video, from inputStream
                Path outputFilePath = Paths.get("src/main/java/Temp/SynopsisFilm"+id+".mp4");
                try (OutputStream outputStream = Files.newOutputStream(outputFilePath)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = synop.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    System.out.println("Error Handelling the video");
                }
                //Handeling the Video, from inputStream
                Path outputFilePath1 = Paths.get("src/main/java/Temp/VideoFilm"+id+".mp4");
                try (OutputStream outputStream1 = Files.newOutputStream(outputFilePath1)) {
                    byte[] bufferved = new byte[4096];
                    int bytesReadved;
                    while ((bytesReadved = vedio.read(bufferved)) != -1) {
                        outputStream1.write(bufferved, 0, bytesReadved);
                    }
                } catch (IOException e) {
                    System.out.println("Error Handelling the video");
                }

                File filesynop = new File("src/main/java/Temp/SynopsisFilm"+ id +".mp4");
                File fileImage = new File("src/main/java/Temp/ImgFilm"+id+".jpeg");
                File filmvedio=new File("src/main/java/Temp/VideoFilm"+ id +".mp4");


                ArrayList<String> genrelist = new ArrayList<>();
                String[] array = listegenre.split(",");
                for (String s : array) {
                    genrelist.add(s);
                }


                Film filmm=new Film(nom,decription,annerdesortie.toLocalDate(),langue,paysorigine,genrelist,fileImage,duree,vunbr,score,vote,filesynop,filmvedio,idrealisateur);
                list.add(filmm);
            }

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return list;
    }
    public static ArrayList<Film> FindByActor(Actor act) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Film> list=new ArrayList<>();
        ArrayList<Film>list1=new ArrayList<>();
        Long idact=ActorDAO.getActId(act.getName(),act.getPrename());

        String sql;
        try {

            // String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (3, 'Jesser')";
            //String sql="select id_film from Acteurprinc_Film where Acteurprinc_Film.id_act="+Long.toString(idact);
            try{sql = "SELECT id_film FROM acteursec_film WHERE id_act = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,idact);
            rs = pstmt.executeQuery();

                while (rs.next()) {

                    list= (ArrayList<Film>) FindByID(rs.getLong(1));
                for (int i = 0; i < list.size(); i++) {
                    list1.add(list.get(i));
                }
            }}catch (Exception e){
                System.out.println("ce acteur a aucun acteur secandaire");
            }

            try{ sql = "SELECT id_film FROM Acteurprinc_Film WHERE id_act = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,idact);

            rs = pstmt.executeQuery();

                while (rs.next()) {

                list= (ArrayList<Film>) FindByID(rs.getLong(1));
                for (int i = 0; i < list.size(); i++) {
                    list1.add(list.get(i));
                }
            }}catch (Exception e){
                System.out.println("ce acteur a aucun acteur principale");
            }


        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list1;

    }

    public static ArrayList<Film> FindByproducer(Producer prod) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Film> list=new ArrayList<>();
        ArrayList<Film>list1=new ArrayList<>();

        String sql;
        try {

            // String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (3, 'Jesser')";
            //String sql="select id_film from Acteurprinc_Film where Acteurprinc_Film.id_act="+Long.toString(idact);
            try{sql = "SELECT id_film FROM Film WHERE id_prod = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setLong(1,prod.getId());
                rs = pstmt.executeQuery();

                while (rs.next()) {

                    list= (ArrayList<Film>) FindByID(rs.getLong(1));
                    for (int i = 0; i < list.size(); i++) {
                        list1.add(list.get(i));
                    }
                }}catch (Exception e){
                System.out.println("ce acteur a aucun acteur secandaire");
            }




        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list1;

    }




    public static ArrayList<Film> GetAllFilms() {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Film> list=new ArrayList<>();
        ArrayList<Film>list1=new ArrayList<>();

        String sql;
        try {

            try{sql = "SELECT * FROM Film";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();

                while (rs.next()) {

                    list= (ArrayList<Film>) FindByID(rs.getLong(1));
                    System.out.println("Your film list is: " + list);
                    for (int i = 0; i < list.size(); i++) {

                        list1.add(list.get(i));
                    }
                }
            }catch (Exception e){
                System.out.println("Error getting all Actors");
            }

        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return list1;

    }



    public static boolean deleteFilm(Film film) {
           PreparedStatement pstmt = null;


           String sql;

            // String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (3, 'Jesser')";
            //String sql="select id_film from Acteurprinc_Film where Acteurprinc_Film.id_act="+Long.toString(idact);
            try{sql = "delete FROM Film WHERE id_film = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,film.getId());
             pstmt.executeUpdate();
            return true;

            }catch (Exception e){
                System.out.println("Film n'exite pas");
                return false;
            }




    }

    public static Long getvote(Film film){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Film>list=new ArrayList<>();
        try {
            String sql="select vote from Film where Film.id_film="+film.getId();
            pstmt = conn.prepareStatement(sql);
            rs=pstmt.executeQuery();
            rs.next();
            return rs.getLong(1);
        }catch (Exception e){
            System.out.println("film nexiste pas");
            return Long.valueOf(-1);
        }





}
    public static Long getscore(Film film){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Film>list=new ArrayList<>();
        try {
            String sql="select score from Film where Film.id_film="+film.getId();
            pstmt = conn.prepareStatement(sql);
            rs=pstmt.executeQuery();
            rs.next();
            return rs.getLong(1);
        }catch (Exception e){
            System.out.println("film nexiste pas");
            return Long.valueOf(-1);
        }





    }
    public static Long getnbrvue(Film film){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Film>list=new ArrayList<>();
        try {
            String sql="select vuenbr from Film where Film.id_film="+film.getId();
            pstmt = conn.prepareStatement(sql);
            rs=pstmt.executeQuery();
            rs.next();
            return rs.getLong(1);
        }catch (Exception e){
            System.out.println("film nexiste pas");
            return Long.valueOf(-1);
        }





    }




    public static boolean UpdatePositiveScoreFilm(Film film) {
        PreparedStatement pstmt;
        ResultSet rs;
        String sql;
        long score = getscore(film);
        long votes = getvote(film);


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
        try {;
            score = score + 1;
            votes=votes+1;
            sql = "UPDATE film SET score = '" + score + "' WHERE id_film = " + film.getId();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeQuery();
            sql = "UPDATE film SET vote = '" + votes + "' WHERE id_film = " + film.getId();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeQuery();
            return true;
        } catch (SQLException e) {
            System.out.println("error dans la connection de la base");
            return false;
        }


    }
    public static boolean UpdatenegativeScoreFilm(Film film) {
        PreparedStatement pstmt;
        ResultSet rs;
        String sql;
        long votes = getvote(film);



        if (votes == -1)
        {
            System.out.println("Error retrieving Votes in UpdatePositiveScoreEpisode Function ");
            return false;
        }
        try {;
            votes++;
            sql = "UPDATE film SET vote = '" + votes + "' WHERE id_film = " + film.getId();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeQuery();
            return true;
        } catch (SQLException e) {
            System.out.println("error dans la connection de la base");
            return false;
        }


    }
    public static boolean UpdatevuenbrFilm(Film film) {
        PreparedStatement pstmt;
        ResultSet rs;
        String sql;
        long vuenbr = getnbrvue(film);



        if (vuenbr == -1)
        {
            System.out.println("Error retrieving vuenbr in UpdatePositiveScoreEpisode Function ");
            return false;
        }
        try {;
            vuenbr++;
            sql = "UPDATE film SET vuenbr = '" + vuenbr + "' WHERE id_film = " + film.getId();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeQuery();
            return true;
        } catch (SQLException e) {
            System.out.println("error dans la connection de la base");
            return false;
        }


    }

    public static Long getscorepourcantage(Film film){
        Long socore=getscore(film);
        Long votesTotal=getvote(film);
        return((socore*100)/votesTotal);

    }












    public static boolean modifnom(Film film,String nom) {
        PreparedStatement pstmt;
        ResultSet rs;
        String sql;

        try {;

            sql = "UPDATE film SET nom = '" + nom + "' WHERE id_film = " + film.getId();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeQuery();

            return true;
        } catch (SQLException e) {
            System.out.println("error dans la connection de la base");
            return false;
        }


    }
    public static boolean modifdescription(Film film,String description) {
        PreparedStatement pstmt;
        ResultSet rs;
        String sql;

        try {;

            sql = "UPDATE film SET description = '" + description + "' WHERE id_film = " + film.getId();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeQuery();

            return true;
        } catch (SQLException e) {
            System.out.println("error dans la connection de la base");
            return false;
        }


    }
    public static boolean modiflangues(Film film,String langue) {
        PreparedStatement pstmt;
        ResultSet rs;
        String sql;

        try {;

            sql = "UPDATE film SET langue = '" + langue + "' WHERE id_film = " + film.getId();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeQuery();

            return true;
        } catch (SQLException e) {
            System.out.println("error dans la connection de la base");
            return false;
        }


    }
    public static boolean modifpaysoregine(Film film,String paysorgine) {
        PreparedStatement pstmt;
        ResultSet rs;
        String sql;

        try {;

            sql = "UPDATE film SET paysorigine = '" + paysorgine + "' WHERE id_film = " + film.getId();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeQuery();

            return true;
        } catch (SQLException e) {
            System.out.println("error dans la connection de la base");
            return false;
        }


    }
    public static boolean modifAnnerdesoritie(Film film, LocalDate date) {
        PreparedStatement pstmt;
        ResultSet rs;
        String sql;

        try {;

            sql = "UPDATE film SET ANNEE_SORTIE = '" + java.sql.Date.valueOf(date) + "' WHERE id_film = " + film.getId();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeQuery();

            return true;
        } catch (SQLException e) {
            System.out.println("error dans la connection de la base");
            return false;
        }


    }
    public static boolean modiflistegenre(Film film,List<String> listegenre ) {
        PreparedStatement pstmt;
        ResultSet rs;
        String sql;

        try {;
            String genreListString = String.join(",", listegenre.stream().map(Object::toString).toArray(String[]::new));

            sql = "UPDATE film SET listegenre = '" + genreListString + "' WHERE id_film = " + film.getId();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeQuery();

            return true;
        } catch (SQLException e) {
            System.out.println("error dans la connection de la base");
            return false;
        }


    }
    public static boolean modifduree(Film film,String duree ) {
        PreparedStatement pstmt;
        ResultSet rs;
        String sql;

        try {;

            sql = "UPDATE film SET duree = '" + duree + "' WHERE id_film = " + film.getId();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeQuery();

            return true;
        } catch (SQLException e) {
            System.out.println("error dans la connection de la base");
            return false;
        }


    }
    /*public static boolean modifimg(Film film,File img ) {
        PreparedStatement pstmt;
        ResultSet rs;
        String sql;

        try {;
           InputStream inputStreamSynopsisimg = new FileInputStream(img);

            sql = "UPDATE film SET img = '" + inputStreamSynopsisimg + "' WHERE id_film = " + film.getId();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeQuery();

            return true;
        } catch (SQLException e) {
            System.out.println("error dans la connection de la base");
            return false;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }*/
    /*public static boolean modifsynop(Film film,File synop ) {
        PreparedStatement pstmt;
        ResultSet rs;
        String sql;

        try {;
             InputStream inputStreamSynopsissynops=new FileInputStream(synop);

            sql = "UPDATE film SET synopsis = '" + inputStreamSynopsissynops + "' WHERE id_film = " + film.getId();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeQuery();

            return true;
        } catch (SQLException e) {
            System.out.println("error dans la connection de la base");
            return false;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }*/
    /*public static boolean modiffilmvedio(Film film,File vid ) {
        PreparedStatement pstmt;
        ResultSet rs;
        String sql;

        try {;

            InputStream inputStreamSynopsisfilm=new FileInputStream(vid);


            sql = "UPDATE film SET film = '" + inputStreamSynopsisfilm + "' WHERE id_film = " + film.getId();
            pstmt = conn.prepareStatement(sql);

            pstmt.executeQuery();

            return true;
        } catch (SQLException e) {
            System.out.println("error dans la connection de la base");
            return false;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }*/
    public static boolean modifimg(Film film, File img) {
        PreparedStatement pstmt;
        String sql;

        try {
            // On lit le contenu du fichier dans un tableau de bytes
            byte[] imgBytes = Files.readAllBytes(img.toPath());

            // On prépare la requête SQL avec un paramètre pour le tableau de bytes
            sql = "UPDATE film SET img = ? WHERE id_film = ?";
            pstmt = conn.prepareStatement(sql);

            // On affecte le paramètre avec le tableau de bytes
            pstmt.setBytes(1, imgBytes);
            pstmt.setLong(2, film.getId());

            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
            return false;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }
    public static boolean modiffilmvedio(Film film, File vid) {
        PreparedStatement pstmt;
        String sql;

        try {
            // On lit le contenu du fichier dans un tableau de bytes
            byte[] vidBytes = Files.readAllBytes(vid.toPath());

            // On prépare la requête SQL avec un paramètre pour le tableau de bytes
            sql = "UPDATE film SET film = ? WHERE id_film = ?";
            pstmt = conn.prepareStatement(sql);

            // On affecte le paramètre avec le tableau de bytes
            pstmt.setBytes(1, vidBytes);
            pstmt.setLong(2, film.getId());

            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
            return false;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }
    public static boolean modifsynop(Film film, File synop) {
        PreparedStatement pstmt;
        String sql;

        try {
            // On lit le contenu du fichier dans un tableau de bytes
            byte[] vidBytes = Files.readAllBytes(synop.toPath());

            // On prépare la requête SQL avec un paramètre pour le tableau de bytes
            sql = "UPDATE film SET synopsis = ? WHERE id_film = ?";
            pstmt = conn.prepareStatement(sql);

            // On affecte le paramètre avec le tableau de bytes
            pstmt.setBytes(1, vidBytes);
            pstmt.setLong(2, film.getId());

            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
            return false;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }
    public static boolean deleteFilm_actsec(Film film,Actor act) {
        PreparedStatement pstmt = null;


        String sql;

        // String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (3, 'Jesser')";
        //String sql="select id_film from Acteurprinc_Film where Acteurprinc_Film.id_act="+Long.toString(idact);

        try{sql = "delete FROM acteursec_film WHERE id_film = ? and id_act=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,film.getId());
            pstmt.setLong(2,act.getID());

            pstmt.executeUpdate();
            return true;

        }catch (Exception e){
            System.out.println("acteur n'exite pas");
            return false;
        }



    }
    public static boolean deleteFilm_actprinc(Film film,Actor act) {
        PreparedStatement pstmt = null;


        String sql;

        // String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (3, 'Jesser')";
        //String sql="select id_film from Acteurprinc_Film where Acteurprinc_Film.id_act="+Long.toString(idact);
        try{sql = "delete FROM acteurprinc_film WHERE id_film = ? and id_act=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,film.getId());
            pstmt.setLong(2,act.getID());

            pstmt.executeUpdate();
            return true;

        }catch (Exception e){
            return false;
        }




    }
    public static boolean ajoutFilm_actprinc(Film film,Actor act) {
        PreparedStatement pstmt = null;


        String sql;

        // String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (3, 'Jesser')";
        //String sql="select id_film from Acteurprinc_Film where Acteurprinc_Film.id_act="+Long.toString(idact);
        try {
            sql = "INSERT INTO acteurprinc_film (id_act,id_film)" +
                    " VALUES (?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, act.getID());

            pstmt.setLong(2, film.getId());

            pstmt.executeUpdate();
            return true;

        } catch (Exception e) {
            return false;
        }


    }
    public static boolean ajoutFilm_actsec(Film film,Actor act) {
        PreparedStatement pstmt = null;


        String sql;

        // String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (3, 'Jesser')";
        //String sql="select id_film from Acteurprinc_Film where Acteurprinc_Film.id_act="+Long.toString(idact);
        try {
            sql = "INSERT INTO acteursec_film (id_act,id_film)" +
                    " VALUES (?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, act.getID());
            pstmt.setLong(2, film.getId());


            pstmt.executeUpdate();
            return true;

        } catch (Exception e) {
            return false;
        }


    }


    public static List<Film> searchFilm(List<String> searchTerms) throws SQLException, IOException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM FILM WHERE ");
        List<Film> list = new ArrayList<>();
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
            Long id=rs.getLong(1);
            String nom=rs.getString(2);
            String decription= rs.getString(3);
            Date annerdesortie=rs.getDate(4);
            String langue=rs.getString(5);
            String paysorigine=rs.getString(6);
            String listegenre= rs.getString(7);
            Blob img=rs.getBlob(8);
            String duree=rs.getString(9);
            Long vunbr=rs.getLong(10);
            Long score=rs.getLong(11);
            Long vote=rs.getLong(12);
            InputStream image=img.getBinaryStream();
            InputStream synop=rs.getBinaryStream(13);
            InputStream vedio=rs.getBinaryStream(14);
            Long idrealisateur=rs.getLong(15);

            //Converting Blob Image to Jpeg File
            File fileImg = new File("src/main/java/Temp/ImgFilm"+id+".jpeg");
            OutputStream outS = new FileOutputStream(fileImg);
            byte[] bufferImg = new byte[1024];
            int length;
            while ((length = image.read(bufferImg)) != -1) {
                outS.write(bufferImg, 0, length);
            }

            //Handeling the Video, from inputStream
            Path outputFilePath = Paths.get("src/main/java/Temp/SynopsisFilm"+id+".mp4");
            try (OutputStream outputStream = Files.newOutputStream(outputFilePath)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = synop.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                System.out.println("Error Handelling the video");
            }
            //Handeling the Video, from inputStream
            Path outputFilePath1 = Paths.get("src/main/java/Temp/VideoFilm"+id+".mp4");
            try (OutputStream outputStream1 = Files.newOutputStream(outputFilePath1)) {
                byte[] bufferved = new byte[4096];
                int bytesReadved;
                while ((bytesReadved = vedio.read(bufferved)) != -1) {
                    outputStream1.write(bufferved, 0, bytesReadved);
                }
            } catch (IOException e) {
                System.out.println("Error Handelling the video");
            }

            File filesynop = new File("src/main/java/Temp/SynopsisFilm"+ id +".mp4");
            File fileImage = new File("src/main/java/Temp/ImgFilm"+id+".jpeg");
            File filmvedio=new File("src/main/java/Temp/VideoFilm"+ id +".mp4");


            ArrayList<String> genrelist = new ArrayList<>();
            String[] array = listegenre.split(",");
            for (String s : array) {
                genrelist.add(s);
            }


            Film filmm=new Film(nom,decription,annerdesortie.toLocalDate(),langue,paysorigine,genrelist,fileImage,duree,vunbr,score,vote,filesynop,filmvedio,idrealisateur);
            list.add(filmm);

        }

        // Close the ResultSet, PreparedStatement, and database connection
        rs.close();
//        stmt.close();
//        conn.close();
        return list;
    }



    public static List<Film> searchFilmOR(List<String> searchTerms) throws SQLException, IOException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM FILM WHERE ");
        List<Film> list = new ArrayList<>();
        for (int i = 0; i < searchTerms.size(); i++) {
            if (i > 0) {
                sqlBuilder.append(" OR ");
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
            Long id=rs.getLong(1);
            String nom=rs.getString(2);
            String decription= rs.getString(3);
            Date annerdesortie=rs.getDate(4);
            String langue=rs.getString(5);
            String paysorigine=rs.getString(6);
            String listegenre= rs.getString(7);
            Blob img=rs.getBlob(8);
            String duree=rs.getString(9);
            Long vunbr=rs.getLong(10);
            Long score=rs.getLong(11);
            Long vote=rs.getLong(12);
            InputStream image=img.getBinaryStream();
            InputStream synop=rs.getBinaryStream(13);
            InputStream vedio=rs.getBinaryStream(14);
            Long idrealisateur=rs.getLong(15);

            //Converting Blob Image to Jpeg File
            File fileImg = new File("src/main/java/Temp/ImgFilm"+id+".jpeg");
            OutputStream outS = new FileOutputStream(fileImg);
            byte[] bufferImg = new byte[1024];
            int length;
            while ((length = image.read(bufferImg)) != -1) {
                outS.write(bufferImg, 0, length);
            }

            //Handeling the Video, from inputStream
            Path outputFilePath = Paths.get("src/main/java/Temp/SynopsisFilm"+id+".mp4");
            try (OutputStream outputStream = Files.newOutputStream(outputFilePath)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = synop.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                System.out.println("Error Handelling the video");
            }
            //Handeling the Video, from inputStream
            Path outputFilePath1 = Paths.get("src/main/java/Temp/VideoFilm"+id+".mp4");
            try (OutputStream outputStream1 = Files.newOutputStream(outputFilePath1)) {
                byte[] bufferved = new byte[4096];
                int bytesReadved;
                while ((bytesReadved = vedio.read(bufferved)) != -1) {
                    outputStream1.write(bufferved, 0, bytesReadved);
                }
            } catch (IOException e) {
                System.out.println("Error Handelling the video");
            }

            File filesynop = new File("src/main/java/Temp/SynopsisFilm"+ id +".mp4");
            File fileImage = new File("src/main/java/Temp/ImgFilm"+id+".jpeg");
            File filmvedio=new File("src/main/java/Temp/VideoFilm"+ id +".mp4");


            ArrayList<String> genrelist = new ArrayList<>();
            String[] array = listegenre.split(",");
            for (String s : array) {
                genrelist.add(s);
            }


            Film filmm=new Film(nom,decription,annerdesortie.toLocalDate(),langue,paysorigine,genrelist,fileImage,duree,vunbr,score,vote,filesynop,filmvedio,idrealisateur);
            list.add(filmm);

        }

        // Close the ResultSet, PreparedStatement, and database connection
        rs.close();
//        stmt.close();
//        conn.close();
        return list;
    }
    public static List<Film> getMostRecentFilm(int numSeries) throws SQLException, IOException {
        List<Film> List = new ArrayList<>();
        String sql = "SELECT * FROM Film ORDER BY ANNEE_SORTIE DESC FETCH FIRST ? ROWS ONLY";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, numSeries);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Long id=rs.getLong(1);
            String nom=rs.getString(2);
            String decription= rs.getString(3);
            Date annerdesortie=rs.getDate(4);
            String langue=rs.getString(5);
            String paysorigine=rs.getString(6);
            String listegenre= rs.getString(7);
            Blob img=rs.getBlob(8);
            String duree=rs.getString(9);
            Long vunbr=rs.getLong(10);
            Long score=rs.getLong(11);
            Long vote=rs.getLong(12);
            InputStream image=img.getBinaryStream();
            InputStream synop=rs.getBinaryStream(13);
            InputStream vedio=rs.getBinaryStream(14);
            Long idrealisateur=rs.getLong(15);

            //Converting Blob Image to Jpeg File
            File fileImg = new File("src/main/java/Temp/ImgFilm"+id+".jpeg");
            OutputStream outS = new FileOutputStream(fileImg);
            byte[] bufferImg = new byte[1024];
            int length;
            while ((length = image.read(bufferImg)) != -1) {
                outS.write(bufferImg, 0, length);
            }

            //Handeling the Video, from inputStream
            Path outputFilePath = Paths.get("src/main/java/Temp/SynopsisFilm"+id+".mp4");
            try (OutputStream outputStream = Files.newOutputStream(outputFilePath)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = synop.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                System.out.println("Error Handelling the video");
            }
            //Handeling the Video, from inputStream
            Path outputFilePath1 = Paths.get("src/main/java/Temp/VideoFilm"+id+".mp4");
            try (OutputStream outputStream1 = Files.newOutputStream(outputFilePath1)) {
                byte[] bufferved = new byte[4096];
                int bytesReadved;
                while ((bytesReadved = vedio.read(bufferved)) != -1) {
                    outputStream1.write(bufferved, 0, bytesReadved);
                }
            } catch (IOException e) {
                System.out.println("Error Handelling the video");
            }

            File filesynop = new File("src/main/java/Temp/SynopsisFilm"+ id +".mp4");
            File fileImage = new File("src/main/java/Temp/ImgFilm"+id+".jpeg");
            File filmvedio=new File("src/main/java/Temp/VideoFilm"+ id +".mp4");


            ArrayList<String> genrelist = new ArrayList<>();
            String[] array = listegenre.split(",");
            for (String s : array) {
                genrelist.add(s);
            }


            Film filmm=new Film(nom,decription,annerdesortie.toLocalDate(),langue,paysorigine,genrelist,fileImage,duree,vunbr,score,vote,filesynop,filmvedio,idrealisateur);
            List.add(filmm);
        }
        return List;
    }


}
