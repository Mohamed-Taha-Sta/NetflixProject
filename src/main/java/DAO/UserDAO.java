package DAO;

import Entities.User;
import Utils.ConxDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    private static final Connection conn = ConxDB.getInstance();

    public static boolean check_Mail(String mail) {
        PreparedStatement pstmt;
        String sql;
        ResultSet rs;
        try {
            sql = "SELECT * FROM users where MAIL=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mail);
            rs = pstmt.executeQuery();
            if (!rs.next()) {
                System.out.println("its unique");
                return true;
            } else
            {
                System.out.println("its not unique");
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }

    }

    public static boolean Authetication(String mail, String pass){
        PreparedStatement pstmt;
        String sql;
        ResultSet rs;
        try {
            sql = "SELECT * FROM users where MAIL=? AND password =?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mail);
            pstmt.setString(2,pass);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("found");
                return true;
            } else
            {
                System.out.println("not found");
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public static boolean ajout_User(User user) {
        PreparedStatement pstmt;
        String sql;
        ResultSet rs;

        try {
            sql = "SELECT MAX(id) FROM users";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            int maxID = 0;
            if (rs.next()) {
                maxID = rs.getInt(1);
            }
            int newID = maxID + 1;

            sql = "INSERT INTO users (id,nom,prenome,birthday,actorlist,genrelist,password,mail) VALUES (?,?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newID);
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPrename());
            pstmt.setDate(4, java.sql.Date.valueOf(user.getBirthday()));
            String actorListString = String.join(",", user.getActorsList().stream().map(Object::toString).toArray(String[]::new));
            pstmt.setString(5, actorListString);
            String genreListString = String.join(",", user.getGenreList().stream().map(Object::toString).toArray(String[]::new));
            pstmt.setString(6, genreListString);
            pstmt.setString(7, user.getPassword());
            pstmt.setString(8, user.getMail());
            pstmt.executeUpdate();
            System.out.println("exucuted correctly");
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }


}

