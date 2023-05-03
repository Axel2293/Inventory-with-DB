package Application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import DataBaseConnection.DBToolkit;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PasswordChangeWindow extends Thread implements ActionListener{

	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	JButton btnNewButton;
	JButton btnNewButton_1;

	/**
	 * Launch the application.
	 */
	public static void createPasswordChangeWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PasswordChangeWindow window = new PasswordChangeWindow();
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
	public PasswordChangeWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[][][grow]", "[][][][][][][][]"));
		
		JLabel lblNewLabel_3 = new JLabel("Cambiar Contraseña");
		lblNewLabel_3.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		frame.getContentPane().add(lblNewLabel_3, "cell 1 0");
	
		JLabel lblNewLabel = new JLabel("Usuario");
		frame.getContentPane().add(lblNewLabel, "cell 1 2,alignx trailing");
		
		textField = new JTextField();
		frame.getContentPane().add(textField, "cell 2 2,growx");
		textField.setColumns(10);	
		
		JLabel lblNewLabel_1 = new JLabel("Nueva contraseña");
		frame.getContentPane().add(lblNewLabel_1, "cell 1 4,alignx trailing");
		
		passwordField = new JPasswordField();
		frame.getContentPane().add(passwordField, "cell 2 4,growx");
		
		JLabel lblNewLabel_2 = new JLabel("Confirmar");
		frame.getContentPane().add(lblNewLabel_2, "cell 1 5,alignx trailing");
		
		passwordField_1 = new JPasswordField();
		frame.getContentPane().add(passwordField_1, "cell 2 5,growx");
		
		btnNewButton = new JButton("Cambiar");
		frame.getContentPane().add(btnNewButton, "flowx,cell 2 7");
		btnNewButton.addActionListener(this);

		btnNewButton_1 = new JButton("Validar contraseña");
		frame.getContentPane().add(btnNewButton_1, "cell 2 7");
		btnNewButton_1.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e){
		if (e.getSource() == btnNewButton) {
			run();
		}
		else if(e.getSource() == btnNewButton_1){
			String ps1 = new String(passwordField.getPassword());
			String ps2 = new String(passwordField_1.getPassword());
			
			if (validateNewPassword(ps1, ps2)) {
				JOptionPane.showMessageDialog(null, "Contraseña valida :-) ");
			}
		}
	}

	@Override
	public void run(){
			changePasswordDB();
	}

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

	public void changePasswordDB(){
		String ps1 = new String(passwordField.getPassword());
			String ps2 = new String(passwordField_1.getPassword());
			String usr = textField.getText();

			try {
				Connection db = DBToolkit.getToolkit().getConnection();
				PreparedStatement p1 =  db.prepareStatement("SELECT ID, PASSWORD FROM students WHERE USERNAME=?");
				p1.setString(1, usr);
				int ID;
				ResultSet r1 = p1.executeQuery();
				
				// Find the User with the username
				if (r1.next()) {
					ID = r1.getInt("ID");
					if(validateNewPassword(ps1, ps2)){
						//Update the password
						p1 = db.prepareStatement("UPDATE students SET PASSWORD=? WHERE ID=?");
						p1.setString(1, ps1);
						p1.setInt(2, ID);
						p1.executeUpdate();
						JOptionPane.showMessageDialog(null, "Contraseña guardada con exito :-)");
						frame.dispose();
					}
				}
				
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Revisa tus datos", "Datos erroneos", JOptionPane.ERROR_MESSAGE);
			}
	}

}
