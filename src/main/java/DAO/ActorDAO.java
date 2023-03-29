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
        String type="main";//get_from_jasser()
        String sql;
        Long compteur=act.getID();
        try {
            // String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (3, 'Jesser')";
        if(type=="main") {
             sql = "INSERT INTO mainactor (id_act,nom,prenome,mail,password) VALUES (" + Long.toString(act.getID()) + "," + act.getName() + "," + act.getPrename() + "," + act.getMail() + "," + act.getPassword() + ")";
        }else{
             sql = "INSERT INTO supportingactor (id_act,nom,prenome,mail,password) VALUES (" + Long.toString(act.getID()) + "," + act.getName() + "," + act.getPrename() + "," + act.getMail() + "," + act.getPassword() + ")";


        }
            pstmt = conn.prepareStatement(sql);

            pstmt.executeUpdate();
            act.setID(compteur+1);

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
            etat=false;
        }
        return etat;
    }
public static long  getactid(String nom,String prenom ){
    PreparedStatement pstmt = null;
    PreparedStatement pstmt1 = null;
    String sql2;
    String sql1;
    ResultSet rs;
    ResultSet rs1;
 try {
         sql1="select id_act from mainactor where mainactor.nom="+nom+" and mainactor.prenome="+prenom;
     pstmt = conn.prepareStatement(sql1);
     pstmt.executeUpdate();
     rs = pstmt.getGeneratedKeys();
         sql2="select id_act from supportingactor where supportingactor.nom="+nom+" and supportingactor.prenome="+prenom;
     pstmt1 = conn.prepareStatement(sql2);
     pstmt.executeUpdate();
     rs1 = pstmt.getGeneratedKeys();
    if(rs.getString(1)==null || rs.getString(1)=="" || rs.getString(1).length()<1){
        return rs1.getLong(1);
    }
     return rs.getLong(1);
 }catch (SQLException ex){
     System.out.println(ex.getMessage());
 }
 return 0;
}/*
    public static List<String> recherche_filmActor(String filmname) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        List<String>list=new ArrayList<>();
        try {
            // String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (3, 'Jesser')";

            String sql="select id_film,nom,realisateur,annerdesortie,langue,paysorigine,duree from Film where Film.nom like%"+filmname+"%";
            pstmt = conn.prepareStatement(sql);

            pstmt.executeQuery();
            while (rs.next()) {
            rs = pstmt.getGeneratedKeys();
            list.add(rs.getString(1));
            list.add(rs.getString(2));
            list.add(rs.getString(3));
            list.add(rs.getString(4));
            list.add(rs.getString(5));
            list.add(rs.getString(6));
            list.add(rs.getString(7));}

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


        return list;
    }*/
























}