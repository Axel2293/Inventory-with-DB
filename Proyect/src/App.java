import Application.LoginWindow;
import DataBaseConnection.DBToolkit;

public class App {
    public static void main(String[] args) throws Exception {

        DBToolkit db = DBToolkit.getToolkit("root", "Legoishadow22", "jdbc:mysql://20.25.141.116:3306/Company");

        LoginWindow.createLoginWindow();
        db.closeConnection();

        
    }
}
