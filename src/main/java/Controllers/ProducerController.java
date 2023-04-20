package Controllers;

import DAO.UserDAO;
import Entities.Producer;
import Services.ProducerService;

public class ProducerController {

    public static void main(String[] args) {

        Producer producer = new Producer("WB","","WB@gmail.com","123456");

        createprod(producer);
    }

    public static  void createprod(Producer prod) {
        ProducerService.createprod(prod);
    }

    public static boolean authenticate(String mail, String pass) {
        return ProducerService.authenticate(mail, pass);
    }


    public static boolean check_Mail(String mail) {
        return ProducerService.check_Mail(mail);
    }


}
