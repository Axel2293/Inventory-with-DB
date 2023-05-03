package Application;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import DataBaseConnection.DBToolkit;

import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.concurrent.Executors;

import net.miginfocom.swing.MigLayout;

public class CreateUserWIndow {

	private JFrame frmCrearUsuarioIteso;
	private JTextField textField;
	private JPasswordField passwordField;
	private final JSeparator separator_1 = new JSeparator();
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;

	/**
	 * Launch the application.
	 */
	public static void initCreateUserWIndow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateUserWIndow window = new CreateUserWIndow();
					window.frmCrearUsuarioIteso.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	private CreateUserWIndow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCrearUsuarioIteso = new JFrame();
		frmCrearUsuarioIteso.setTitle("Crear usuario ITESO");
		frmCrearUsuarioIteso.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\axelw\\Desktop\\app\\src\\ITESO-logo-D1724C4595-seeklogo.com.png"));
		frmCrearUsuarioIteso.setBounds(100, 100, 450, 366);
		// frmCrearUsuarioIteso.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCrearUsuarioIteso.getContentPane().setLayout(new MigLayout("", "[146px][6px][276px]", "[14px][1px][20px][20px][14px][1px][20px][20px][20px][20px][20px][14px][1px][20px][20px][1px][23px]"));
		
		JLabel lblNewLabel = new JLabel("Informacion de inicio de sesión");
		frmCrearUsuarioIteso.getContentPane().add(lblNewLabel, "cell 0 0,alignx left,aligny top");
		
		JSeparator separator = new JSeparator();
		frmCrearUsuarioIteso.getContentPane().add(separator, "cell 0 1,grow");
		
		JLabel lblNewLabel_1 = new JLabel("Usuario");
		frmCrearUsuarioIteso.getContentPane().add(lblNewLabel_1, "cell 0 2,alignx right,aligny top");
		
		// USER TEXT FIELD
		textField = new JTextField();
		frmCrearUsuarioIteso.getContentPane().add(textField, "cell 2 2,growx,aligny top");
		textField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Contraseña");
		frmCrearUsuarioIteso.getContentPane().add(lblNewLabel_2, "cell 0 3,alignx right,aligny center");
		
		// PASSWORD  FIELD
		passwordField = new JPasswordField();
		frmCrearUsuarioIteso.getContentPane().add(passwordField, "cell 2 3,growx,aligny top");
		
		JLabel lblNewLabel_11 = new JLabel("Datos personales");
		frmCrearUsuarioIteso.getContentPane().add(lblNewLabel_11, "cell 0 4,growx,aligny top");
		frmCrearUsuarioIteso.getContentPane().add(separator_1, "cell 0 5,grow");
		
		JLabel lblNewLabel_3 = new JLabel("Nombre");
		frmCrearUsuarioIteso.getContentPane().add(lblNewLabel_3, "cell 0 6,alignx right,aligny center");
		
		// Name text field
		textField_4 = new JTextField();
		frmCrearUsuarioIteso.getContentPane().add(textField_4, "cell 2 6,growx,aligny top");
		textField_4.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Apellidos");
		frmCrearUsuarioIteso.getContentPane().add(lblNewLabel_4, "cell 0 7,alignx right,aligny center");
		
		// Apellidos text field
		textField_3 = new JTextField();
		frmCrearUsuarioIteso.getContentPane().add(textField_3, "cell 2 7,growx,aligny top");
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Carrera");
		frmCrearUsuarioIteso.getContentPane().add(lblNewLabel_5, "cell 0 8,alignx right,aligny center");
		
		// Carrera text field
		textField_2 = new JTextField();
		frmCrearUsuarioIteso.getContentPane().add(textField_2, "cell 2 8,growx,aligny top");
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Edad");
		frmCrearUsuarioIteso.getContentPane().add(lblNewLabel_6, "cell 0 9,alignx right,aligny center");
		
