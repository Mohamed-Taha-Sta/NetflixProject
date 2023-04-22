package DAO;

import Entities.Admin;
import Entities.Episode;
import Entities.Film;
import Utils.ConxDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminDAO {
    private static final Connection conn = ConxDB.getInstance();

    public static void createadmin(Admin admin) {
        boolean etat = true;
        PreparedStatement pstmt = null;
        String sql;
        Long compteur = admin.getID();

        try {
            sql = "INSERT INTO Admin (nom,prenom,mail,password) VALUES (?,?,?,?)";


            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, admin.getName());
            pstmt.setString(2, admin.getPrename());
            pstmt.setString(3, admin.getMail());
            pstmt.setString(4, admin.getPrename());
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            etat = false;
        }

    }


    public static void modifnom(Long id,String nom){
        /**/
        PreparedStatement pstmt = null;
        String sql = "UPDATE ADMIN SET nom = '"+nom+"' WHERE id_admin = "+id;
        try {


            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        }catch (Exception e){

        }
    }
    public static void modifprenom(Long id,String prenom){
        /**/
        PreparedStatement pstmt = null;
        String sql = "UPDATE ADMIN SET prenom = '"+prenom+"' WHERE id_admin = "+id;
        try {


            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        }catch (Exception e){

        }
    }

    public static void modifmail(Long id,String mail){
        /**/
        PreparedStatement pstmt = null;
        String sql = "UPDATE ADMIN SET mail = '"+mail+"' WHERE id_admin = "+id;
        try {


            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        }catch (Exception e){

        }
    }
    public static void modifpass(Long id,String pass){
        /**/
        PreparedStatement pstmt = null;
        String sql = "UPDATE ADMIN SET password = '"+pass+"' WHERE id_admin = "+id;
        try {


            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        }catch (Exception e){

        }
    }














    public static Long consult_nbrvue_film(Film film) {
        return FilmDAO.getnbrvue(film);
    }

    public static Long consult_score_film(Film film) {
        return FilmDAO.getscore(film);
    }
    public static Long consult_score_filmpourcentage(Film film) {
        return FilmDAO.getscorepourcantage(film);
    }
    public static Long consult_vote_film(Film film) {
        return FilmDAO.getvote(film);
    }
    public static Long consult_nbrvue_episode(Episode ep) {
        try{
        return EpisodeDAO2.getVotesEpisode(ep);
    }
        catch (Exception e){
        System.out.println("error dans la connection du base de donner");
        return Long.valueOf(-1);

    }
    }

    public static Long consult_score_episode(Episode ep) {
       try{ return EpisodeDAO2.getScoreEpisode(ep);
    }catch (Exception e){
            System.out.println("error dans la connection du base de donner");
            return Long.valueOf(-1);
        }
    }
   public static Long consult_score_episodepourcentage(Episode ep) {try{
      return EpisodeDAO2.getScoreEpisode(ep);
   }
   catch (Exception e){
       System.out.println("error dans la connection du base de donner");
       return Long.valueOf(-1);

   }
    }
    public static Long consult_vote_serie(Episode ep) {
        try{
        return EpisodeDAO2.getVotesEpisode(ep);}
        catch (Exception e){
            System.out.println("error dans la connection du base de donner");
            return Long.valueOf(-1);

        }
    }
}
