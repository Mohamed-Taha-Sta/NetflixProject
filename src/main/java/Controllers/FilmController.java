package Controllers;

import DAO.ActorDAO;
import DAO.FilmDAO;
import Entities.Actor;
import Entities.Film;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class FilmController {
    public static void main(String[] args) {
        Long compteur=Actor.getID();

       // Actor.ID=Actor.ID+1;

        //ActorDAO.ajout_acteur(new Actor("fares","makki","faresmakki21@gmail.com","tahajasser"));
    //ActorDAO.ajout_acteur(new Actor("jasser","hamdi","taha@gmail.com","zzzz"));
       //System.out.println(   ActorDAO.recherche_actjasser());
        //System.out.println(Actor.getID());
      //  Actor.ID=Actor.ID+1;
      //  System.out.println(Actor.getID());
        ArrayList<String>list=new ArrayList<>();
        list.add("fares");
        list.add("zaza");
        list.add("jasser");
        ArrayList<Actor>liste1=new ArrayList<>();
        liste1.add(new Actor("jasser","hamdi","taha@gmail.com","zzzz"));
        liste1.add(new Actor("fares","makki","faresmakki21@gmail.com","tahajasser"));
        liste1.add(new Actor("ahmed","makki","faresmakki21@gmail.com","tahajasser"));

        FilmDAO.ajout_film(new Film("dont breath","3amirajel", LocalDate.now(),"arabic","tunis",list, LocalTime.now(),liste1));


    }
}
