package Application;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import DataBaseConnection.DBToolkit;
import net.miginfocom.swing.MigLayout;

/**
 * Lets the admin user delete a row with a specific ID on the selected table
 */
public class DeleteRow implements ActionListener{

	private JFrame frame;
	private JTextField textField;
	private String currentTable;

	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void ShowDeleteRow(String currentTable) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeleteRow window = new DeleteRow(currentTable);
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
	public DeleteRow(String currentTable) {
		this.currentTable = currentTable;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 129);
		frame.getContentPane().setLayout(new MigLayout("", "[][grow]", "[][][][]"));
		
		JLabel lblNewLabel = new JLabel("Eliminar fila en tabla: "+currentTable);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		frame.getContentPane().add(lblNewLabel, "cell 0 0");
		
		JLabel lblNewLabel_1 = new JLabel("ID");
		frame.getContentPane().add(lblNewLabel_1, "cell 0 1,alignx trailing");
		
		textField = new JTextField();
		frame.getContentPane().add(textField, "cell 1 1,growx");
		textField.setColumns(10);
		
		btnNewButton = new JButton("Eliminar");
		frame.getContentPane().add(btnNewButton, "cell 1 2,growx");
		btnNewButton.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e ){
		deleteRow();
	}

	private void deleteRow(){
		Connection db =DBToolkit.getToolkit().getConnection();
		PreparedStatement update;

		try {
			String expresion ="DELETE FROM "+currentTable+" WHERE ";
			if (currentTable.equals("accounts")) {
				expresion+="empleadoID = ?;";
			}
			else if (currentTable.equals("products")) {
				expresion+="productoID = ?;";
			}
			else if(currentTable.equals("tickets")){
				expresion+="ticketID = ?;";
			}
			else{
				expresion = null;
			}

			update = db.prepareStatement(expresion);
			update.setObject(1, textField.getText());
			update.executeUpdate();
			JOptionPane.showMessageDialog(null, "Eliminado con exito");
		} catch (SQLException e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Error al eliminar la fila");
		}
		
	}

}
