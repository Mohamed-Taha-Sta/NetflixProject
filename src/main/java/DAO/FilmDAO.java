package DAO;

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

        try {
           // String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (3, 'Jesser')";
            String sql = "INSERT INTO Film (id,nom,realisateur,annerdesortie,langue,paysorigine,duree) VALUES ("+Long.toString(film.getId())+film.getNom()+film.getRealisateur()+film.getAnnerdesortie().toString()+film.toString()+film.getPaysorigine()+film.getDuree().toString() +")";

            pstmt = conn.prepareStatement(sql);

            pstmt.executeUpdate();

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
            etat=false;
        }
        return etat;
    }

    public static List<String> recherche_film(String filmname) {
        boolean etat = true;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<String>list=new ArrayList<>();
        try {
            // String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (3, 'Jesser')";
            String sql="select id,nom,realisateur,annerdesortie,langue,paysorigine,duree from Film where Film.nom=%"+filmname+"%";
            pstmt = conn.prepareStatement(sql);

            pstmt.executeQuery();
            rs = pstmt.getGeneratedKeys();
            list.add(rs.getString(1));
            list.add(rs.getString(2));
            list.add(rs.getString(3));
            list.add(rs.getString(4));
            list.add(rs.getString(5));
            list.add(rs.getString(6));
            list.add(rs.getString(7));

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
            etat=false;
        }


        return list;
    }



}
