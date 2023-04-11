package DAO;

import Entities.Film;
import Entities.MainActor;
import Entities.Producer;
import Utils.ConxDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProducerDAO {
    private static final Connection conn = ConxDB.getInstance();

    public static  void createprod(Producer prod){
        boolean etat = true;
        PreparedStatement pstmt = null;
        String sql;
        Long compteur = prod.getId();

        try {
             sql = "INSERT INTO Producer (nom,prenom,mail,password) VALUES (?,?,?,?)";



            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, prod.getNom());
            pstmt.setString(2, prod.getPrenom());
            pstmt.setString(3, prod.getEmail());
            pstmt.setString(4, prod.getpassword());
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            etat = false;
        }

    }
    public static void ajoutFilm(Film film){
        FilmDAO.Add(film);
    }
    public static void modifnom(Long id,String nom){
        /**/
        PreparedStatement pstmt = null;
        String sql = "UPDATE producer SET nom = '"+nom+"' WHERE id_prod = "+id;
        try {


            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        }catch (Exception e){

        }
    }
    public static void modifprenom(Long id,String prenom){
        /**/
        PreparedStatement pstmt = null;
        String sql = "UPDATE producer SET prenom = '"+prenom+"' WHERE id_prod = "+id;
        try {


            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        }catch (Exception e){

        }
    }
    public static void modifmail(Long id,String mail){
        /**/
        PreparedStatement pstmt = null;
        String sql = "UPDATE producer SET mail = '"+mail+"' WHERE id_prod = "+id;
        try {


            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        }catch (Exception e){

        }
    }
    public static void modifpassword(Long id,String password){
        /**/
        PreparedStatement pstmt = null;
        String sql = "UPDATE producer SET password = '"+password+"' WHERE id_prod = "+id;
        try {


            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        }catch (Exception e){

        }
    }







}
