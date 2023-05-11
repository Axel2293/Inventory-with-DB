package Application;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import DataBaseConnection.DBToolkit;

/**
 * Lets the admin add a new row-element to the table
 */
public class AddTableRow implements ActionListener{

	private JFrame frame;
	
	private JTable table;
	private DefaultTableModel m;

	private JButton btnNewButton;

	private String currentTable;

	/**
	 * Launch the application.
	 */
	public static void ShowAddTableRow(String tableName) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddTableRow window = new AddTableRow(tableName);
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
	public AddTableRow(String tableName) {
		currentTable = tableName;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 816, 150);
		frame.getContentPane().setLayout(new MigLayout("", "[grow]", "[][grow][]"));
		
		JLabel lblNewLabel = new JLabel("Tabla: "+currentTable);
		frame.getContentPane().add(lblNewLabel, "cell 0 0");
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, "cell 0 1,grow");
		
		btnNewButton = new JButton("Guardar");
		frame.getContentPane().add(btnNewButton, "cell 0 2");
		btnNewButton.addActionListener(this);

		m = new DefaultTableModel(DBToolkit.getColumns(currentTable), 1);
		table = new JTable(m);
		scrollPane.setViewportView(table);
	}

	public void actionPerformed(ActionEvent e){
		saveRow();
	}

	/*
	 * Gets the data on the table displayed to the administrator and saves the data on the selecter table of the data base
	 * From this tool the admin can not set a primary key value as that is automatically handeled by the DB
	 */
	public void saveRow(){
		Connection db = DBToolkit.getToolkit().getConnection();
		PreparedStatement update;

		String expresion = new String("INSERT INTO "+currentTable+" (");
		int totalColumns = m.getColumnCount();
		// Add the columns names for the expresion
		for (int i = 0; i <totalColumns; i++) {
			expresion+=m.getColumnName(i);
			if ((i+1)<totalColumns) {
				expresion+=",";
			}
		}
		expresion+=") VALUES (";
		// Add the parameters for later addition
		for (int i = 0; i < totalColumns; i++) {
			expresion+="?";
			if ((i+1)<totalColumns) {
				expresion+=",";
			}
		}
		expresion+=");";

		try {
			update = db.prepareStatement(expresion);
			for (int i = 0; i < totalColumns; i++) {
				// Add the parameters values
				update.setObject(i+1, m.getValueAt(0, i));
			}
			// Add the row to the table
			update.executeUpdate();
			JOptionPane.showMessageDialog(null, "Agregado con exito");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Algun dato introducido es erroneo: "+e, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
