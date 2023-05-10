package DataBaseConnection;

import java.sql.*;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
                                                                                                                                                                                                                        

/**
 * Tools for the connection, modification, insertion or delition of data in the data base of the company. Constructed as a singletone.
 *  @Extra Static methods to manage columns and rows of a Default table model
 */
public class DBToolkit {

    //  User and password to make the connecton to the data base 
    private  String pass=null;
    private  String user=null;
    // Url of the data base
    private  String url = null;

    // Unique instance of the toolkit
    private static DBToolkit instace = null;

    // Open connection to the database
    private Connection DB = null;
    
    private DBToolkit(String user, String pass, String url){
        setCredentials(user, pass);
        setUrl(url);
    }

    /*
     * Gives the instance of the toolkit and initializes the credentials for the connection.
     */
    public static DBToolkit getToolkit(String user, String pass, String url){
        if(DBToolkit.instace == null){
            DBToolkit.instace = new DBToolkit(user, pass, url);

        }
        return DBToolkit.instace;
    }

    public static DBToolkit getToolkit(){
        if(DBToolkit.instace == null){
            DBToolkit.instace = new DBToolkit(null, null, null);

        }
        return DBToolkit.instace;
    }

    public void setCredentials(String user, String password){
        this.user = user;
        pass = password;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public Connection getConnection(){
		try {
			if(DB == null){
                DB = DriverManager.getConnection(url, user, pass);
            }
		}catch(SQLException ex) {
            ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Revisa tu conexion a internet"+user+pass+url);
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

    /*
     * Removes all the data that its on the table model
     */
    public static void deleteTableData(DefaultTableModel tbl, boolean deleteRows, boolean deleteColumns){
		// Remove rows
		if (deleteRows) {
            int rows = tbl.getRowCount();
            for(int i=0; i<rows; i++){
                // JOptionPane.showMessageDialog(null, "Removing row at "+ i);
                try {
                    tbl.removeRow(0);
                } catch (Exception e) {
                    System.out.println("Indx aout of bounds");
                }
            }
            tbl.setNumRows(0);
            tbl.fireTableDataChanged();
        }
		// Delete Columns
		if (deleteColumns) {
            tbl.setColumnCount(0);
        }
	}

    /*
     * Takes the result set from a consult and puts the data on a table model
     */
    public static void showData(ResultSet data, DefaultTableModel tbl, boolean addColumns){
        ResultSetMetaData meta;
        try {

            meta = data.getMetaData();
             int columnsLen = meta.getColumnCount();
			if (addColumns) {
                //Gets meta data to get the columns names
                // Get column names
                for(int i=1; i<=columnsLen; i++){
                    tbl.addColumn(meta.getColumnName(i));
                }
            }

            // Get rows data
            while(data.next()){

                Vector<Object> row= new Vector<>();

                //Save the data in the vector
                for(int i=1; i<=columnsLen; i++){
					// Store column info in vector
                    row.addElement(data.getObject(i));
                }
				// Add row vector to table
                tbl.addRow(row);
            }
			data.close();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("HOLAAAAAAAA");
            e.printStackTrace();
            
        }

		
	}

    public static Vector<String> getColumns(String currentTable){
		Vector<String> columns = new Vector<>(); 

		// Add the columns for the table on the vector
		if (currentTable.equals("accounts")) {
			columns.add("Nombre"); columns.add("Apellidos"); 
			columns.add("Departamento"); columns.add("Tipo"); 
            columns.add("Contraseña");columns.add("Usuario") ;
		}
		else if (currentTable.equals("products")) {
			columns.add("Nombre"); columns.add("Descripcion"); 
			columns.add("Tipo"); columns.add("Precio");
			columns.add("Cantidad") ;
		}
		else if (currentTable.equals("tickets")) {
			columns.add("Cliente"); columns.add("CajeroID"); 
			columns.add("Total"); columns.add("Fecha");
		}
		return columns;
	}

    public static String getPrimaryKey(String tableName){
        if (tableName.equals("accounts")) {
            return "empleadoID";
        }
        else if (tableName.equals("products")) {
            return "productoID";
        }else if (tableName.equals("tickets")) {
            return "ticketID";
        }
        else{
            return null;
        }
    }
}
