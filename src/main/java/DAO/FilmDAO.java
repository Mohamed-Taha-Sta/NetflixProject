package DAO;

import Entities.Actor;
import Entities.Film;
import Utils.ConxDB;

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
        try {
           // String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (3, 'Jesser')";

            String sql = "INSERT INTO Film (id_film,nom,realisateur,annerdesortie,langue,paysorigine,duree,media) VALUES ("+Long.toString(film.getId())+","+film.getNom()+","+film.getRealisateur()+","+film.getAnnerdesortie().toString()+","+film.toString()+","+film.getPaysorigine()+","+"0 "+film.getDuree().toString()+"null"+")";
            String sql1="select id_film from Film where Film.nom="+film.getNom()+"and Film.realisateur="+film.getRealisateur();

            pstmt = conn.prepareStatement(sql);

            pstmt.executeUpdate();


            pstmt = conn.prepareStatement(sql1);

            pstmt.executeQuery();
            rs = pstmt.getGeneratedKeys();
            int idfilm=rs.getInt(1);
            ArrayList<Actor> act=film.getActeur();
            for(int i=0;i<act.size();i++){
                sql3 = "INSERT INTO relation (id_act,id_film) VALUES ("+Long.toString(act.get(i).getID())+","+Integer.toString(idfilm)+")";
                pstmt = conn.prepareStatement(sql);

                pstmt.executeUpdate();
                ActorDAO.ajout_acteur(act.get(i));

            }

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
            etat=false;
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
