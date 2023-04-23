package Controllers;

import DAO.AdminDAO;
import Services.AdminService;

public class AdminController {

    public static void modifnom(Long id,String nom) {
        AdminService.modifnom(id,nom);
    }

    public static void modifprenom(Long id,String prenom) {
        AdminService.modifprenom(id,prenom);
    }

    public static void modifmail(Long id,String mail) {
        AdminService.modifmail(id,mail);
    }

    public static void modifpass(Long id,String pass) {
        AdminService.modifpass(id,pass);
    }


    public static boolean check_Mail(String mail) {
        return AdminService.check_Mail(mail);
    }

    public static boolean authenticate(String mail, String pass) {
        return AdminService.authenticate(mail, pass);
    }




}
