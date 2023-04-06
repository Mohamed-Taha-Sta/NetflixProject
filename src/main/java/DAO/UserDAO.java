package DAO;

import Entities.User;
import Utils.ConxDB;
import Utils.DataHolder;

import java.io.File;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class UserDAO {

    private static final Connection conn = ConxDB.getInstance();

    public static boolean check_Mail(String mail) {
        PreparedStatement pstmt;
        String sql;
        ResultSet rs;
        try {
            sql = "SELECT * FROM Utilisateurs where MAIL=?";
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

    public static boolean authenticate(String mail, String pass) {
        PreparedStatement pstmt;
        String sql;
        ResultSet rs;

        try {
            sql = "SELECT * FROM Utilisateurs WHERE MAIL=? AND password =?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mail);
            pstmt.setString(2, pass);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("ID");
                System.out.println("User found: " + userId);

                String genreListString = rs.getString("GENRELIST");
                String[] genreArray = genreListString.split(",");
                ArrayList<String> genreList = new ArrayList<>(Arrays.asList(genreArray));

                String actorListString = rs.getString("ACTORLIST");
                String[] actorArray = actorListString.split(",");
                ArrayList<Long> actorList = new ArrayList<>();
                for (String str : actorArray) {
                    actorList.add(Long.parseLong(str));
                }

                LocalDate birthday = rs.getDate("BIRTHDAY").toLocalDate();
                LocalDate subscription = rs.getDate("SUBSCRIPTION").toLocalDate();

                Blob episodeImageB = rs.getBlob("image");
                String imagePath = "src/main/resources/Images/image_" + userId + ".png";
                File imageFile = new File(imagePath);

                if (episodeImageB != null && !imageFile.exists()) {
                    System.out.println("Image file not found: " + imagePath);
                }

                User user = new User(userId, rs.getString("Last_Name"), rs.getString("First_Name"), mail, pass, birthday, actorList, genreList, subscription, imageFile);
                DataHolder.setUser(user);
                return true;
            } else {
                System.out.println("User not found");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error authenticating user: " + e.toString());
            return false;
        }
    }


    public static boolean ajout_User(User user) {
        PreparedStatement pstmt;
        String sql;
        ResultSet rs;
        LocalDate today = LocalDate.now();

        try {
            sql = "SELECT MAX(id) FROM Utilisateurs";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            int maxID = 0;
            if (rs.next()) {
                maxID = rs.getInt(1);
            }
            int newID = maxID + 1;

            sql = "INSERT INTO Utilisateurs (id,Last_name,first_name,birthday,actorlist,genrelist,password,mail,subscription) VALUES (?,?,?,?,?,?,?,?,?)";
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
            pstmt.setDate(9, java.sql.Date.valueOf(today));
            pstmt.executeUpdate();
            authenticate(user.getMail(), user.getPassword());
            System.out.println("exucuted correctly");
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }


}

