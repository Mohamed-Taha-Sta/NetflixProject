package DAO;

import Entities.*;
import Utils.ConxDB;
import Utils.DataHolder;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ActorDAO {

    //    public List<Long> CheckVues(Content content, Season season, Episode episode)
//    {
//        List<Long> vues=new ArrayList<>();
//
//        if (content instanceof Serie serie) {
//            if (season != null) {
//                Season seas;
//                for (Season se : serie.getSeasonList()) {
//                    if (se.equals(season)) {
//                        seas =se;
//                    }
//                }
//                if(episode !=null){
//
//                    for
//                }
//
//            }
//
//        }
//    }
/*   protected long ID;
    protected String Name;
    protected String Prename;

    protected String Mail;
    protected String password;*/
    private static final Connection conn = ConxDB.getInstance();

    public static boolean ajout_acteur(Actor act) {
        boolean etat = true;
        PreparedStatement pstmt = null;
        String sql;
        Long compteur = act.getID();

        try {

            // String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (3, 'Jesser')";
                sql = "INSERT INTO Actor (nom,prenome,mail,password) VALUES (?,?,?,?)";

                // sql = "INSERT INTO mainactor (id_act,nom,prenome,mail,password) VALUES (" + Long.toString(Actor.getID()) + "," + act.getName() + "," + act.getPrename() + "," + act.getMail() + "," + act.getPassword() + ")";


            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, act.getName());
            pstmt.setString(2, act.getPrename());
            pstmt.setString(3, act.getMail());
            pstmt.setString(4, act.getPassword());


            //pstmt = conn.prepareStatement(sql);

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            etat = false;
        }
        return etat;
    }

    /*public static long getactid(String nom, String prenom) {/*pour le recherche des film avec nom acteur saisie*/
       /* PreparedStatement pstmt = null;
        String sql1;
        ResultSet rs;
        try {


                sql1 = "SELECT id_act FROM ACTOR WHERE ACTOR.nom='%" + nom + "%' or ACTOR.prenome='%" + prenom + "%'";
                pstmt = conn.prepareStatement(sql1);
                rs = pstmt.executeQuery();


                rs.next();

                return rs.getLong(1);




        } catch (Exception ex) {
            System.out.println("acteur nexiste pas");


        }
        return -1;

    }*/
    public static long getActId(String nom, String prenom) {
        PreparedStatement pstmt = null;
        String sql;
        ResultSet rs;
        try {
            sql = "SELECT id_act FROM ACTOR WHERE nom LIKE ? OR prenome LIKE ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,  nom  );
            pstmt.setString(2,  prenom  );
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                System.out.println("acteur n'existe pas");
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
    public static List<Actor> recherche_actjasser(String something) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Actor> list = new ArrayList<>();
        //  ArrayList<String>list1=new ArrayList<>();
        Actor act = null;
        try {
            // String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (3, 'Jesser')";
            if(something.isEmpty()){
                String sql = "select id_act,nom,prenome,Mail,password from Actor";

                pstmt = conn.prepareStatement(sql);

                rs = pstmt.executeQuery();
                while (rs.next()) {
                    act = new Actor(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                    list.add(act);
                }
            }
            else{
                String sql = "select id_act,nom,prenome,Mail,password from Actor where Nom like ? or Prenome like ?";

                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,"%"+something+"%");
                pstmt.setString(2,"%"+something+"%");
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    act = new Actor(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                    list.add(act);
                }
            }



        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


        return list;
    }


    public static ArrayList<Actor> getact(String nom, String prenom) {/*pour le recherche des film avec nom acteur saisie*/
        PreparedStatement pstmt = null;
        String sql1;
        ResultSet rs=null;
        ArrayList<Actor>list=new ArrayList<>();
        try {

            try {
                sql1 = "SELECT id_act, nom,prenome,mail,password " +
                        "FROM Actor " +
                        "WHERE Actor.nom LIKE '%" + nom + "%'" + "and Actor.prenome LIKE '%" + prenom + "%'";
                pstmt = conn.prepareStatement(sql1);
                rs = pstmt.executeQuery();


            } catch (Exception e) {

            }
            try{
            while (rs.next()) {
                Long id_act=rs.getLong("id_act");
                String name=rs.getString("nom");
                String prenome=rs.getString("prenome");
                String mail=rs.getString("mail");
                String pass=rs.getString("password");
                Actor ACT=new Actor(id_act,name,prenome,mail,pass);
                list.add( ACT);
            }}catch (Exception e){
                System.out.println("there are no main actor with that name");
            }


        } catch (Exception e) {
            System.out.println("acteur nexiste pas");


            throw new RuntimeException(e);
        }

    return list;
    }



    public static void modifnom(Long id,String nom){
        /**/
        PreparedStatement pstmt = null;
        String sql = "UPDATE ACTOR SET nom = '"+nom+"' WHERE id_act = "+id;
        try {


            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        }catch (Exception e){

        }
    }
    public static void modifprenom(Long id,String prenom){
        /**/
        PreparedStatement pstmt = null;
        String sql = "UPDATE ACTOR SET prenom = '"+prenom+"' WHERE id_act = "+id;
        try {


            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        }catch (Exception e){

        }
    }
    public static void modifmail(Long id,String mail){
        /**/
        PreparedStatement pstmt = null;
        String sql = "UPDATE actor SET mail = '"+mail+"' WHERE id_act = "+id;
        try {


            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        }catch (Exception e){

        }
    }
    public static void modifpassword(Long id,String password){
        /**/
        PreparedStatement pstmt = null;
        String sql = "UPDATE actor SET password = '"+password+"' WHERE id_act = "+id;
        try {


            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        }catch (Exception e){

        }
    }




    public static boolean authenticate(String mail, String pass) {
        PreparedStatement pstmt;
        String sql;
        ResultSet rs;

        try {
            sql = "SELECT * FROM Actor WHERE MAIL=? AND PASSWORD =?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mail);
            pstmt.setString(2, pass);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                long ActId = rs.getInt("ID_ACT");
                String Nom = rs.getString("NOM");
                String Prenom = rs.getString("PRENOME");

                Actor actor = new Actor(ActId,Nom,Prenom,mail,pass);

                DataHolder.setActor(actor);
                return true;
            } else {
                System.out.println("Actor not found");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error authenticating user Actor: " + e.toString());
            return false;
        }
    }



    public static boolean check_Mail(String mail) {
        PreparedStatement pstmt;
        String sql;
        ResultSet rs;
        try {
            sql = "SELECT * FROM Actor where MAIL=?";
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