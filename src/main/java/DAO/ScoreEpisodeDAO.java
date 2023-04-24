package DAO;

import Entities.Episode;
import Entities.User;
import Utils.ConxDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScoreEpisodeDAO {

    private static final Connection conn = ConxDB.getInstance();

    //checks if the user have rated the episode
    public static boolean Score_Exist(Episode episode, User user) {
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs;

        try {
            sql = "Select * from score_episode where id_episode=? and id_user=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, episode.getID());
            pstmt.setLong(2, user.getID());
            rs = pstmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    //returns the user score
    public static double RetrieveUserScore(Episode episode, User user) {
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs;

        try {
            sql = "Select * from score_episode where id_episode=? and id_user=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, episode.getID());
            pstmt.setLong(2, user.getID());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(3);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    //adds the score by the user
    public static boolean Add_Score(Episode episode, User user, double score) {
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs;
        try {
            sql = "INSERT INTO score_episode (id_user,id_episode,score) VALUES (?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, user.getID());
            pstmt.setLong(2, episode.getID());
            pstmt.setDouble(3, score);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //deletes the score by the user
    public static boolean Delete_Score(Episode episode, User user) {
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs;

        try {
            sql = "DELETE FROM score_episode WHERE id_user = ? AND id_episode = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, user.getID());
            pstmt.setLong(2, episode.getID());
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //update the score by the user
    public static boolean Update_Score(Episode episode, User user, double score) {
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs;

        try {
            sql = "UPDATE score_episode SET score = ? WHERE id_user = ? AND id_episode = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, score);
            pstmt.setLong(2, user.getID());
            pstmt.setLong(3, episode.getID());
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //return average EPISODE  score by all users
    public static double GetEpisodeScore(Episode episode) {
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs;
        try {
            sql = "SELECT AVG(score) FROM score_episode WHERE id_episode = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, episode.getID());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public static int GetNumberVotesEpisode(Episode episode) {
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs = null;
        int count = 0;

        try {
            sql = "SELECT COUNT(*) FROM score_episode WHERE id_episode = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, episode.getID());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
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
