package DAO;

import Entities.Admin;
import Utils.ConxDB;
import Utils.DataHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
    private static final Connection conn = ConxDB.getInstance();


    public static void modifnom(Long id, String nom) {

        PreparedStatement pstmt = null;
        String sql = "UPDATE Admin_app SET nom = ? WHERE id_admin = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nom);
            pstmt.setLong(2, id);
            pstmt.executeUpdate();
        } catch (Exception e) {

        }
    }

    public static void modifprenom(Long id, String prenom) {

        PreparedStatement pstmt = null;
        String sql = "UPDATE Admin_app SET prenom = ? WHERE id_admin = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, prenom);
            pstmt.setLong(2, id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing ResultSet and PreparedStatement: " + e.toString());
            }}
    }

    public static void modifmail(Long id, String mail) {

        PreparedStatement pstmt = null;
        String sql = "UPDATE Admin_app SET mail = ? WHERE id_admin = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mail);
            pstmt.setLong(2, id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing ResultSet and PreparedStatement: " + e.toString());
            }
        }
    }

    public static void modifpass(Long id, String pass) {

        PreparedStatement pstmt = null;
        String sql = "UPDATE Admin_app SET password = ? WHERE id_admin = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pass);
            pstmt.setLong(2, id);

            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing ResultSet and PreparedStatement: " + e.toString());
            }
        }
    }


    public static boolean authenticate(String mail, String pass) {
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs = null;

        try {
            sql = "SELECT * FROM Admin_app WHERE MAIL=? AND PASSWORD =?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mail);
            pstmt.setString(2, pass);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                long idAdmin = rs.getInt("ID_admin");
                String Nom = rs.getString("NOM");
                String Prenom = rs.getString("PRENOM");

                Admin admin = new Admin(idAdmin, Nom, Prenom, mail, pass);

                DataHolder.setAdmin(admin);
                return true;
            } else {
                System.out.println("Admin not found");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error authenticating user Admin: " + e.toString());
            return false;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing ResultSet and PreparedStatement: " + e.toString());
            }
        }
    }

    public static boolean check_Mail(String mail) {
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs = null;
        try {
            sql = "SELECT * FROM Admin_app where MAIL=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mail);
            rs = pstmt.executeQuery();
            if (!rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing ResultSet and PreparedStatement: " + e.toString());
            }

        }

    }
}
