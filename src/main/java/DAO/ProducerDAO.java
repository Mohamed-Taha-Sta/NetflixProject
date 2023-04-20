package DAO;

import Entities.Actor;
import Entities.Film;
import Entities.Producer;
import Utils.ConxDB;
import Utils.DataHolder;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ProducerDAO {
    private static final Connection conn = ConxDB.getInstance();

    public static  void createprod(Producer prod){
        boolean etat = true;
        PreparedStatement pstmt = null;
        String sql;
        Long compteur = prod.getId();

        try {
             sql = "INSERT INTO Producer (NOM,PRENOM,EMAIL,PASSWORD) VALUES (?,?,?,?)";



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
    public static void deleteFilm(Film film){
        FilmDAO.deleteFilm(film);
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


    public static void modifnom(Film film,String nom) {
    FilmDAO.modifnom(film,nom);

    }
    public static void modifdescription(Film film,String description) {

        FilmDAO.modifdescription(film,description);


    }
    public static void modiflangues(Film film,String langue) {
        FilmDAO.modiflangues(film,langue);



    }
    public static void modifpaysoregine(Film film,String paysoegine) {
       FilmDAO.modifpaysoregine(film,paysoegine);

    }
    public static void modifAnnerdesoritie(Film film, LocalDate date) {
        FilmDAO.modifAnnerdesoritie(film,date);



    }
    public static void modiflistegenre(Film film, List<String> listegenre ) {
        FilmDAO.modiflistegenre(film,listegenre);


    }
    public static void modifduree(Film film,String duree ) {
        FilmDAO.modifduree(film,duree);



    }
    public static void modifimg(Film film, File img ) {
        FilmDAO.modifimg(film,img);



    }
    public static void modifsynop(Film film,File synop ) {
        FilmDAO.modifsynop(film,synop);


    }
    public static void modiffilmvedio(Film film,File vid ) {
        FilmDAO.modiffilmvedio(film,vid);



    }
    public static void deleteFilm_actsec(Film film, Actor act) {
        FilmDAO.deleteFilm_actsec(film,act);




    }
    public static void deleteFilm_actprinc(Film film,Actor act) {
        FilmDAO.deleteFilm_actprinc(film,act);





    }
    public static void ajoutFilm_actprinc(Film film,Actor act) {
        FilmDAO.ajoutFilm_actprinc(film,act);


    }
    public static void ajoutFilm_actsec(Film film,Actor act) {
        FilmDAO.ajoutFilm_actsec(film,act);



    }


    public static long getprodId(String nom, String prenom) {
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs;
        try {
            sql = "SELECT id_prod FROM producer WHERE nom LIKE ? OR prenome LIKE ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,  nom  );
            pstmt.setString(2,  prenom  );
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                System.out.println("PRODUCER n'existe pas");
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'exécution de la requête SQL : " + ex.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException ex) {
                System.out.println("Erreur lors de la fermeture du statement : " + ex.getMessage());
            }
        }
        return -1;
    }




    public static boolean authenticate(String mail, String pass) {
        PreparedStatement pstmt;
        String sql;
        ResultSet rs;

        try {
            sql = "SELECT * FROM Producer WHERE MAIL=? AND PASSWORD =?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mail);
            pstmt.setString(2, pass);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                long Prod_ID = rs.getInt("ID_PROD");
                String Nom = rs.getString("NOM");
                String Prenom = rs.getString("PRENOM");

                Producer producer = new Producer(Prod_ID,Nom,Prenom,mail,pass);

                DataHolder.setProducer(producer);
                return true;
            } else {
                System.out.println("Producer not found");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error authenticating user Producer: " + e.toString());
            return false;
        }
    }


    public static boolean check_Mail(String mail) {
        PreparedStatement pstmt;
        String sql;
        ResultSet rs;
        try {
            sql = "SELECT * FROM Producer where EMAIL=?";
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
        }

    }
















}
