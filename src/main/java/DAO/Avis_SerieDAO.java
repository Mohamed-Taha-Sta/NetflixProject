package DAO;

import Entities.Film;
import Entities.Serie;
import Entities.User;
import Utils.ConxDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Avis_SerieDAO {

    private static final Connection conn = ConxDB.getInstance();


    public static boolean Avis_Exist(Serie serie, User user){
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs = null;
        try{
            sql="Select * from AVIS_SERIE where ID_USER=? and ID_SERIE=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, (int) user.getID());
            pstmt.setInt(2, (int) serie.getId());
            rs = pstmt.executeQuery();
            return rs.next();
        }catch (Exception e){
            System.out.println("Error searching for avis serie");
            return false;
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

    public static boolean add_avis(Serie serie, User user, String avis){
        PreparedStatement pstmt = null;
        String sql;


        try {
            sql = "INSERT INTO AVIS_SERIE (id_user,id_serie,avis) VALUES (?,?,?)";


            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,user.getID() );
            pstmt.setLong(2, serie.getId());

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
    public static boolean modif_avis(Serie serie, User user, String avis){
        PreparedStatement pstmt = null;
        String sql;


        try {
            sql = "UPDATE AVIS_SERIE SET avis = ? WHERE id_user = ? AND id_serie = ?";


            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(2,user.getID() );
            pstmt.setLong(3, serie.getId());
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

    public static boolean delete_avis(Serie serie, User user){
        PreparedStatement pstmt = null;
        String sql;


        try {
            sql = "DELETE FROM AVIS_SERIE WHERE id_user = ? AND id_serie = ?";


            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,user.getID() );
            pstmt.setLong(2, serie.getId());
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
    public static String affiche_avis(Serie serie, User user){
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs = null;

        try {
            sql = "SELECT avis FROM AVIS_SERIE WHERE id_serie = ? and id_user=?";


            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(2,user.getID() );
            pstmt.setLong(1, serie.getId());
            rs=pstmt.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException ex) {
            System.out.println("tu dois avoid un commentaire pour le supprimer");
            String s="tu dois avoid un commentaire pour le supprimer";
            return s;
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
    public static List<String> FindAvisAllSerie(Serie serie){
        PreparedStatement pstmt = null;
        String sql;
        List<String> avisList = new ArrayList<>();
        ResultSet rs = null;

        try {
            sql = "SELECT avis FROM AVIS_SERIE WHERE id_serie = ? ";


            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, serie.getId());
            rs=pstmt.executeQuery();
            while (rs.next())
            {
                avisList.add(rs.getString(1));
            }
            return avisList;
        } catch (SQLException ex) {
            System.out.println("tu dois avoid un commentaire pour le supprimer");
            return avisList;
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
