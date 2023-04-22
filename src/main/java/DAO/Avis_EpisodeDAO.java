package DAO;

import Entities.Episode;
import Entities.Serie;
import Entities.User;
import Utils.ConxDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Avis_EpisodeDAO {

    private static final Connection conn = ConxDB.getInstance();

    public static boolean add_avis(Episode ep, User user, String avis){
        PreparedStatement pstmt = null;
        String sql;


        try {
            sql = "INSERT INTO avis_episodes (id_user,id_episode,avis) VALUES (?,?,?)";


            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,user.getID() );
            pstmt.setLong(2, ep.getID());

            pstmt.setString(3, avis);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("tu peut mettre un seul commentaire");
            return false;
        }

    }
    public static boolean modif_avis(Episode ep, User user, String avis){
        PreparedStatement pstmt = null;
        String sql;


        try {
            sql = "UPDATE avis_episodes SET avis = ? WHERE id_user = ? AND id_episode = ?";


            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(2,user.getID() );
            pstmt.setLong(3, ep.getID());
            pstmt.setString(1, avis);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("tu dois avoid un commentaire pour le modifier");
            return false;
        }

    }

    public static boolean delete_avis(Episode ep, User user){
        PreparedStatement pstmt = null;
        String sql;


        try {
            sql = "DELETE FROM avis_episodes WHERE id_user = ? AND id_episode = ?";


            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,user.getID() );
            pstmt.setLong(2, ep.getID());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("tu dois avoid un commentaire pour le supprimer");
            return false;
        }

    }
    public static String affiche_avis(Episode ep, User user){
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs;

        try {
            sql = "SELECT avis FROM avis_episodes WHERE id_serie = ? and id_episode=?";


            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(2,user.getID() );
            pstmt.setLong(1, ep.getID());
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
