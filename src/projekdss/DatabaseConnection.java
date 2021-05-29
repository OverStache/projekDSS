package projekdss;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
public class DatabaseConnection {
    private Connection connection = null;
    JFrame frame = new JFrame();
    public Connection setConnection() {
        try{
            String url = "jdbc:mysql://localhost:3306/DSS";
            String user = "root";
            String pass = "root";
//            String db = "db_2TIPS";
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(url, user, pass);
//                JOptionPane.showMessageDialog(frame,"Updated by "+a );
        }catch(Exception e){            
            System.out.println("Error "+e.getMessage());
        }return connection;
        }
}
