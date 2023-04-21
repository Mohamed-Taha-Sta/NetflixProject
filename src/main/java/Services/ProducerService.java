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



}
