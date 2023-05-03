package Application;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.concurrent.Executors;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.Color;
import net.miginfocom.swing.MigLayout;

import DataBaseConnection.*;
import Users.Account;

public class LoginWindow implements ActionListener{

	private JFrame frmLoginIteso;
	private JTextField textField;
	private JPasswordField passwordField;
	JButton btnNewButton;
	JButton btnNewButton_1;
	JButton btnNewButton_2;
	JButton btnNewButton_3;

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
		frmLoginIteso.setTitle("Login ITESO");
		// frmLoginIteso.setIconImage();
		// frmLoginIteso.setAlwaysOnTop(true);
		frmLoginIteso.getContentPane().setBackground(new Color(221, 221, 221));
		frmLoginIteso.setBackground(new Color(128, 128, 128));
		frmLoginIteso.setResizable(false);
		frmLoginIteso.setBounds(100, 100, 360, 340);
		frmLoginIteso.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	
		
		// Action listener del login
		btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(this);
		frmLoginIteso.getContentPane().setLayout(new MigLayout("", "[58px][230px]", "[69px][30px][30px][48px][23px][23px][23px]"));
		
		double scale=0.3;
		// ImageIcon logoIco= new ImageIcon(getClass().getResource("/resource/account.png"));
		JLabel lblNewLabel_3 = new JLabel("Company");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 17));
		// logoIco.setImage(logoIco.getImage().getScaledInstance((int)(logoIco.getIconWidth()*scale), (int)(logoIco.getIconHeight()*scale), Image.SCALE_AREA_AVERAGING));
		// lblNewLabel_3.setIcon(logoIco);
		frmLoginIteso.getContentPane().add(lblNewLabel_3, "cell 1 0,grow, aligny top");

		JLabel lblNewLabel = new JLabel("Username");
		frmLoginIteso.getContentPane().add(lblNewLabel, "cell 0 1,grow");
		
		textField = new JTextField();
		frmLoginIteso.getContentPane().add(textField, "cell 1 1,grow");
		textField.setColumns(10);
		frmLoginIteso.getContentPane().add(btnNewButton, "cell 1 3,grow");
		
		passwordField = new JPasswordField();
		frmLoginIteso.getContentPane().add(passwordField, "cell 1 2,grow");
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		frmLoginIteso.getContentPane().add(lblNewLabel_2, "cell 0 2,grow");
		
		btnNewButton_1 = new JButton("Crear cuenta");
		frmLoginIteso.getContentPane().add(btnNewButton_1, "cell 1 5,growx,aligny top");
		btnNewButton_1.addActionListener(this);

		// btnNewButton_2 = new JButton("Cambiar contraseña");
		// frmLoginIteso.getContentPane().add(btnNewButton_2, "cell 1 4,growx,aligny top");
		// btnNewButton_2.addActionListener(this);

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
					loginAcount();
				}
			});

		}
		// Change password button
		else if(e.getSource() == btnNewButton_2) {
			PasswordChangeWindow.createPasswordChangeWindow();
		}
		// Create user window
		else if(e.getSource() == btnNewButton_1){
			CreateUserWIndow.initCreateUserWIndow();
		}
	}


	public void loginAcount(){
		// Get credentials
		String  username= textField.getText();
		String  password= new String(passwordField.getPassword());
		
		//  Conection to MySQL remote DB
		DBToolkit db = DBToolkit.getToolkit("root", "Legoishadow22", "jdbc:mysql://20.25.141.116:3306/Company");
		Connection c1 = db.getConnection();
		
		ResultSet res = null;
		try {
			PreparedStatement credentialsVerification = c1.prepareStatement("SELECT idemployee FROM accounts WHERE username=? AND password=?;");
			credentialsVerification.setString(1, username);
			credentialsVerification.setString(2, password);
			res= credentialsVerification.executeQuery();
			
			//.next will return false if the ResultSet is empty
			if (res.next()) {
				Integer userID =  res.getInt("idemployee");
				frmLoginIteso.dispose();
				
				// OPEN THE TABLE WINDOW AFTER LOGIN
				Account loged = new Account(userID.intValue());
				ShowTableWindow.CreateTableWindow(loged);
				res.close();
				
			}
			else{
				JOptionPane.showMessageDialog(null,"Usuario no encontrado o la contraseña es erronea");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null,"Error");
		}
	}
}
