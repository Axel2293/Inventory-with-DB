package DataBaseConnection;

import java.sql.*;

import javax.swing.JOptionPane;
                                                                                                                                                                                                                        

/**
 * Tools for the connection, modification, insertion or delition of data in the data base of the company. Constructed as a singletone.
 */
public class DBToolkit {

    //  User and password to make the connecton to the data base 
    private static String pass=null;
    private static String user=null;
    // Url of the data base
    private static String url = null;

    // Unique instance of the toolkit
    private static DBToolkit instace = null;

    // Open connection to the database
    private Connection DB = null;
    
    private DBToolkit(){

    }

    /*
     * Gives the instance of the toolkit without initializing the credentials for the connection with the data base.
     */
    public static DBToolkit getToolkit(){
        if(DBToolkit.instace == null){
            DBToolkit.instace = new DBToolkit();
        }
        return DBToolkit.instace;
    }

    /*
     * Gives the instance of the toolkit and initializes the credentials for the connection.
     */
    public static DBToolkit getToolkit(String user, String pass, String url){
        if(DBToolkit.instace == null){
            DBToolkit.instace = new DBToolkit();
            setCredentials(user, pass);
            setUrl(url);
        }
        return DBToolkit.instace;
    }

    public static void setCredentials(String user, String password){
        DBToolkit.user = user;
        DBToolkit.pass = password;
    }

    public static void setUrl(String url){
        DBToolkit.url = url;
    }

    public Connection getConnection(){
		try {
			if(DB == null){
                DB = DriverManager.getConnection(DBToolkit.url, DBToolkit.user, DBToolkit.pass);
            }
		}catch(SQLException ex) {
            ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Revisa tu conexion a internet");
		}
        return DB;
	}

    public void closeConnection(){
        if(DB != null){
            try {
                DB.close();
                DB = null;
                System.out.println("Connection closed");
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

     

}
