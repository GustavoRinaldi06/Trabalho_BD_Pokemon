
package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author mari
 */
public class DB {
    public static Connection connect(String user, String password) throws SQLException{
        
        try{
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/pokemon", user, password);
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, "O banco de dados está online?", "Alerta!", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
