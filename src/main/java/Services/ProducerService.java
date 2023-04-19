package Services;

import DAO.ProducerDAO;
import Entities.Producer;

public class ProducerService {

    public static  void createprod(Producer prod) {
        ProducerDAO.createprod(prod);
    }


}
