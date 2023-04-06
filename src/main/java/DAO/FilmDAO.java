package DAO;

import Entities.Actor;
import Entities.Content;
import Entities.Film;
import Entities.Synopsis;
import Utils.ConxDB;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO {

    private static final Connection conn = ConxDB.getInstance();

    public static boolean ajout_film(Film film) {
        boolean etat = true;
        PreparedStatement pstmt = null;
        java.sql.Time sqlTime = java.sql.Time.valueOf(film.getDuree());
        ResultSet rs;
        String sql3;

        /*    public Film(String nom, String realisateur, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre, File img, LocalTime duree, ArrayList<Actor> acteur, , long vueNbr, long score, long votes, File file, File synopsis, File film) {
         */
        try {
           // String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (3, 'Jesser')";
            String genreListString = String.join(",", film.getListegenre().stream().map(Object::toString).toArray(String[]::new));
            System.out.println(1);
            String sql = "INSERT INTO Film (nom,realisateur,annerdesortie,langue,paysorigine,listegenre,img,vuenbr,score,vote,synopsis,film) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            String sql1="select id_film from Film where Film.nom="+film.getNom()+"and Film.realisateur="+film.getRealisateur();
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
            //pstmt.setString(8,film.getDuree().toString());
            pstmt.setLong(8,film.getVueNbr());
            pstmt.setLong(9,film.getScore());
            pstmt.setLong(10,film.getVotes());
            pstmt.setBlob(11,inputStreamSynopsisfilm);
            pstmt.setBlob(12,inputStreamSynopsissynops);



            System.out.println(2);

            System.out.println(3);

            pstmt.executeUpdate();
            System.out.println(4);


            pstmt = conn.prepareStatement(sql1);

            pstmt.executeQuery();
            rs = pstmt.executeQuery();
            int idfilm=rs.getInt(1);
            ArrayList<Actor> act=film.getActeur();
            for(int i=0;i<act.size();i++){
                sql3 = "INSERT INTO Acteurprinc_Film (id_act,id_film) VALUES ("+Long.toString(act.get(i).getID())+","+Integer.toString(idfilm)+")";
                pstmt = conn.prepareStatement(sql3);

                pstmt.executeUpdate();
                ActorDAO.ajout_acteur(act.get(i));

            }

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
            etat=false;
        } catch (FileNotFoundException e) {

        }
        return etat;
    }

    public static List<String> recherche_film(String filmname) {
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


    }
    public static List<String> recherche_film(Long filmid) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<String>list=new ArrayList<>();
        try {
            // String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (3, 'Jesser')";
            String sql="select id_film,nom,realisateur,annerdesortie,langue,paysorigine,duree from Film where Film.id_film="+filmid;
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


}
