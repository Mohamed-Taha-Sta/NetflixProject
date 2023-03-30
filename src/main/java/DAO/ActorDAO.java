package DAO;

import Entities.*;
import Utils.ConxDB;

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
        String type="sec";//get_from_jasser()
        String sql;
        Long compteur=act.getID();

        try {

            // String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (3, 'Jesser')";
            if(type=="main") {
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
public static long  getactid(String nom,String prenom ){
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

}    public static List<ArrayList<String>> recherche_actjasser() {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ArrayList<String>>list=new ArrayList<>();
     //  ArrayList<String>list1=new ArrayList<>();

        try {
            // String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (3, 'Jesser')";
            String sql="select id_act,nom,prenome,Mail,password from mainactor";

            pstmt = conn.prepareStatement(sql);

            pstmt.executeQuery();
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ArrayList<String>list1=new ArrayList<>();

                list1.add(rs.getString(1));
                list1.add(rs.getString(2));
                list1.add(rs.getString(3));
                list1.add(rs.getString(4));
                list1.add(rs.getString(5));
                list.add(list1);
                //System.out.println(list1);
               // System.out.println(list);

                list1.remove(0)  ;
               // System.out.println(list);



                ;}
             sql="select id_act,nom,prenom,Mail,password from SUPPORTINGACTOR";

            pstmt = conn.prepareStatement(sql);

            pstmt.executeQuery();
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ArrayList<String>list1=new ArrayList<>();

                list1.add(rs.getString(1));
                list1.add(rs.getString(2));
                list1.add(rs.getString(3));
                list1.add(rs.getString(4));
                list1.add(rs.getString(5));
                list.add(list1);
                //System.out.println(list1);
                // System.out.println(list);

                list1.remove(0)  ;
                // System.out.println(list);



                ;}

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


        return list;
    }























}