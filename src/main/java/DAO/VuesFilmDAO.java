package DAO;

import Entities.Film;
import Entities.User;
import Utils.ConxDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VuesFilmDAO {

    private static final Connection conn = ConxDB.getInstance();

    public static boolean Vue_Exist(Film film , User user){
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs = null;

        try {
            sql = "Select * from vues_film where id_film=? and id_user=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, film.getId());
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

    public static boolean Add_Vues(Film film, User user) {
        PreparedStatement pstmt = null;
        String sql;
        try {
            sql = "INSERT INTO vues_film (id_user,id_film) VALUES (?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, user.getID());
            pstmt.setLong(2, film.getId());
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

    public static int GetFilmVues(Film film){
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String sql;
        int count=0;
        try{
            sql="Select Count(*) from vues_film where id_film=?";
            pstmt=conn.prepareStatement(sql);
            pstmt.setLong(1, film.getId());
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
