package Controllers;

import DAO.ProducerDAO;
import Entities.Producer;
import Services.ProducerService;

public class ProducerController {

    public static void main(String[] args) {

        Producer producer = new Producer("WB","","WB@gmail.com","123456");

        createprod(producer);
    }

    public static long createprod(Producer prod) {
        return ProducerService.createprod(prod);

    }

    public static boolean authenticate(String mail, String pass) {
        return ProducerService.authenticate(mail, pass);
    }


    public static boolean check_Mail(String mail) {
        return ProducerService.check_Mail(mail);
    }

    public static void modifnom(Long id,String nom) {
        ProducerService.modifnom(id,nom);
    }
    public static void modifpassword(Long id,String pass) {
        ProducerService.modifpassword(id,pass);
    }

    public static void modifmail(Long id,String pass) {
        ProducerService.modifmail(id,pass);
    }
    public static void modifprenom(Long id,String pass) {
        ProducerService.modifprenom(id,pass);
    }

    public static Producer getProdByID(long ID_PROD) {
        return ProducerService.getProdByID(ID_PROD);
    }



}
