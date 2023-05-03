package Application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import DataBaseConnection.DBToolkit;
import Users.Account;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

public class ShowTableWindow implements ActionListener {

	private JFrame frame;
	private JComboBox<String> comboBox;
	private DefaultTableModel tableModel;
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

	private JToggleButton tglbtnNewToggleButton;
	private JToggleButton tglbtnNewToggleButton_1;
	private JToggleButton tglbtnNewToggleButton_2;

	private JButton btnNewButton_2;
	private JButton btnNewButton_6;

	private Account user;

	/**
	 * Launch the application.
	 */
	public static void CreateTableWindow(Account user) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShowTableWindow window = new ShowTableWindow(user);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 */
	public ShowTableWindow(Account user) {
		// Save the user account data from the login
		this.user = user;
		// Build de components
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1140, 590);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[grow][][grow][][][][][][][][][][][][][][][][][][][][grow][][][][][][][][][]", "[][][grow][grow][][][][][]"));
		
		JLabel lblNewLabel_1 = new JLabel("Company");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 17));
		frame.getContentPane().add(lblNewLabel_1, "flowx,cell 0 0 7 2");
		
		// Vector that stores the names of the tables
		Vector<String> tables = fetchTables();

		comboBox = new JComboBox<>(tables);
		frame.getContentPane().add(comboBox, "cell 7 1 24 1,growx");
		
		// Refresh table button
		btnNewButton_2 = new JButton("Actualizar");
		frame.getContentPane().add(btnNewButton_2, "cell 31 1");
		btnNewButton_2.addActionListener(this);
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, "cell 0 2 32 1,grow");
		
		tableModel = new DefaultTableModel();
		table = new JTable(tableModel);
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Tabla");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		frame.getContentPane().add(lblNewLabel, "cell 0 3");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		frame.getContentPane().add(panel_1, "cell 2 3 1 6,grow");
		panel_1.setLayout(new MigLayout("", "[][grow][][grow][][][][][][][][][][]", "[][][][][][][]"));
		
		JLabel lblNewLabel_12 = new JLabel("Filtros");
		lblNewLabel_12.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_1.add(lblNewLabel_12, "flowy,cell 0 0");
		
		JSeparator separator_5 = new JSeparator();
		panel_1.add(separator_5, "cell 0 0,growx");
		
		JButton btnNewButton_5 = new JButton("Guardar y actualizar");
		panel_1.add(btnNewButton_5, "cell 3 0");
		
		JLabel lblNewLabel_13 = new JLabel("Rango de filas:");
		lblNewLabel_13.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_13, "cell 0 1,alignx trailing");
		
		textField = new JTextField();
		panel_1.add(textField, "cell 1 1,growx");
		textField.setColumns(10);
		
		JLabel lblNewLabel_14 = new JLabel("a");
		panel_1.add(lblNewLabel_14, "cell 2 1,alignx trailing");
		
		textField_1 = new JTextField();
		panel_1.add(textField_1, "cell 3 1 2 1,growx");
		textField_1.setColumns(10);
		
		// Activates the filter with a specific amount of rows A to B
		tglbtnNewToggleButton = new JToggleButton("Activar");
		panel_1.add(tglbtnNewToggleButton, "cell 5 1");
		tglbtnNewToggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				if(tglbtnNewToggleButton.isSelected()){
					tglbtnNewToggleButton_1.setEnabled(false);
					tglbtnNewToggleButton_2.setEnabled(false);
				}
				else{
					tglbtnNewToggleButton_1.setEnabled(true);
					tglbtnNewToggleButton_2.setEnabled(true);
				}
			}
		});
		
		JLabel lblNewLabel_15 = new JLabel("Filtrar por llave primaria:");
		lblNewLabel_15.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_15, "cell 0 2,alignx trailing");
		
		textField_2 = new JTextField();
		panel_1.add(textField_2, "cell 1 2 4 1,growx");
		textField_2.setColumns(10);
		
		// Activates the search by primary key
		 tglbtnNewToggleButton_1 = new JToggleButton("Activar");
		panel_1.add(tglbtnNewToggleButton_1, "cell 5 2");
		tglbtnNewToggleButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				if(tglbtnNewToggleButton_1.isSelected()){
					tglbtnNewToggleButton.setEnabled(false);
					tglbtnNewToggleButton_2.setEnabled(false);
				}
				else{
					tglbtnNewToggleButton.setEnabled(true);
					tglbtnNewToggleButton_2.setEnabled(true);
				}
			}
		});
		
		JLabel lblNewLabel_16 = new JLabel("Filtrar por valor de columna:");
		lblNewLabel_16.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_16, "cell 0 3 1 2,alignx right");
		
		JLabel lblNewLabel_17 = new JLabel("Columna");
		panel_1.add(lblNewLabel_17, "cell 1 4,alignx right");
		
		textField_3 = new JTextField();
		panel_1.add(textField_3, "cell 2 4 3 1,growx");
		textField_3.setColumns(10);
		
		// Activates the search by column
		 tglbtnNewToggleButton_2 = new JToggleButton("Activar");
		panel_1.add(tglbtnNewToggleButton_2, "cell 5 4");
		tglbtnNewToggleButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				if(tglbtnNewToggleButton_2.isSelected()){
					tglbtnNewToggleButton.setEnabled(false);
					tglbtnNewToggleButton_1.setEnabled(false);
				}
				else{
					tglbtnNewToggleButton.setEnabled(true);
					tglbtnNewToggleButton_1.setEnabled(true);
				}
			}
		});

		
		JLabel lblNewLabel_18 = new JLabel("Valor");
		panel_1.add(lblNewLabel_18, "cell 1 5,alignx right");
		
		textField_4 = new JTextField();
		panel_1.add(textField_4, "cell 2 5 3 1,growx");
		textField_4.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		frame.getContentPane().add(separator_1, "cell 0 4,growx");
		
		JButton btnNewButton = new JButton("Agregar");
		frame.getContentPane().add(btnNewButton, "cell 0 5,growx");
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		frame.getContentPane().add(panel, "cell 4 3 28 6,grow");
		panel.setLayout(new MigLayout("", "[][]", "[][][][][][][][][]"));
		
		JLabel lblNewLabel_3 = new JLabel("Datos de cuenta");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel.add(lblNewLabel_3, "cell 0 0");
		
		JSeparator separator = new JSeparator();
		panel.add(separator, "cell 0 1,growx");
		
		JLabel lblNewLabel_4 = new JLabel("Nombre: ");
		panel.add(lblNewLabel_4, "cell 0 2");
		
		JLabel lblNewLabel_7 = new JLabel(user.getName());
		panel.add(lblNewLabel_7, "cell 1 2");
		
		JSeparator separator_2 = new JSeparator();
		panel.add(separator_2, "cell 0 3 2 1,growx");
		
		JLabel lblNewLabel_6 = new JLabel("Departamento: ");
		panel.add(lblNewLabel_6, "cell 0 4");
		
		JLabel lblNewLabel_8 = new JLabel(user.getDepartment());
		panel.add(lblNewLabel_8, "cell 1 4");
		
		JSeparator separator_3 = new JSeparator();
		panel.add(separator_3, "cell 0 5 2 1,growx");
		
		JLabel lblNewLabel_5 = new JLabel("Tipo de cuenta: ");
		panel.add(lblNewLabel_5, "cell 0 6");
		
		JLabel lblNewLabel_9 = new JLabel(""+user.getPermisions());
		panel.add(lblNewLabel_9, "cell 1 6");
		
		JSeparator separator_4 = new JSeparator();
		panel.add(separator_4, "cell 0 7 2 1,growx");
		
		JLabel lblNewLabel_10 = new JLabel("ID de empleado: ");
		panel.add(lblNewLabel_10, "cell 0 8");
		
		JLabel lblNewLabel_11 = new JLabel(""+user.getID());
		panel.add(lblNewLabel_11, "cell 1 8");
		
		// CHANGE ACCOUNT BUTTON
		btnNewButton_6 = new JButton("Cambiar de cuenta");
		panel.add(btnNewButton_6, "cell 1 9");
		btnNewButton_6.addActionListener(this);

		JButton btnNewButton_1 = new JButton("Eliminar");
		frame.getContentPane().add(btnNewButton_1, "cell 0 6,growx");
		
		JButton btnNewButton_3 = new JButton("Modificar");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		frame.getContentPane().add(btnNewButton_3, "cell 0 7,growx");
		
		JButton btnNewButton_4 = new JButton("Consultar con ID");
		frame.getContentPane().add(btnNewButton_4, "cell 0 8,growx");
		
		JLabel lblNewLabel_2 = new JLabel("Tabla seleccionada:");
		frame.getContentPane().add(lblNewLabel_2, "cell 6 1");

		if (hasWritePermission(user.getPermisions()) == false) {
			System.out.println("Only read permisions");
			btnNewButton_3.setEnabled(false);
			btnNewButton_1.setEnabled(false);
			btnNewButton.setEnabled(false);

		}

	}

	// Actions for the buttons. Every action is executed on a single thread
	public void actionPerformed(ActionEvent e){
		// Fire refresh of the table
		if (e.getSource() == btnNewButton_2) {
			Executors.newSingleThreadExecutor().execute(new Runnable() {
				@Override
				public void run(){
					updateTableData();
				}
			});
		}
		else if (e.getSource() == btnNewButton_6){
			frame.dispose();
			LoginWindow.createLoginWindow();
		}

		
	}



	/*
	 * Gets the available tables for the user depending on its type of account
	 */
	private Vector<String> fetchTables(){
		Connection db = DBToolkit.getToolkit("root", "Legoishadow22", "jdbc:mysql://20.25.141.116:3306/Company").getConnection();
		Vector <String> names=new Vector<>();
		try {
			PreparedStatement query = db.prepareStatement("SHOW TABLES;");
			ResultSet tablesNames = query.executeQuery();
			while(tablesNames.next()) {
				// Only Admin type accounts get acces to the accounts table
				if (tablesNames.getString(1).equals("accounts") && user.getPermisions() == 0) {
					names.add(tablesNames.getString(1));
					System.out.println(tablesNames.getString(1));
				}
				// Rest of accounts get access to the rest of tables
				else if(!tablesNames.getString(1).equals("accounts")) {
					names.add(tablesNames.getString(1));
					System.out.println(tablesNames.getString(1));
				}
			}
			return names;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/*
	 * Deletes all the data currently stored in the table
	 */

	private void deleteTableData(){
		// Remove rows
		int rows = table.getRowCount();
		for(int i=0; i<rows; i++){
			// JOptionPane.showMessageDialog(null, "Removing row at "+ i);
			tableModel.removeRow(0);
		}
		// Delete Tables
		tableModel.setColumnCount(0);
	}

	/*
	 * Update data on the table
	 */

	private void updateTableData(){
		deleteTableData();
		showData(getTableData((String)comboBox.getSelectedItem()));
	}

	public ResultSet getTableData(String table_name){
		Connection db = DBToolkit.getToolkit("root", "Legoishadow22", "jdbc:mysql://20.25.141.116:3306/Company").getConnection();

		ResultSet data = null;
		try {
			PreparedStatement query;
			

			// Filter only by rows in the table
			if(tglbtnNewToggleButton.isSelected()){
				int a=Integer.parseInt(textField.getText()) , b = Integer.parseInt(textField_1.getText());
				query = db.prepareStatement("SELECT * FROM "+table_name+" LIMIT ?,?;");
				query.setInt(1, a);
				query.setInt(2, b);
				data = query.executeQuery();
			}
			// Filter only by special column value
			else if(tglbtnNewToggleButton_2.isSelected()){
				String columnFilter=textField_3.getText();
				String value = textField_4.getText();
				
				query = db.prepareStatement("SELECT * FROM "+table_name+" WHERE "+columnFilter+"=?;");
				query.setObject(1, value);
				data = query.executeQuery();
				
			}
			//  Filter only by primary key
			else if(tglbtnNewToggleButton_1.isSelected()){
				// query = db.prepareStatement("SELECT * FROM "+table_name+" WHERE "+columnFilter+"=?;");
				// query.setObject(1, value);
				// data = query.executeQuery();
			}
			// No Filter
			else{
				query = db.prepareStatement("SELECT * FROM "+table_name);
				data = query.executeQuery();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}


	public void showData(ResultSet data){
        ResultSetMetaData meta;
        try {

			//Gets meta data to get the columns names
            meta = data.getMetaData();
		    int columnsLen = meta.getColumnCount();
        
            // Get column names
            for(int i=1; i<=columnsLen; i++){
				tableModel.addColumn(meta.getColumnName(i));
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
                tableModel.addRow(row);
            }
			data.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

		
	}
	
	public boolean hasWritePermission(int type){
		if(type != 2 && type <2  && type >=0){
			return true;
		}return false;
	}


}
