package DAO;

import Entities.*;
import Utils.ConxDB;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

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
            String sql = "INSERT INTO Film (nom,realisateur,annerdesortie,langue,paysorigine,listegenre,img,duree,vuenbr,score,vote,synopsis,film,resume)" +
                    " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            String sql1 = "SELECT id_film FROM Film WHERE Film.nom='" + film.getNom() + "' AND Film.realisateur='" + film.getRealisateur() + "'";
            pstmt = conn.prepareStatement(sql);

            InputStream inputStreamSynopsisimg = null;
            InputStream inputStreamSynopsissynops = null;
            InputStream inputStreamSynopsisfilm= null;

            inputStreamSynopsisimg = new FileInputStream(film.getImg());
            inputStreamSynopsisfilm=new FileInputStream(film.getFilm());
            inputStreamSynopsissynops=new FileInputStream(film.getSynopsis());

            pstmt.setString(1,film.getNom());
            pstmt.setString(2,film.getRealisateur());
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
            pstmt.setString(14,film.getResume());

            pstmt.executeUpdate();

            pstmt = conn.prepareStatement(sql1);

            rs = pstmt.executeQuery();
            rs.next();

            int idfilm=rs.getInt(1);

            List<Actor> act=film.getActeur();

            for(int i=0;i<act.size();i++){
               // sql3 = "INSERT INTO Acteurprinc_Film (id_act, id_film) VALUES ('" + Long.toString(act.get(i).getID()) + "', '" + Integer.toString(idfilm) + "')";
              //  sql3 = "INSERT INTO acteursec_film (id_act, id_film) VALUES ('" + Long.toString(act.get(i).getID()) + "', '" + Integer.toString(idfilm) + "')";


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
            String sql="select id_film,nom,realisateur,annerdesortie,langue,paysorigine,listegenre,img,duree,vuenbr,score,vote,synopsis,film,resume from Film where Film.id_film="+filmid;
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                Long id=rs.getLong(1);
                String nom=rs.getString(2);
                String realisateur= rs.getString(3);
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
                String resume=rs.getString(15);
                //Converting Blob Image to Jpeg File
                File fileImg = new File("src/main/java/Test/ImgFilm"+id+".jpeg");
                OutputStream outS = new FileOutputStream(fileImg);
                byte[] bufferImg = new byte[1024];
                int length;
                while ((length = image.read(bufferImg)) != -1) {
                    outS.write(bufferImg, 0, length);
                }

                //Handeling the Video, from inputStream
                Path outputFilePath = Paths.get("src/main/java/Test/VideoSynopsis"+id+".mp4");
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
                Path outputFilePath1 = Paths.get("src/main/java/Test/VideoFilm"+id+".mp4");
                try (OutputStream outputStream1 = Files.newOutputStream(outputFilePath1)) {
                    byte[] bufferved = new byte[4096];
                    int bytesReadved;
                    while ((bytesReadved = vedio.read(bufferved)) != -1) {
                        outputStream1.write(bufferved, 0, bytesReadved);
                    }
                } catch (IOException e) {
                    System.out.println("Error Handelling the video");
                }

                File filesynop = new File("src/main/java/Test/SynopsisFilm"+ id +".mp4");
                File fileImage = new File("src/main/java/Test/ImgFilm"+ id +".jpeg");
                File filmvedio=new File("src/main/java/Test/VideoFilm"+ id +".mp4");


                ArrayList<String> genrelist = new ArrayList<>();
                String[] array = listegenre.split(",");
                for (String s : array) {
                    genrelist.add(s);
                }


                Film filmm=new Film(nom,realisateur,annerdesortie.toLocalDate(),langue,paysorigine,genrelist,fileImage,duree,vunbr,score,vote,filesynop,filmvedio,resume);
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
            String sql="SELECT id_film, nom, realisateur, annerdesortie, langue, paysorigine, listegenre, img, duree, vuenbr, score, vote, synopsis, film,resume " +
                    "FROM Film " +
                    "WHERE Film.nom LIKE '%" + filmnom + "%'";
            pstmt = conn.prepareStatement(sql);


            rs = pstmt.executeQuery();
            while (rs.next()) {
                Long id=rs.getLong(1);
                String nom=rs.getString(2);
                String realisateur= rs.getString(3);
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
                String resume=rs.getString(15);

                //Converting Blob Image to Jpeg File
                File fileImg = new File("src/main/java/Test/ImgFilm"+id+".jpeg");
                OutputStream outS = new FileOutputStream(fileImg);
                byte[] bufferImg = new byte[1024];
                int length;
                while ((length = image.read(bufferImg)) != -1) {
                    outS.write(bufferImg, 0, length);
                }

                //Handeling the Video, from inputStream
                Path outputFilePath = Paths.get("src/main/java/Test/SynopsisFilm"+id+".mp4");
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
                Path outputFilePath1 = Paths.get("src/main/java/Test/VideoFilm"+id+".mp4");
                try (OutputStream outputStream1 = Files.newOutputStream(outputFilePath1)) {
                    byte[] bufferved = new byte[4096];
                    int bytesReadved;
                    while ((bytesReadved = vedio.read(bufferved)) != -1) {
                        outputStream1.write(bufferved, 0, bytesReadved);
                    }
                } catch (IOException e) {
                    System.out.println("Error Handelling the video");
                }

                File filesynop = new File("src/main/java/Test/SynopsisFilm"+ id +".mp4");
                File fileImage = new File("src/main/java/Test/ImgFilm"+id+".jpeg");
                File filmvedio=new File("src/main/java/Test/VideoFilm"+ id +".mp4");


                ArrayList<String> genrelist = new ArrayList<>();
                String[] array = listegenre.split(",");
                for (String s : array) {
                    genrelist.add(s);
                }


                Film filmm=new Film(nom,realisateur,annerdesortie.toLocalDate(),langue,paysorigine,genrelist,fileImage,duree,vunbr,score,vote,filesynop,filmvedio,resume);
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

}
