package Controllers;

import DAO.ActorDAO;
import DAO.AdminDAO;
import DAO.FilmDAO;
import DAO.ProducerDAO;
import Entities.*;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class FilmController {
    public static void main(String[] args) {
     //   Long compteur=Actor.getID();

       // Actor.ID=Actor.ID+1;

        //ActorDAO.ajout_acteur(new Actor("fares","makki","faresmakki21@gmail.com","tahajasser"));
    //ActorDAO.ajout_acteur(new Actor("jasser","hamdi","taha@gmail.com","zzzz"));
       //System.out.println(   ActorDAO.recherche_actjasser());
        //System.out.println(Actor.getID());
      //  Actor.ID=Actor.ID+1;
      //  System.out.println(Actor.getID());/*
        ArrayList<String>list=new ArrayList<>();
        list.add("fares");
        list.add("zaza");
        list.add("jasser");
        ArrayList<Actor>liste1=new ArrayList<>();
        liste1.add(new Supportingactor(90,"jasser","hamdi","taha@gmail.com","zzzz"));
       // liste1.add(new Actor(7,"fares","makki","faresmakki21@gmail.com","tahajasser"));
        //liste1.add(new Actor(10,"ahmed","makki","faresmakki21@gmail.com","tahajasser"));

        File synop = new File("src/main/java/Test/VideoTest.mp4");
          File imageFile = new File("src/main/java/Test/LionTest.jpeg");
        File film=new File("src/main/java/Test/VideoTest.mp4");

           // public Film(String nom, String realisateur, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre, File img, LocalTime duree, ArrayList<Actor> acteur, long vueNbr, long score, long votes, File synopsis, File film) {
            Date dat=null;
         //FilmDAO.ajout_film(new Film("caffer","express",LocalDate.now(),"arabic","tunisie",list,imageFile, "lyoumsba7",liste1,11,11,11,synop,film));
           //FilmDAO.ajout_film(new Film("tahamachhour","taha",3, dat,"arabic","tunisie",list,imageFile, "2heurs30minute",liste1,11,11,11,synop,film));
      /* ArrayList<Film>t= (ArrayList<Film>) FilmDAO.recherche_filmnom("");*/
       /* ArrayList<Actor>t=ActorDAO.getact("","m");
        for (int i = 0; i < t.size(); i++) {
            System.out.println(t.get(i));
        }*/
        //liste1.add(new MainActor(410,"hamadi","a7mer","hamadiahmer@gmail.com","zzzz"));
        //Producer p=new Producer("martadha","machhour","@gamil.com","123456789aze");
       // ProducerDAO.createprod(p);
       // ProducerDAO.modifprenom(1L,"rmida");
        //ProducerDAO.modifpassword(1L,"hosnimouchrajel");
       // ActorDAO.ajout_acteur(new Actor(9,"hamadi","a7mer","hamadiahmer@gmail.com","zzzz"));
       // System.out.println(ActorDAO.recherche_actjasser());;
        //FilmDAO.ajout_film(new Film("caffer","express",LocalDate.now(),"arabic","tunisie",list,imageFile, "lyoumsba7",liste1,11,11,11,synop,film));
       // System.out.println(FilmDAO.recherche_filmact(new Actor(41,"hamadi","a7mer","hamadiahmer@gmail.com","zzzz"))); ;
     //  System.out.println(ActorDAO.getact("","")  );
        //System.out.println(        FilmDAO.recherche_filmnom("caff"));
       // AdminDAO.createadmin(new Admin("fares","makki","faresmakki21@gmail.com","fares123"));
        //System.out.println(FilmDAO.getnbrvue(new Film(16,"caffer","express",LocalDate.now(),"arabic","tunisie",list,imageFile, "lyoumsba7",liste1,11,11,11,synop,film)));
       // FilmDAO.getscorepourcantage(new Film(16,"caffer","express",LocalDate.now(),"arabic","tunisie",list,imageFile, "lyoumsba7",liste1,11,11,11,synop,film));
      //  System.out.println(FilmDAO.getscorepourcantage(new Film(16,"caffer","express",LocalDate.now(),"arabic","tunisie",list,imageFile, "lyoumsba7",liste1,11,11,11,synop,film)));

    }
}
