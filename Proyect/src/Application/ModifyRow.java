package Application;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.Executors;

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
 * Lets the admin user modify a row with a specific ID on the selected table
 */
public class ModifyRow implements ActionListener{

    private JFrame frame;
	private JTable table;
	private DefaultTableModel m;

	private JButton btnNewButton;

	private String currentTable;
	private String primaryKey;
	private int ID;

    /**
	 * Launch the application.
	 */
	public static void ShowModifyRow(String tableName, int ID) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModifyRow window = new ModifyRow(tableName, ID);
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
	public ModifyRow(String tableName, int ID) {
		currentTable = tableName;
		this.ID = ID;
		primaryKey = DBToolkit.getPrimaryKey(currentTable);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 816, 150);
		frame.getContentPane().setLayout(new MigLayout("", "[grow]", "[][grow][]"));
		
		JLabel lblNewLabel = new JLabel("Modificando elemento con ID:"+ID+" en la Tabla: "+currentTable);
		frame.getContentPane().add(lblNewLabel, "cell 0 0");
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, "cell 0 1,grow");
		
		btnNewButton = new JButton("Guardar");
		frame.getContentPane().add(btnNewButton, "cell 0 2");
		btnNewButton.addActionListener(this);

		m = new DefaultTableModel(DBToolkit.getColumns(currentTable), 1);
		table = new JTable(m);
		scrollPane.setViewportView(table);
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@Override
			public void run(){
				getSavedData();
			}
		});
	}

	public void actionPerformed(ActionEvent e){
		modifyRow();
	}

	public void getSavedData(){
		Connection db = DBToolkit.getToolkit().getConnection();
		PreparedStatement query;
		ResultSet data;

		try {
			query = db.prepareStatement("SELECT * FROM "+ currentTable+" WHERE "+primaryKey+"="+ID+";");
			data = query.executeQuery();

			// If product exist then show the data
			if (data.next()) {
				for (int i = 0; i < m.getColumnCount(); i++) {
					m.setValueAt(data.getObject(i+2), 0, i);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

    public void modifyRow(){
        Connection db = DBToolkit.getToolkit().getConnection();
		PreparedStatement update;

		String expresion = new String("UPDATE "+currentTable+" SET ");
		int totalColumns = m.getColumnCount();
		for (int i = 0; i <totalColumns; i++) {
			expresion+=m.getColumnName(i)+"=?";
			if ((i+1)<totalColumns) {
				expresion+=",";
			}
		}
		expresion+=" WHERE "+primaryKey+"="+ID+";";

			
		try {
			update = db.prepareStatement(expresion);
			for (int i = 0; i < totalColumns; i++) {
				update.setObject(i+1, m.getValueAt(0, i));
				// System.out.println(m.getValueAt(0, i));
			}
			update.executeUpdate();
			JOptionPane.showMessageDialog(null, "Modificado con exito");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Algun dato introducido es erroneo", "Error", JOptionPane.ERROR_MESSAGE);
		}
    }
}
