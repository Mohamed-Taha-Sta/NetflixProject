package Controllers;

import Controllers.FXMLControllers.VideoPlayerController;
import DAO.*;
import Entities.*;
import Services.FilmService;
import Utils.DataHolderFilm;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FilmController {

    public static void main(String[] args) throws IOException, SQLException {

//        File FileVideo=new File("src/main/java/Test/Synopsis.mp4");
//
//        Media media=new Media(FileVideo.toURI().toString());
//        MediaPlayer mp=new MediaPlayer(media);
//        System.out.println(media.getDuration().toSeconds());
//        String duration = VideoPlayerController.getTime(media.getDuration());
//
//        System.out.println(duration);
      /* File FileVideo=new File("src/main/java/Test/Synopsis.mp4");

//        FilmController.FindByID(10L);

//        File img = new File("src/main/java/Test/FilmPlaceHolder.jpg");
////
////
//        Film film1 = new Film(10L);
//        FilmDAO.modifimg(film1,img);

        System.out.println(duration);*/



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
//        ArrayList<Actor>liste1=new ArrayList<>();
//
//        List<String> listeGenre = Arrays.asList("Action","Comedie","Drama");
//
//        File FileSynopsis = new File("src/main/java/Test/VideoFilm19.mp4");
//        File FileImage = new File("src/main/java/Test/ImgEp10.jpeg");
//        File FileVideo=new File("src/main/java/Test/VideoFilm19.mp4");

/*        Actor actor1 = new MainActor(1,"Smith","John","jsmith@example.com","password1");
        Actor actor2 = new MainActor(44,"Doe","Jane","jdoe@example.com","password2");
        Actor actor3 = new Supportingactor(45,"Johnson","Robert","rjohnson@example.com","password3");

        ArrayList<Actor> ListeActeurs = new ArrayList<>();
        ListeActeurs.add(actor1);
        ListeActeurs.add(actor2);
        ListeActeurs.add(actor3);
*/
       //*/ Film film1 = new Film("FilmJasser","FaresCamera",LocalDate.of(2023,2,2),"Francais","France",listeGenre,FileImage
       //         ,"2heures30minutes",ListeActeurs,FileSynopsis,FileVideo);*/


//        FilmDAO.ajout_film(film1);

//        FilmDAO.recherche_film(8L);


//        ProducerDAO.createprod(new Producer("fares","eeee","fffffff","eeeee"));

               // liste1.add(new MainActor("jasser","hamdi","taha@gmail.com","zzzz"));
//        liste1.add(new MainActor("jasser","","taha@gmail.com","zzzz"));
//        liste1.add(new Actor(1,"fares","makki","faresmakki21@gmail.com","tahajasser"));
//        liste1.add(new Actor(41,"ahmed","makki","faresmakki21@gmail.com","tahajasser"));
//
//ArrayList<String> list=new ArrayList();
//list.add("drama");
//        list.add("fares");

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
//        Long x= Long.valueOf(1);
////FilmDAO.Add(new Film("ddjd","eoeoo",LocalDate.now(),"arab","tunis",list,FileImage,"duree",liste1,FileSynopsis,FileVideo,x));
//      //  System.out.println(FilmDAO.FindByName("d"));
        ArrayList<String> lo =new ArrayList<>();
        lo.add("drama");
        ArrayList<Long>l=new ArrayList<>();
        Long lf= Long.valueOf(10);
        l.add(lf);
//       // FilmDAO.modifimg(new Film(2,"caffer","express",LocalDate.now(),"arabic","tunisie",list,FileImage, "lyoumsba7",liste1,x,x,x,FileSynopsis,FileVideo,x),FileImage);
//           // public Film(long id, String nom, String desc, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre, File img, String duree, ArrayList<Actor> acteur, long vueNbr, long score, long votes, File synopsis, File film,Long idrealisateur) {
//FilmDAO.deleteFilm_actsec(new Film(2,"caffer","express",LocalDate.now(),"arabic","tunisie",list,FileImage, "lyoumsba7",liste1,x,x,x,FileSynopsis,FileVideo,x),new Actor(42,"fares","makki","faresmakki21@gmail.com","tahajasser"));


//        UserDAO.ajout_User(new User("user","prenom","faresmakki@gmail.com","fares",LocalDate.now(),l,lo,LocalDate.now(),null));
     //   Long lff= Long.valueOf(1);
          // new User()
       // Avis_FilmDAO.add_avis(new Film(lf,"caffer","eee",LocalDate.now(),"arabic","tunisie",null,null, "lyoumsba7",null,lf,lf,lf,null,null,lf),new User(2,"user","prenom","faresmakki@gmail.com","fares",LocalDate.now(),l,lo,LocalDate.now(),null),"bla bla");
        //System.out.println(       FilmDAO.searchFilm(lo)










        }

    public static boolean Add(Film film){
        return FilmService.Add(film);
    }
    public static List<Film> FindByID(Long filmid){
        return FilmService.FindByID(filmid);
    }
    public static ArrayList<Film> FindByName(String filmnom){
        return FilmService.FindByName(filmnom);
    }
    public static ArrayList<Film> FindByActor(Actor act){
        return FilmService.FindByActor(act);
    }
    public static ArrayList<Film> GetAllFilms(){
        return FilmService.GetAllFilms();
    }

    public static ArrayList<Film> FindByproducer(Producer prod){
        return FilmService.FindByproducer(prod);
    }

    public static List<Film> searchFilm(List<String> searchTerms) throws SQLException, IOException {
        return FilmService.searchFilm(searchTerms);
    }
    public static List<Film> searchFilmOR(List<String> searchTerms) throws SQLException, IOException {
        return FilmService.searchFilmOR(searchTerms);
    }
    public static List<Film> getMostRecentFilm(int numSeries) throws SQLException, IOException{
        return FilmService.getMostRecentFilm(numSeries);
    }
}
