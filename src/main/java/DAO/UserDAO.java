package DAO;

import Entities.Episode;
import Entities.Film;
import Entities.Serie;
import Entities.User;
import Utils.ConxDB;
import Utils.DataHolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
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
            } else {
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
                System.out.println("inUserDao image:" + imageFile);
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
            if (user.getImage()== null)
                sql = "INSERT INTO Utilisateurs (id,Last_name,first_name,birthday,actorlist,genrelist,password,mail,subscription) VALUES (?,?,?,?,?,?,?,?,?)";
            else
                sql = "INSERT INTO Utilisateurs (id,Last_name,first_name,birthday,actorlist,genrelist,password,mail,subscription,IMAGE) VALUES (?,?,?,?,?,?,?,?,?,?)";
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
            if (user.getImage()!=null)
            {
                InputStream inputStream = new FileInputStream(user.getImage());
                pstmt.setBlob(10,inputStream);
            }
            pstmt.executeUpdate();
            authenticate(user.getMail(), user.getPassword());
            System.out.println("exucuted correctly");
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public static boolean changeName(String newName) {
        PreparedStatement pstmt;
        String sql;

        try {
            sql = "Update Utilisateurs set LAST_NAME=? where id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newName);
            pstmt.setInt(2, (int) DataHolder.getUser().getID());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    public static boolean changePrename(String newPreName) {
        PreparedStatement pstmt;
        String sql;

        try {
            sql = "Update Utilisateurs set FIRST_NAME=? where id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newPreName);
            pstmt.setInt(2, (int) DataHolder.getUser().getID());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    public static boolean changeMail(String newMail){
        PreparedStatement pstmt;
        String sql;

        try {
            sql = "Update Utilisateurs set MAIL=? where id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newMail);
            pstmt.setInt(2, (int) DataHolder.getUser().getID());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean changeBirthday(LocalDate date){
        PreparedStatement pstmt;
        String sql;

        try {
            sql = "Update Utilisateurs set BIRTHDAY=? where id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(date));
            pstmt.setInt(2, (int) DataHolder.getUser().getID());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean changeActors(String actors){
        PreparedStatement pstmt;
        String sql;
        try {
            sql = "Update Utilisateurs set ACTORLIST=? where id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, actors);
            pstmt.setInt(2, (int) DataHolder.getUser().getID());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public static boolean changePass(String pass){
        PreparedStatement pstmt;
        String sql;
        try {
            sql = "Update Utilisateurs set PASSWORD=? where id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pass);
            pstmt.setInt(2, (int) DataHolder.getUser().getID());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public static void adding_Image(File imageFile) {
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs;

        try {
            // Copy the selected image to the target directory
            String fileName = "image_" + DataHolder.getUser().getID() + ".png";
            File targetFile = new File("src/main/resources/Images/" + fileName);
            Files.copy(imageFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            DataHolder.setImage(targetFile);
            // Update the image in the database
            sql = "UPDATE Utilisateurs SET image=? WHERE id=?";
            InputStream inputStream = new FileInputStream(targetFile);
            pstmt = conn.prepareStatement(sql);
            pstmt.setBlob(1, inputStream);
            pstmt.setInt(2, (int) DataHolder.getUser().getID());
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Image updated successfully.");
            } else {
                System.out.println("Image update failed.");
            }
        } catch (Exception e) {
            System.out.println("Error updating image: " + e.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void retrieve_Image(int userId) {
        try {
            String sql = "SELECT image FROM Utilisateurs WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Blob blob = rs.getBlob("image");
                InputStream inputStream = blob.getBinaryStream();
                File file = new File("temp.jpg");
                Path path = Paths.get(file.getAbsolutePath());
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
                DataHolder.setImage(file);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean votepositivefilm(Film film) {
        return FilmDAO.UpdatePositiveScoreFilm(film);
    }

    public static boolean votenegativefilm(Film film) {
        return FilmDAO.UpdatenegativeScoreFilm(film);
    }

    public static boolean regarderfilm(Film film) {
        return FilmDAO.UpdatevuenbrFilm(film);
    }

    public static boolean votepositiveepisode(Episode ep) {
        try {
            return EpisodeDAO2.UpdatePositiveScoreEpisode(ep);
        } catch (SQLException E) {
            System.out.println("error dans la connection du base");
            return false;
        }
    }

    public static boolean votenegativeepisode(Episode ep) {
        try {
            return EpisodeDAO2.UpdateNegativeScoreEpisode(ep);

        } catch (SQLException E) {
            System.out.println("error dans la connection du base");
            return false;
        }
    }

    public static boolean regarederepisode(Episode ep) {
        try {
            return EpisodeDAO2.UpdateViewNbrEpisode(ep);
        } catch (SQLException E) {
            System.out.println("error dans la connection du base");
            return false;
        }
    }


}
