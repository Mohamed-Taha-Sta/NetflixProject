package DAO;

import Entities.User;
import Utils.ConxDB;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDAO {

    private static final Connection conn = ConxDB.getInstance();

    public static boolean ajout_User(User user) {
        PreparedStatement pstmt = null;
        String sql;
        try {
            sql = "INSERT INTO users (nom,prenome,birthday,actorlist,genrelist,password,mail) VALUES (?,?,?,?,?,?,?)";
            pstmt =conn.prepareStatement(sql);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPrename());
            pstmt.setDate(3, java.sql.Date.valueOf(user.getBirthday()));
            String actorListString = String.join(",", user.getActorsList().stream().map(Object::toString).toArray(String[]::new));
            pstmt.setString(4,actorListString);
            String genreListString = String.join(",", user.getGenreList().stream().map(Object::toString).toArray(String[]::new));
            pstmt.setString(5,genreListString);
            pstmt.setString(6, user.getPassword());
            pstmt.setString(7, user.getMail());
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }


    }
}
