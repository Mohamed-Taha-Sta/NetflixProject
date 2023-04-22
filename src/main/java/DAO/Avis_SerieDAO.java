package DAO;

import Entities.Film;
import Entities.Serie;
import Entities.User;
import Utils.ConxDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Avis_SerieDAO {

    private static final Connection conn = ConxDB.getInstance();

    public static boolean add_avis(Serie serie, User user, String avis){
        PreparedStatement pstmt = null;
        String sql;


        try {
            sql = "INSERT INTO avis_serie (id_user,id_serie,avis) VALUES (?,?,?)";


            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,user.getID() );
            pstmt.setLong(2, serie.getId());

            pstmt.setString(3, avis);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("tu peut mettre un seul commentaire");
            return false;
        }

    }
    public static boolean modif_avis(Serie serie, User user, String avis){
        PreparedStatement pstmt = null;
        String sql;


        try {
            sql = "UPDATE avis_serie SET avis = ? WHERE id_user = ? AND id_serie = ?";


            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(2,user.getID() );
            pstmt.setLong(3, serie.getId());
            pstmt.setString(1, avis);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("tu dois avoid un commentaire pour le modifier");
            return false;
        }

    }

    public static boolean delete_avis(Serie serie, User user){
        PreparedStatement pstmt = null;
        String sql;


        try {
            sql = "DELETE FROM avis_film WHERE id_user = ? AND id_film = ?";


            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,user.getID() );
            pstmt.setLong(2, serie.getId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("tu dois avoid un commentaire pour le supprimer");
            return false;
        }

    }    public static String affiche_avis(Film film, User user){
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs;

        try {
            sql = "SELECT avis FROM avis_film WHERE id_film = ? and id_user=?";


            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(2,user.getID() );
            pstmt.setLong(1, film.getId());
            rs=pstmt.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException ex) {
            System.out.println("tu dois avoid un commentaire pour le supprimer");
            String s="tu dois avoid un commentaire pour le supprimer";
            return s;
        }

    }


}
