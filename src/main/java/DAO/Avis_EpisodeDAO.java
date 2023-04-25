package DAO;

import Entities.Episode;
import Entities.Season;
import Entities.Serie;
import Entities.User;
import Utils.ConxDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Avis_EpisodeDAO {

    private static final Connection conn = ConxDB.getInstance();



    public static boolean Avis_Exist(Episode episode, User user){
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs;
        try{
            sql="Select * from AVIS_EPISODES where ID_USER=? and ID_EPISODE=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, (int) user.getID());
            pstmt.setInt(2, (int) episode.getID());
            rs = pstmt.executeQuery();
            return rs.next();
        }catch (Exception e){
            System.out.println("Error searching for avis episode");
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
    public static String affiche_avis(Episode ep, User user){
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs = null;

        try {
            sql = "SELECT * FROM avis_episodes WHERE id_user = ? and id_episode=?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,user.getID());
            pstmt.setLong(2,ep.getID());
            rs=pstmt.executeQuery();
            rs.next();
            String avis = rs.getString("avis");
            return avis;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "";
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

    }

    public static List<String> FindAll(Episode ep){
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs = null;
        List<String> opinions = new ArrayList<>();

        try {
            sql = "SELECT avis FROM avis_episodes WHERE id_episode=?";


            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, ep.getID());
            rs=pstmt.executeQuery();
            while (rs.next())
                opinions.add(rs.getString(1));
            return opinions;
        } catch (SQLException ex) {
            return opinions;
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

    }


}
