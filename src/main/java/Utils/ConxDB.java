package Utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConxDB{

    private static Connection connexion;

    private final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private final String USER = "system";
    private final String PASS = "123456";

    private ConxDB() throws SQLException{

        try{
               Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }

        connexion= DriverManager.getConnection(DB_URL, USER, PASS);

    }

    public static Connection getInstance(){
        if (connexion == null)
            try {
                new ConxDB();
            }catch(Exception e){
                System.out.println("--"+e.getMessage());
            }
        return connexion;
    }
}