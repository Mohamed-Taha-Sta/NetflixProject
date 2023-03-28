package Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import persistence.models.Personne;
import persistence.utils.ConxDB;

public class PersonneDAO {
	
	// Connection
	private static final Connection conn = ConxDB.getInstance();
    
	        
	public static int save(Personne personne) {
		int personneId = 0;
		PreparedStatement pstmt = null;
	    ResultSet rs = null;

		try {
			String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (3, 'Jesser')";

			pstmt = conn.prepareStatement(sql);

			pstmt.executeUpdate();

		}catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

//		try {
//			String sql = "INSERT INTO Client (ID, FIRSTNAME) VALUES (?, ?)";
//
//			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//
//			pstmt.setString(1, personne.getPrenom());
//			pstmt.executeUpdate();
//
//			// 4- Recupérer l'Id généré par le SGBD
//			rs = pstmt.getGeneratedKeys();
//
//			if(rs.next())
//				personneId = rs.getInt(1);
//
//		}catch (SQLException ex) {
//			System.out.println(ex.getMessage());
//		}
		return personneId;
	}
	
	public static List<Personne> findAll(){
		
		Statement stmt = null;
	    ResultSet rs = null;
	    
		List<Personne> personnes = new ArrayList<>();

        String SQL = "SELECT * FROM Client";
        try {
        	stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {

            	int id = rs.getInt(1);
                String firstName = rs.getString(2);

                Personne peronne = new Personne(id, firstName);
                personnes.add(peronne);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return personnes;
	}

}
