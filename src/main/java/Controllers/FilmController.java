package Controllers;

import DAO.ActorDAO;
import DAO.AdminDAO;
import DAO.FilmDAO;
import DAO.ProducerDAO;
import Entities.*;
import Services.FilmService;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
//        ArrayList<String>list=new ArrayList<>();
//        list.add("fares");
//        list.add("zaza");
//        list.add("jasser");
        ArrayList<Actor>liste1=new ArrayList<>();

        List<String> listeGenre = Arrays.asList("Action","Comedie","Drama");

        File FileSynopsis = new File("src/main/java/Test/VideoFilm19.mp4");
        File FileImage = new File("src/main/java/Test/ImgEp10.jpeg");
        File FileVideo=new File("src/main/java/Test/VideoFilm19.mp4");

        Actor actor1 = new MainActor(1,"Smith","John","jsmith@example.com","password1");
        Actor actor2 = new MainActor(44,"Doe","Jane","jdoe@example.com","password2");
        Actor actor3 = new Supportingactor(45,"Johnson","Robert","rjohnson@example.com","password3");

        ArrayList<Actor> ListeActeurs = new ArrayList<>();

        ListeActeurs.add(actor1);
        ListeActeurs.add(actor2);
        ListeActeurs.add(actor3);

       //*/ Film film1 = new Film("FilmJasser","FaresCamera",LocalDate.of(2023,2,2),"Francais","France",listeGenre,FileImage
       //         ,"2heures30minutes",ListeActeurs,FileSynopsis,FileVideo);*/


//        FilmDAO.ajout_film(film1);

//        FilmDAO.recherche_film(8L);




               // liste1.add(new MainActor("jasser","hamdi","taha@gmail.com","zzzz"));
//        liste1.add(new MainActor("jasser","","taha@gmail.com","zzzz"));
        liste1.add(new Actor(1,"fares","makki","faresmakki21@gmail.com","tahajasser"));
        liste1.add(new Actor(41,"ahmed","makki","faresmakki21@gmail.com","tahajasser"));

ArrayList<String> list=new ArrayList();
list.add("drama");
        list.add("fares");

           // public Film(String nom, String realisateur, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre, File img, LocalTime duree, ArrayList<Actor> acteur, long vueNbr, long score, long votes, File synopsis, File film) {
//            Date dat=null;
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
      //  FilmDAO.Add(new Film("dao","service",LocalDate.now(),"arabic","tunisie",list,FileImage, "lyoumsba7",liste1,FileSynopsis,FileVideo,"film ya7ki ala 7aja machhoura"));
       // System.out.println(FilmDAO.recherche_filmact(new Actor(41,"hamadi","a7mer","hamadiahmer@gmail.com","zzzz"))); ;
     //  System.out.println(ActorDAO.getact("","")  );
        //System.out.println(        FilmDAO.recherche_filmnom("caff"));
       // AdminDAO.createadmin(new Admin("fares","makki","faresmakki21@gmail.com","fares123"));
        //System.out.println(FilmDAO.getnbrvue(new Film(16,"caffer","express",LocalDate.now(),"arabic","tunisie",list,imageFile, "lyoumsba7",liste1,11,11,11,synop,film)));
      // /FilmDAO.getscorepourcantage(new Film(16,"caffer","express",LocalDate.now(),"arabic","tunisie",list,imageFile, "lyoumsba7",liste1,11,11,11,synop,film));
      //  System.out.println(FilmDAO.getscorepourcantage(new Film(16,"caffer","express",LocalDate.now(),"arabic","tunisie",list,imageFile, "lyoumsba7",liste1,11,11,11,synop,film)));
       // System.out.println(FilmDAO.FindByName(""));
       // System.out.println(FilmService.filterByGenre("fares"));
        Long x= Long.valueOf(1);
//FilmDAO.Add(new Film("ddjd","eoeoo",LocalDate.now(),"arab","tunis",list,FileImage,"duree",liste1,FileSynopsis,FileVideo,x));
        System.out.println(FilmDAO.FindByName("d"));
    }
}
