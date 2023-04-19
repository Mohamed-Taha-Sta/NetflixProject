package Controllers;

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

}
