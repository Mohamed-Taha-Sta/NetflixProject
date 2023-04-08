package DAO;

import Entities.*;
import Utils.ConxDB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        Long compteur=act.getID();

        try {

            // String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (3, 'Jesser')";
            if(act instanceof MainActor) {
                sql = "INSERT INTO mainactor (nom,prenome,mail,password) VALUES (?,?,?,?)";

            // sql = "INSERT INTO mainactor (id_act,nom,prenome,mail,password) VALUES (" + Long.toString(Actor.getID()) + "," + act.getName() + "," + act.getPrename() + "," + act.getMail() + "," + act.getPassword() + ")";
            }else{
               //  sql = "INSERT INTO supportingactor (id_act,nom,prenome,mail,password) VALUES (" + Long.toString(act.getID()) + "," + act.getName() + "," + act.getPrename() + "," + act.getMail() + "," + act.getPassword() + ")";
                sql = "INSERT INTO SUPPORTINGACTOR (nom,prenom,mail,password) VALUES (?,?,?,?)";


            }

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, act.getName());
            pstmt.setString(2, act.getPrename());
            pstmt.setString(3, act.getMail());
            pstmt.setString(4, act.getPassword());




            //pstmt = conn.prepareStatement(sql);

            pstmt.executeUpdate();

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
            etat=false;
        }
        return etat;
    }
public static long  getactid(String nom,String prenom ){/*pour le recherche des film avec nom acteur saisie*/
    PreparedStatement pstmt = null;
    String sql1;
    ResultSet rs;
 try {
       //  sql1="select id_act from MAINACTOR where mainactor.nom="+nom+"and mainactor.prenome="+prenom;

     int test=0;
     try{
         sql1="SELECT id_act FROM MAINACTOR WHERE mainactor.nom='" + nom + "' AND mainactor.prenome='" + prenom + "'";
         pstmt = conn.prepareStatement(sql1);
         rs=pstmt.executeQuery();
         rs.next();
         return rs.getLong(1);
     }catch(Exception e){
         sql1="select id_act from SUPPORTINGACTOR where SUPPORTINGACTOR.nom="+nom+" and SUPPORTINGACTOR.prenom="+prenom;
         pstmt = conn.prepareStatement(sql1);
         rs=pstmt.executeQuery();
         rs.next();
         return rs.getLong(1);

     }














 }catch (SQLException ex){
     System.out.println("acteur nexiste pas");
     return -1;


 }

}    public static List<Actor> recherche_actjasser() {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Actor>list=new ArrayList<>();
     //  ArrayList<String>list1=new ArrayList<>();
        Actor act=null;
        try {
            // String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (3, 'Jesser')";
            String sql="select id_act,nom,prenome,Mail,password from mainactor";

            pstmt = conn.prepareStatement(sql);

            pstmt.executeQuery();
            rs = pstmt.executeQuery();
            while (rs.next()) {
                act=new Actor(rs.getLong(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));


                list.add(act);




                ;}
             sql="select id_act,nom,prenom,Mail,password from SUPPORTINGACTOR";

            pstmt = conn.prepareStatement(sql);

            pstmt.executeQuery();
            rs = pstmt.executeQuery();
            while (rs.next()) {
                act=new Actor(rs.getLong(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));

                list.add(act);

                ;}

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


        return list;
    }























}