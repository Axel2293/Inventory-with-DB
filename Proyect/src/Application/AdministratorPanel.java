package Application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import DataBaseConnection.DBToolkit;
import Users.Employee;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

/**
 * Window with a display of the selected table with some tools for modification like, add, delete, modify.
 * @Admin admin user can acces all tables
 * @Inventory inventory admin can only access the products table
 */
public class AdministratorPanel implements ActionListener {

	private JFrame frame;
	private JComboBox<String> comboBox;
	private DefaultTableModel tableModel;
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_3;
	private JTextField textField_4;

	private JToggleButton tglbtnNewToggleButton;
	private JToggleButton tglbtnNewToggleButton_2;
	
	private JButton btnNewButton;
	private JButton btnNewButton_2;
	private JButton btnNewButton_6;
	private JButton btnNewButton_1;
	private JButton btnNewButton_3;

	private Employee user;

	/**
	 * Launch the application.
	 */
	public static void CreateAdministratorPanel(Employee user) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdministratorPanel window = new AdministratorPanel(user);
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
	public AdministratorPanel(Employee user) {
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
		
		JLabel lblNewLabel_1 = new JLabel("Lumérico Panel de administración");
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
		table.setEnabled(false);
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
					tglbtnNewToggleButton_2.setEnabled(false);
				}
				else{
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
				}
				else{
					tglbtnNewToggleButton.setEnabled(true);
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
		
		btnNewButton = new JButton("Agregar");
		frame.getContentPane().add(btnNewButton, "cell 0 5,growx");
		btnNewButton.addActionListener(this);

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
		
		JLabel lblNewLabel_9 = new JLabel(""+user.getType());
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

		btnNewButton_1 = new JButton("Eliminar");
		frame.getContentPane().add(btnNewButton_1, "cell 0 6,growx");
		btnNewButton_1.addActionListener(this);
		
		btnNewButton_3 = new JButton("Modificar");
		btnNewButton_3.addActionListener(this);
		frame.getContentPane().add(btnNewButton_3, "cell 0 7,growx");
		
		JLabel lblNewLabel_2 = new JLabel("Tabla seleccionada:");
		frame.getContentPane().add(lblNewLabel_2, "cell 6 1");

	}

	/*
	 * Actions for the buttons. Every action is executed on a single thread
	 */
	public void actionPerformed(ActionEvent e){
		// Fire refresh of the table
		if (e.getSource() == btnNewButton_2) {
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run(){
					btnNewButton_2.setEnabled(false);
					updateTableData();
					btnNewButton_2.setEnabled(true);
				}
			});
		}
		else if (e.getSource() == btnNewButton_6){
			btnNewButton_6.setEnabled(false);
			frame.dispose();
			LoginWindow.createLoginWindow();
		}
		else if (e.getSource() == btnNewButton ) {
			AddTableRow.ShowAddTableRow((String) comboBox.getSelectedItem());
		}
		else if(e.getSource() == btnNewButton_1){
			DeleteRow.ShowDeleteRow((String) comboBox.getSelectedItem());
		}
		else if (e.getSource() ==btnNewButton_3 ) {
			try {
				int ID =Integer.parseInt( JOptionPane.showInputDialog(null, "Ingresa el ID del elemento a modificar en la tabla: "+comboBox.getSelectedItem()));
				ModifyRow.ShowModifyRow((String) comboBox.getSelectedItem(), ID);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(
					null, 
					"Error en el ID ingresado", 
					"Error al formatear ID", 
					JOptionPane.ERROR_MESSAGE);
			}
		}
	}



	/*
	 * Gets the available tables for the user depending on its type of account
	 * 	-Admins get full access to tables
	 * 	-Inventory admins get acces to the products table
	 */
	private Vector<String> fetchTables(){
		Connection db = DBToolkit.getToolkit().getConnection();
		Vector <String> names=new Vector<>();
		try {
			PreparedStatement query = db.prepareStatement("SHOW TABLES;");
			ResultSet tablesNames = query.executeQuery();
			while(tablesNames.next()) {
				// Only Admin type accounts get acces to the accounts table and tickets table
				if (user.getPermisions() == 0) {
					names.add(tablesNames.getString(1));
				}
				// Inventory admins get acces to the products table
				else if(tablesNames.getString(1).equals("products") && user.getPermisions()==2) {
					names.add(tablesNames.getString(1));
				}
			}
			return names;

		} catch (SQLException e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Ocurrio un error: "+e, "Error en la DB", JOptionPane.ERROR_MESSAGE);

		}
		return null;
	}	

	/*
	 * Update data on the table
	 */

	private void updateTableData(){
		DBToolkit.deleteTableData(tableModel, true, true);
		DBToolkit.showData(getTableData((String)comboBox.getSelectedItem()), tableModel, true );
	}

	/*
	 * Gets the data of a table with the specified filters (if activated)
	 */
	public ResultSet getTableData(String table_name){
		Connection db = DBToolkit.getToolkit().getConnection();

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
				
				query = db.prepareStatement("SELECT * FROM "+table_name+" WHERE "+columnFilter+" LIKE '%"+value+"%';");
				data = query.executeQuery();
				
			}
			// No Filter
			else{
				query = db.prepareStatement("SELECT * FROM "+table_name);
				data = query.executeQuery();
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Ocurrio un error: "+e, "Error en la DB", JOptionPane.ERROR_MESSAGE);
		}
		return data;
	}
	



}
