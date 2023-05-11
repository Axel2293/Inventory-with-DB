package Application;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.concurrent.Executors;
import java.awt.event.ActionEvent;
import javax.swing.*;

import DataBaseConnection.*;
import Users.Administrator;
import Users.Cashier;
import Users.Employee;
import Users.InvalidAccountType;
import Users.InventoryAdmin;

/**
 * Main login portal
 */
public class LoginWindow implements ActionListener{

	private JFrame frmLoginIteso;
	private JTextField textField;
	private JPasswordField passwordField;
	private JButton btnNewButton;


	/**
	 * Launch the application.
	*/

	public static void createLoginWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow window = new LoginWindow();
					window.frmLoginIteso.setVisible(true);
					window.frmLoginIteso.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});



	}

	/**
	 * Create the application.
	 */
	private LoginWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLoginIteso = new JFrame();
		frmLoginIteso.setResizable(false);
		frmLoginIteso.setBounds(100, 100, 450, 275);
		frmLoginIteso.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLoginIteso.getContentPane().setLayout(null);
		frmLoginIteso.setTitle("Login Lumérico");
		
		textField = new JTextField();
		textField.setBounds(123, 111, 173, 20);
		frmLoginIteso.getContentPane().add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(123, 142, 173, 20);
		frmLoginIteso.getContentPane().add(passwordField);
		passwordField.setColumns(10);
		
		Double scale = 0.2;
		ImageIcon logo = new ImageIcon("Proyect/src/Resource/Empresa.png");
		
		JLabel lblNewLabel = new JLabel("");
        Image imagenEscalada = logo.getImage().getScaledInstance((int)(logo.getIconWidth()*scale), (int)(logo.getIconHeight()*scale), Image.SCALE_SMOOTH);
		lblNewLabel.setIcon(new ImageIcon(imagenEscalada));
		lblNewLabel.setBounds(158, 11, 102, 89);
		frmLoginIteso.getContentPane().add(lblNewLabel);
		
		btnNewButton = new JButton("Acceder");
		btnNewButton.setBounds(123, 174, 173, 23);
		frmLoginIteso.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(this);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("Proyect/src/Resource/background.png"));
		lblNewLabel_1.setBounds(0, 0, 434, 261);
		frmLoginIteso.getContentPane().add(lblNewLabel_1);
	}
	
	/*
	 * Actions for the buttons
	 */
	public void actionPerformed(ActionEvent e) {
		// Login button
		if (e.getSource() == btnNewButton) {
			//Runn the command on a different thread
			Executors.newSingleThreadExecutor().execute(new Runnable() {
				@Override
				public void run(){
					btnNewButton.setEnabled(false);
					try {
						loginAcount();
					} catch (InvalidAccountType e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, e, "Tipo de cuenta invalido", JOptionPane.ERROR_MESSAGE);
					}
					btnNewButton.setEnabled(true);
				}
			});
		}
	}


	public void loginAcount() throws InvalidAccountType{
		// Get credentials
		String  username= textField.getText();
		String  password= new String(passwordField.getPassword());
		
		//  Conection to MySQL remote DB
		DBToolkit db = DBToolkit.getToolkit();
		Connection c1 = db.getConnection();
		
		ResultSet res = null;
		try {
			PreparedStatement credentialsVerification = c1.prepareStatement("SELECT * FROM accounts WHERE Usuario=? AND Contraseña=?;");
			credentialsVerification.setString(1, username);
			credentialsVerification.setString(2, password);
			res= credentialsVerification.executeQuery();
			
			//.next will return false if the ResultSet is empty
			if (res.next()) {
				Integer userID =  res.getInt("empleadoID");
				Integer type = res.getInt("Tipo");
				String name = res.getString("Nombre");
				String surname = res.getString("Apellidos");
				String department = res.getString("Departamento");
				frmLoginIteso.dispose();
				
				// Instantiate the users class with its data depending on the type
				Employee loged;
				// Administrator
				if (type == 0) {
					loged = new Administrator(name, surname, userID, department);
				}
				// Cashier
				else if (type == 1) {
					loged = new Cashier(name, surname, userID, department);
				}
				// Inventory administrator
				else if (type == 2) {
					loged = new InventoryAdmin(name, surname, userID, department);
				}
				else{
					throw new InvalidAccountType(type);
				}

				if (loged.getPermisions() == 1) {
					CashierWindow.CreateCashierWindow(loged);
				}
				else if (loged.getPermisions() == 0 || loged.getPermisions() == 2) {
					AdministratorPanel.CreateAdministratorPanel(loged);
				}
				else{
					throw new InvalidAccountType(loged.getPermisions());
				}

				
			}
			else{
				JOptionPane.showMessageDialog(null,"Usuario no encontrado o la contraseña es erronea");
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,"Error en la base de datos");
		}
	}
}
