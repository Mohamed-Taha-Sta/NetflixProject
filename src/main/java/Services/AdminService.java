package Services;

import DAO.ActorDAO;
import DAO.AdminDAO;

public class AdminService {

    public static void modifnom(Long id,String nom) {
        AdminDAO.modifnom(id,nom);
    }

    public static void modifprenom(Long id,String prenom) {
        AdminDAO.modifprenom(id,prenom);
    }

    public static void modifmail(Long id,String mail) {
        AdminDAO.modifmail(id,mail);
    }

    public static void modifpass(Long id,String pass) {
        AdminDAO.modifpass(id,pass);
    }

    public static boolean check_Mail(String mail) {
        return AdminDAO.check_Mail(mail);
    }

    public static boolean authenticate(String mail, String pass) {
        return AdminDAO.authenticate(mail, pass);
    }


}
