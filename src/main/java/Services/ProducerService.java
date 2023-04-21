package Services;

import DAO.ProducerDAO;
import Entities.Producer;

public class ProducerService {

    public static long createprod(Producer prod) {
        return ProducerDAO.createprod(prod);

    }

    public static boolean authenticate(String mail, String pass) {
        return ProducerDAO.authenticate(mail,pass);
    }


    public static boolean check_Mail(String mail) {
        return ProducerDAO.check_Mail(mail);
    }

    public static void modifnom(Long id,String nom) {
        ProducerDAO.modifnom(id,nom);
    }

    public static void modifpassword(Long id,String pass) {
        ProducerDAO.modifpassword(id,pass);
    }

    public static void modifmail(Long id,String pass) {
        ProducerDAO.modifmail(id,pass);
    }

    public static void modifprenom(Long id,String pass) {
        ProducerDAO.modifprenom(id,pass);
    }

    public static Producer getProdByID(long ID_PROD) {
        return ProducerDAO.getProdByID(ID_PROD);
    }


    }
