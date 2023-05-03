package Users;
import java.sql.*;

import javax.swing.JOptionPane;

import DataBaseConnection.*;

/*
 * General class for an user acccount with multiple types, 0 for Admin (full acces), 1 for employee(acces to products), 2 view only of products.
 */
public class Account {
        private int idemployee;
        private String name;
        private String department;
        private int type; 


        /*
         * Creates the account and saves the account on the data base (Only for new accounts)
         * 
         */
        public Account(String username, String password, String name, String department, int type ){
                this.name = name;
                this.department =department;
                this.type = type; 
                if(validateNewPassword(password, password) && validateUsername(username)){
                        saveAccount(username, password);
                }
        }

        /*
         * Creates the account instance with the existing account on the db
         */
        public Account(int idemployee){
                this.idemployee = idemployee;
                constructUser(idemployee);
        }

        
        public String getName(){
                return name;
        }
        public String getDepartment(){
                return department;
        }
        public int getPermisions(){
                return type;
        }

        public int getID(){
                return idemployee;
        }

        /*
         * Get data of an existing user with its idemployee and constructs the instance of the account
         */
        public void constructUser(int userID){
                Connection db = DBToolkit.getToolkit("root", "Legoishadow22", "jdbc:mysql://20.25.141.116:3306/Company").getConnection();
                try {
                        PreparedStatement query = db.prepareStatement("SELECT name, department, type FROM accounts WHERE idemployee=?");
                        query.setInt(1, userID);
                        ResultSet data = query.executeQuery();

                        if(data.next()){
                                name = data.getString("name");
                                department = data.getString("department");
                                type = data.getInt("type");
                        }
                } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }

        }

        /*
         * Validates if the given passwords are equal and meet the requirements for a password
         */
        public static boolean validateNewPassword(String s1, String s2){

		if(!s1.equals("") && s1.length()>=3 && s1.contains(" ") == false){

		}
		else {
			JOptionPane.showMessageDialog(null, "La nueva contraseña no cumple con los requerimentos: (Minimo 3 caracteres y sin espacio)", "Contraseña no permitida", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if(s1.equals(s2)){
			return true;
		}
		else{
			JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden", "Contraseñas no coinciden", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
        /*
         * Validates if the given username is not already used by another user
         */
        public static boolean validateUsername(String username){
                Connection db = DBToolkit.getToolkit("root", "Legoishadow22", "jdbc:mysql://20.25.141.116:3306/Company").getConnection();
                PreparedStatement query;
                try {
                        query = db.prepareStatement("SELECT username FROM accounts WHERE username=? ");
                        query.setString(1, username);
                        ResultSet data = query.executeQuery();
                        // If data base returns no result found, then the username does not exist, and can be used
                        if(!data.next()){
                                JOptionPane.showMessageDialog(null, "Noombre de usuario valido", "Todo bien :)", JOptionPane.INFORMATION_MESSAGE);
                                return true;
                        }
                } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        JOptionPane.showMessageDialog(null, "El usuario introducido ya existe, utiliza otro", "Usuario ya ocupado :()", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
                return false;
                

        }

        /*
         * save the new account on the db
         */
        public void saveAccount(String username, String password){
                Connection db = DBToolkit.getToolkit("root", "Legoishadow22", "jdbc:mysql://20.25.141.116:3306/Company").getConnection();
                try {
                        PreparedStatement insert = db.prepareStatement("INSERT INTO accounts (name, department, type, password, username) VALUES(?,?,?,?,?)");
                        insert.setString(1, name);
                        insert.setString(2, department);
                        insert.setInt(3, type);
                        insert.setString(4, password);
                        insert.setString(5, username);
                } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                }
        }



}
