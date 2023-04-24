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

public class Avis_SeasonDAO {


    private static final Connection conn = ConxDB.getInstance();


    public static boolean Avis_Exist(Season season, User user){
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs = null;
        try{
            sql="Select * from AVIS_SAISON where ID_USER=? and ID_SAISON=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, (int) user.getID());
            pstmt.setInt(2, (int) season.getID());
            rs = pstmt.executeQuery();
            return rs.next();
        }catch (Exception e){
            System.out.println("Error searching for avis season");
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
    

    public static boolean add_avis(Season s, User user, String avis){
        PreparedStatement pstmt = null;
        String sql;


        try {
            sql = "INSERT INTO avis_saison (id_user,id_saison,avis) VALUES (?,?,?)";


            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,user.getID() );
            pstmt.setLong(2, s.getID());

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

    public static boolean modif_avis(Season s, User user, String avis){
        PreparedStatement pstmt = null;
        String sql;


        try {
            sql = "UPDATE avis_saison SET avis = ? WHERE id_user = ? AND id_saison = ?";


            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(2,user.getID() );
            pstmt.setLong(3, s.getID());
            pstmt.setString(1, avis);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("tu dois avoir un commentaire pour le modifier");
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

    public static boolean delete_avis(Season s, User user){
        PreparedStatement pstmt = null;
        String sql;


        try {
            sql = "DELETE FROM avis_saison WHERE id_user = ? AND id_saison = ?";


            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,user.getID() );
            pstmt.setLong(2, s.getID());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("tu dois avoir un commentaire pour le supprimer");
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

    
    public static String affiche_avis(Season s, User user){
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs = null;

        try {
            sql = "SELECT avis FROM avis_saison WHERE id_user = ? and id_saison=?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,user.getID() );
            pstmt.setLong(2,s.getID());
            rs=pstmt.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException ex) {
            System.out.println("tu dois avoid un commentaire pour le supprimer");
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

    public static List<String> FindAll(Season s){
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs = null;
        List<String> opinionList = new ArrayList<>();

        try {
            sql = "SELECT avis FROM avis_saison WHERE id_saison=?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,s.getID());
            rs=pstmt.executeQuery();
            while (rs.next())
                opinionList.add(rs.getString(1));
            return opinionList;
        } catch (SQLException ex) {

            return opinionList;
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
