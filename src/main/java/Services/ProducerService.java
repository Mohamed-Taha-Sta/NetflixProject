package Services;

import DAO.ProducerDAO;
import Entities.Producer;

public class ProducerService {

    public static  void createprod(Producer prod) {
        ProducerDAO.createprod(prod);
    }

    public static boolean authenticate(String mail, String pass) {
        return ProducerDAO.authenticate(mail,pass);
    }

}
