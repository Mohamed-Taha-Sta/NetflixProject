package DAO;

import Entities.Actor;
import Entities.Film;
import Entities.User;
import Utils.ConxDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Avis_FilmDAO {
    private static final Connection conn = ConxDB.getInstance();


    public static boolean Avis_Exist(Film film, User user){
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs;
        try{
            sql="Select * from avis_film where ID_USER=? and ID_FILM=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, (int) user.getID());
            pstmt.setInt(2, (int) film.getId());
            rs = pstmt.executeQuery();
            return rs.next();
        }catch (Exception e){
            System.out.println("Error searching for avis film");
            return false;
        }

    }

    public static boolean add_avis(Film film, User user, String avis){
        PreparedStatement pstmt = null;
        String sql;


        try {
            sql = "INSERT INTO avis_film (id_user,id_film,avis) VALUES (?,?,?)";


            pstmt = conn.prepareStatement(sql);
            System.out.println(user.getID());
            pstmt.setLong(1,user.getID() );
            System.out.println(film.getId());
            pstmt.setLong(2, film.getId());
            System.out.println(99);

            pstmt.setString(3, avis);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("tu peut mettre un seul commentaire");
            return false;
        }

    }
    public static boolean modif_avis(Film film, User user, String avis){
        PreparedStatement pstmt = null;
        String sql;


        try {
            sql = "UPDATE avis_film SET avis = ? WHERE id_user = ? AND id_film = ?";


            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(2,user.getID() );
            pstmt.setLong(3, film.getId());
            pstmt.setString(1, avis);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("tu dois avoid un commentaire pour le modifier");
            return false;
        }

    }

    public static boolean delete_avis(Film film, User user){
        PreparedStatement pstmt = null;
        String sql;


        try {
            sql = "DELETE FROM avis_film WHERE id_user = ? AND id_film = ?";


            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,user.getID() );
            pstmt.setLong(2, film.getId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("tu dois avoid un commentaire pour le supprimer");
            return false;
        }

    }
    public static String affiche_avis(Film film, User user){
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
