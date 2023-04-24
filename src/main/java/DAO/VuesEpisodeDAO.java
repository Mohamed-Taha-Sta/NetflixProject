package DAO;

import Entities.Episode;
import Entities.User;
import Utils.ConxDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VuesEpisodeDAO {

    private static final Connection conn = ConxDB.getInstance();

    public static boolean Vue_Exist(Episode episode , User user){
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs = null;

        try {
            sql = "Select * from vues_episode where id_episode=? and id_user=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, episode.getID());
            pstmt.setLong(2, user.getID());
            rs = pstmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean Add_Vues(Episode episode, User user) {
        PreparedStatement pstmt = null;
        String sql;
        try {
            sql = "INSERT INTO vues_episode (id_user,id_episode) VALUES (?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, user.getID());
            pstmt.setLong(2, episode.getID());
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static int GetEpisodeVues(Episode episode){
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String sql;
        int count=0;
        try{
            sql="Select Count(*) from vues_episode where id_episode=?";
            pstmt=conn.prepareStatement(sql);
            pstmt.setLong(1, episode.getID());
            rs = pstmt.executeQuery();
            if(rs.next()){
                count = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return count;

    }
}
