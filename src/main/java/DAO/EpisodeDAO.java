package DAO;

import Entities.Episode;
import Entities.Text;
import Utils.ConxDB;
import javafx.scene.image.Image;
import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EpisodeDAO {

    private static final Connection conn = ConxDB.getInstance();

    public static boolean ajout_episode(Episode episode) {
        boolean etat = true;
        PreparedStatement pstmt = null;
        String sql ;

        try {
            if(episode.getResume() instanceof Text)
                sql = "INSERT INTO EPISODES (Name,SEASON_ID,NUM,DEBUT_DATE,PREMIERE_DATE,IMAGE,TEXTE,VIEW_NBR,SCORE,VOTES,VIDEO) VALUES ("+episode.getName()+episode.getSeasonParentID()+","+episode.getNumber()+","
                        +episode.getDebutDate().toString()+","+episode.getPremiereDate()+","+episode.getImage()+","+episode.getResume() +","+episode.getVueNbr()+","+episode.getVotes() +","+episode.getMedia() +")";
            else
                sql = "INSERT INTO EPISODES (Name,SEASON_ID,NUM,DEBUT_DATE,PREMIERE_DATE,IMAGE,SYNOPSIS,VIEW_NBR,SCORE,VOTES,VIDEO) VALUES (" + episode.getName() + episode.getSeasonParentID() + "," + episode.getNumber() + ","
                        + episode.getDebutDate().toString() + "," + episode.getPremiereDate() + "," + episode.getImage() + "," + episode.getResume() + "," + episode.getVueNbr() + "," + episode.getVotes() + "," + episode.getMedia() + ")";

            pstmt = conn.prepareStatement(sql);

            pstmt.executeUpdate();

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
            etat=false;
        }
        return etat;
    }


    public static List<Episode> recherche_film(String EpisodeName) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Episode> episodeList =new ArrayList<>();
        try {
            String sql="select ID,Name,SEASON_ID,NUM,DEBUT_DATE,PREMIERE_DATE,IMAGE,TEXTE,SYNOPSIS,VIEW_NBR,SCORE,VOTES,VIDEO from Episodes where Episodes.Name like %"+EpisodeName+"%";
            pstmt = conn.prepareStatement(sql);

            pstmt.executeQuery();
            rs = pstmt.getGeneratedKeys();

            while (rs.next())
            {
                long id = rs.getLong(1);
                String Name = rs.getString(2);
                long Seasonid = rs.getLong(3);
                int num = rs.getInt(4);
                Date DebutDate = rs.getDate(5);
                Date PremiereDate = rs.getDate(6);
                Image image = (Image) rs.getBlob(7);
                String texte = rs.getString(8);
                Blob synopsis = rs.getBlob(9);
                long viewNum = rs.getInt(10);
                long score = rs.getLong(11);
                long votes = rs.getLong(12);
                Blob video = rs.getBlob(13);

                Episode episode = new Episode(id,Seasonid,Name,num,DebutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),PremiereDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                        ,image,viewNum,score,votes);
                episodeList.add(episode);
            }

        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


        return episodeList;
    }


}