		// Edad text field
		textField_1 = new JTextField();
		frmCrearUsuarioIteso.getContentPane().add(textField_1, "cell 2 9,growx,aligny top");
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_12 = new JLabel("Fecha de nacimiento");
		frmCrearUsuarioIteso.getContentPane().add(lblNewLabel_12, "cell 0 10,alignx right,aligny center");
		
		// Decha de nacimiento text field
		textField_7 = new JTextField();
		frmCrearUsuarioIteso.getContentPane().add(textField_7, "cell 2 10,growx,aligny top");
		textField_7.setColumns(10);
	

		JLabel lblNewLabel_7 = new JLabel("Dirección");
		frmCrearUsuarioIteso.getContentPane().add(lblNewLabel_7, "cell 0 11,growx,aligny top");
		
		JSeparator separator_2 = new JSeparator();
		frmCrearUsuarioIteso.getContentPane().add(separator_2, "cell 0 12,grow");
		
		JLabel lblNewLabel_8 = new JLabel("Calle");
		frmCrearUsuarioIteso.getContentPane().add(lblNewLabel_8, "cell 0 13,alignx right,aligny center");
		
		// Calle text field
		textField_5 = new JTextField();
		frmCrearUsuarioIteso.getContentPane().add(textField_5, "cell 2 13,growx,aligny top");
		textField_5.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("Numero");
		frmCrearUsuarioIteso.getContentPane().add(lblNewLabel_9, "cell 0 14,alignx right,aligny center");
		
		// Numero text field
		textField_6 = new JTextField();
		frmCrearUsuarioIteso.getContentPane().add(textField_6, "cell 2 14,growx,aligny top");
		textField_6.setColumns(10);
		
		JSeparator separator_3 = new JSeparator();
		frmCrearUsuarioIteso.getContentPane().add(separator_3, "cell 0 15 3 1,grow");
		
		JButton btnNewButton = new JButton("Crear usuario");
		frmCrearUsuarioIteso.getContentPane().add(btnNewButton, "cell 0 16 3 1,growx,aligny top");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				Executors.newSingleThreadExecutor().execute(new Runnable() {
					@Override
					public void run(){
						createUserDB();
					}
				});
			}
		});
	}

	private void createUserDB(){
		String newPass= new String(passwordField.getPassword());

		if(PasswordChangeWindow.validateNewPassword(newPass, newPass)){
			try {
				Connection db = DBToolkit.getToolkit().getConnection();
				PreparedStatement createUser = db.prepareStatement("INSERT INTO students (USERNAME, PASSWORD, NOMBRE, APELLIDOS, CARRERA, EDAD, FECHANACIM, CALLE, NUMERO) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
				createUser.setString(1, textField.getText());
				createUser.setString(2, newPass);
				
				
				// NOMBRE
				createUser.setString(3, textField_4.getText());
				// APELLIDOS
				createUser.setString(4, textField_3.getText());
				// CARRERA
				createUser.setString(5, textField_2.getText());	
				// EDAD
				if (!textField_1.getText().equals("")) {
					createUser.setInt(6, Integer.parseInt(textField_1.getText()));
				}else{
					createUser.setInt(6, 0);
				}
				// DATE
				if (!textField_7.getText().equals("")) {
					createUser.setDate(7, Date.valueOf(textField_7.getText()));
				}else{
					createUser.setDate(7, Date.valueOf("2000-01-01"));
				}
				// CALLE
				createUser.setString(8, textField_5.getText());
				// NUMERO
				if (!textField_6.getText().equals("")) {
					createUser.setInt(9, Integer.parseInt(textField_6.getText()));
				}else{
					createUser.setInt(9, 0);
				}

				createUser.executeUpdate();
				JOptionPane.showMessageDialog(null,"Usuario creado con exito");
				frmCrearUsuarioIteso.dispose();

			} catch (Exception exception) {
				JOptionPane.showMessageDialog(null,"Error al crear usuario");
				exception.printStackTrace();
			}
		}
	}

}
