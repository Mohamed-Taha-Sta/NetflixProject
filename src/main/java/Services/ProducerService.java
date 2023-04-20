package Services;

import DAO.ProducerDAO;
import DAO.UserDAO;
import Entities.Producer;

public class ProducerService {

    public static  void createprod(Producer prod) {
        ProducerDAO.createprod(prod);
    }

    public static boolean authenticate(String mail, String pass) {
        return ProducerDAO.authenticate(mail,pass);
    }


    public static boolean check_Mail(String mail) {
        return ProducerDAO.check_Mail(mail);
    }



}
