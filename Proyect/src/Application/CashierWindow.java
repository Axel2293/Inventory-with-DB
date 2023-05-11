package Application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;
import java.util.Date;
import java.util.concurrent.Executors;

import javax.swing.table.DefaultTableModel;

import DataBaseConnection.DBToolkit;
import Users.Employee;

import javax.swing.border.BevelBorder;

/**
 * Displays the selling panel to the cashier. Cart system, add-remove operations and a complete transaction that removes the sold products from the DB and generates a ticket.
 */
public class CashierWindow implements ActionListener{

	private JFrame frmPuntoDeVenta;
	private JTextField textField;
	private JTable table;
	private JTable table_1;
    private DefaultTableModel m1;
    private DefaultTableModel m2;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JLabel lblNewLabel_1;
	
    private JButton btnNewButton;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JButton btnNewButton_1;
	private JButton btnNewButton_4;

    private Employee loged;
	private HashMap <Integer, Vector<Object>> cart = new HashMap<>();
	private Double total = 0.0;

	/**
	 * Launch the application.
	 */
	public static void CreateCashierWindow(Employee user) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CashierWindow window = new CashierWindow(user);
					window.frmPuntoDeVenta.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CashierWindow(Employee user) {
		loged = user;
        initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPuntoDeVenta = new JFrame();
		frmPuntoDeVenta.setTitle("Punto de venta");
		frmPuntoDeVenta.setBounds(100, 100, 1187, 735);
		frmPuntoDeVenta.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPuntoDeVenta.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 11, 395, 674);
		frmPuntoDeVenta.getContentPane().add(panel);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(31, 40, 199, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		btnNewButton = new JButton("Buscar");
		btnNewButton.setBounds(249, 39, 120, 23);
		panel.add(btnNewButton);
        btnNewButton.addActionListener(this);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 660, 375, -600);
		panel.add(scrollPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(10, 66, 375, 597);
		panel.add(panel_1);
		panel_1.setLayout(new MigLayout("", "[357px]", "[601px]"));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1, "cell 0 0,grow");
		
        m1 = new DefaultTableModel();
		table = new JTable(m1);
		table.setEnabled(false);
		scrollPane_1.setViewportView(table);
		
		JLabel lblNewLabel_4 = new JLabel("Lista de productos");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_4.setBounds(10, 15, 161, 14);
		panel.add(lblNewLabel_4);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setBounds(415, 11, 746, 201);
		frmPuntoDeVenta.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Agregar producto al carrito");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_3.setBounds(10, 11, 214, 19);
		panel_2.add(lblNewLabel_3);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBounds(10, 41, 470, 109);
		panel_2.add(panel_6);
		panel_6.setLayout(new MigLayout("", "[][grow]", "[][][][]"));
		
		JLabel lblNewLabel_5 = new JLabel("ID del producto");
		panel_6.add(lblNewLabel_5, "cell 0 0,alignx trailing");
		
		textField_1 = new JTextField();
		textField_1.setText("");
		panel_6.add(textField_1, "cell 1 0,growx");
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Cantidad");
		panel_6.add(lblNewLabel_6, "cell 0 1,alignx trailing");
		
		textField_2 = new JTextField();
		panel_6.add(textField_2, "cell 1 1,growx");
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Cliente");
		panel_6.add(lblNewLabel_7, "cell 0 2,alignx trailing");
		
		textField_3 = new JTextField();
		panel_6.add(textField_3, "cell 1 2,growx");
		textField_3.setColumns(10);
		
		btnNewButton_2 = new JButton("Agregar");
		panel_6.add(btnNewButton_2, "flowx,cell 1 3");
		btnNewButton_2.addActionListener(this);
		
		btnNewButton_3 = new JButton("Eliminar");
		panel_6.add(btnNewButton_3, "cell 1 3");
		btnNewButton_3.addActionListener(this);
		
		btnNewButton_4 = new JButton("Cerrar sesion");
		btnNewButton_4.setBounds(10, 167, 120, 23);
		panel_2.add(btnNewButton_4);
		btnNewButton_4.addActionListener(this);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_3.setBounds(415, 223, 746, 413);
		frmPuntoDeVenta.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_5.setBounds(10, 30, 726, 372);
		panel_3.add(panel_5);
		panel_5.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		panel_5.add(scrollPane_2, "cell 0 0,grow");
		
        m2 = new DefaultTableModel();
		table_1 = new JTable(m2);
		table_1.setEnabled(false);
		scrollPane_2.setViewportView(table_1);
		
		JLabel lblNewLabel_2 = new JLabel("Carrito");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(10, 11, 81, 19);
		panel_3.add(lblNewLabel_2);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_4.setBounds(669, 647, 218, 38);
		frmPuntoDeVenta.getContentPane().add(panel_4);
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel = new JLabel("Total");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_4.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("$");
		panel_4.add(lblNewLabel_1);
		
		btnNewButton_1 = new JButton("Completar compra");
		btnNewButton_1.setBounds(944, 647, 191, 38);
		frmPuntoDeVenta.getContentPane().add(btnNewButton_1);
		btnNewButton_1.addActionListener(this);

        // First search
        updateProductsTable(textField.getText(), true);

		// Add columnns for the cart
		m2.addColumn("Producto");
		m2.addColumn("Cantidad");
		m2.addColumn("Precio c/u");
		m2.addColumn("ID");
	}

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == btnNewButton) {
            String search = textField.getText();
			updateProductsTable(search, false);
        }
		else if (e.getSource() == btnNewButton_2){
			Executors.newSingleThreadExecutor().execute(new Runnable() {
				@Override
				public void run(){
					addCartProduct(textField_1.getText(), textField_2.getText());
				}
			});
		}
		else if (e.getSource() == btnNewButton_3){
			Executors.newSingleThreadExecutor().execute(new Runnable() {
				@Override 
				public void run(){
					removeCartProduct(textField_1.getText(), textField_2.getText());
				}
			});
		}
		else if(e.getSource() == btnNewButton_1){
			Executors.newSingleThreadExecutor().execute(new Runnable() {
				@Override
				public void run(){
					pushTransaction(textField_3.getText(), loged.getID(), total);
				}
			});
		}
		else if (e.getSource() ==btnNewButton_4 ) {
			frmPuntoDeVenta.dispose();
			LoginWindow.createLoginWindow();

		}
    }

    private void updateProductsTable(String search, boolean addColumns){

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                // Get products table data
				btnNewButton.setEnabled(false);
                ResultSet data = getProductsData(search);
                DBToolkit.deleteTableData(m1, true, false);
                // Show the products data
                DBToolkit.showData(data, m1, addColumns);
				btnNewButton.setEnabled(true);

            }
        });
    }

    private ResultSet getProductsData(String search){
        DBToolkit db = DBToolkit.getToolkit();
        Connection c1 = db.getConnection();
        ResultSet data = null;

        try {
			// No filter
            if (search == "") {
                PreparedStatement query = c1.prepareStatement("SELECT  productoID, Nombre, Precio FROM products;");
                data = query.executeQuery();
            }
            // Search for similar products with the given name
            else{
                PreparedStatement query = c1.prepareStatement("SELECT productoID, Nombre, Precio FROM products WHERE Nombre LIKE '%"+search+"%' ;");
                data = query.executeQuery();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error", "Error en la DB", JOptionPane.ERROR_MESSAGE);
        }
        return data;
    }

    private void addCartProduct(String id, String quantity){
		DBToolkit db = DBToolkit.getToolkit();
        Connection c1 = db.getConnection();
        ResultSet data = null;

		try {
			int productID = Integer.parseInt(id);
			int qt = Integer.parseInt(quantity);
			// Get the name price and quantity in inventory from the product
			PreparedStatement query = c1.prepareStatement("SELECT Precio, Cantidad, Nombre FROM products WHERE productoID=?;");
			query.setInt(1, productID);
			data = query.executeQuery();

			// If product exist int the DB and entered data is not out of range
			if (data.next()  && qt >0 && productID>=0) {
				// if its not already in the cart
				if (!cart.containsKey(productID) && checkInventory(data.getInt("Cantidad"), qt)) {
					Vector <Object> pdct = new Vector<>();
					// Name
					pdct.add(data.getString(3));
					// Quantity
					pdct.add(qt);
					// price
					pdct.add(data.getDouble(1));
					// Add the id
					pdct.add(productID);
					// Add the product to the HashMap of the cart
					cart.put(productID, pdct);
					// Add the row to the rable
					m2.addRow(pdct);
					// Update the total
					addTotal(qt, data.getDouble(1));
				}
				// The product is already on the shoping list
				else if(cart.containsKey(productID)){
					// Get the product on the cart
					Vector <Object> pdInCart = cart.get(productID);
					Integer total = (Integer) pdInCart.get(1);
					if (checkInventory(data.getInt("Cantidad") , qt+total)) {
						total+=qt;
						pdInCart.setElementAt(total, 1);
						m2.fireTableDataChanged();
						addTotal(qt, data.getDouble(1));
					}
					else{
						JOptionPane.showMessageDialog(null, "No hay inventario suficiente para el producto: "+data.getString("Nombre"), "Inventario insuficiente", JOptionPane.INFORMATION_MESSAGE);
					}
					
				}
				else {
					JOptionPane.showMessageDialog(null, "No hay inventario suficiente para el producto: "+data.getString("Nombre"), "Inventario insuficiente", JOptionPane.INFORMATION_MESSAGE);
				}
				

			}else {
				JOptionPane.showMessageDialog(null, "Producto no encontrado o los datos son erroneos", "No encontrado", JOptionPane.ERROR_MESSAGE);
			}


		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "El id o la cantidad no son numeros", "Error", JOptionPane.ERROR_MESSAGE);
		} catch(SQLException ex){
			JOptionPane.showMessageDialog(null, "Error en al base de datos", "Error", JOptionPane.ERROR_MESSAGE);
		}
    }

	private void removeCartProduct(String id, String cantidad) {
        try {
			// Parse the data to integer
            int productID = Integer.parseInt(id);
            int qt = Integer.parseInt(cantidad);

			// Verify if the product is in the cart (HashMap)
            if (cart.containsKey(productID) && qt > 0) {
				// Get the vector from the hash map
                Vector<Object> pdInCart = cart.get(productID);
                int totalQty = (Integer) pdInCart.get(1);

				// Remove the selected quantity from the cart
                if (totalQty > qt) {
                    totalQty -= qt;
                    pdInCart.setElementAt(totalQty, 1);
                    m2.fireTableDataChanged();
                    restTotal(qt, (Double) pdInCart.get(2));
                }
				// Equals then remove product from the cart 
				else if (totalQty == qt) {
					// Remove the product from the hash map
                    cart.remove(productID);

					// Cicle through the rows of the table to find the product
                    for (int i = 0; i < m2.getRowCount(); i++) {
                        if ((Integer) m2.getValueAt(i, 3) == productID) {
							// Delete the row
                            m2.removeRow(i);
                            break;
                        }
                    }
					// Subtracts the removed products price from the total
                    restTotal(qt, (Double) pdInCart.get(2));
                } else {
                    JOptionPane.showMessageDialog(null, "La cantidad a eliminar es mayor a la cantidad en el carrito", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Producto no encontrado en el carrito o los datos son erroneos", "No encontrado", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El id o la cantidad no son numeros", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void restTotal(int quantity, double price) {
        total -= (double) quantity * price;

        DecimalFormat df = new DecimalFormat(".####");

        lblNewLabel_1.setText("$" + df.format(total));
	}

	private void addTotal(int quantity, double price){
		total += (double)quantity*price;

		DecimalFormat df = new DecimalFormat(".####");
		
		lblNewLabel_1.setText("$"+df.format(total));
	}

	private void pushTransaction(String client, int cashierID, double total){
		DBToolkit db = DBToolkit.getToolkit();
		Connection c1 = db.getConnection();
		String ticket = new String("Empresa: Lumérico\n");
		// Get the current date and time

		Date d1 = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(d1);
		
		ticket+="Fecha: "+cal.get(Calendar.DAY_OF_MONTH)+"/ "+cal.get(Calendar.MONTH) + "/"+cal.get(Calendar.YEAR)+"\n";
		ticket+="Cliente: "+client+"\n";
		ticket+="Cajero: "+cashierID+"\n";

		ticket+="--------------------------------------------------------------------------------------------------\n";
		// Cycle through the rows of th etable
		for(int i=0; i<m2.getRowCount(); i++){
			int totalCurrent = (Integer) m2.getValueAt(i, 1);
			int idCurrent = (Integer) m2.getValueAt(i, 3);
			Double price = (Double) m2.getValueAt(i, 2);
			String productName = (String) m2.getValueAt(i, 0);

			// System.out.println(totalCurrent+" | "+idCurrent);
			removeFromInventory(idCurrent, totalCurrent, c1);
			ticket+=totalCurrent+" - "+productName + " | Precio c/u: "+price+"\n";


		}
		ticket+="--------------------------------------------------------------------------------------------------\n";
		ticket+="\tTotal: "+total;
		
		

		// Send the transaction data to the db
		try {
			PreparedStatement update = c1.prepareStatement("INSERT INTO tickets (Cliente, CajeroID, Total, Fecha) VALUES (?,?,?,?)");
			update.setString(1, textField_3.getText());
			update.setInt(2, cashierID);
			update.setDouble(3, total);
			update.setDate(4, new java.sql.Date(d1.getTime()));
			update.execute();
		
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Error al registrar el ticket", "Error", JOptionPane.ERROR_MESSAGE);
		}

		Ticket.ShowTicket(ticket);
		// Clean the cart table
		DBToolkit.deleteTableData(m2, true, false);
		// Delete the cart hash map elements
		cart.clear();
	}

	private void removeFromInventory(int id, int quantity, Connection c1){
		
		try {
			// Get the quantity alter the inventory
			PreparedStatement query = c1.prepareStatement("SELECT Cantidad FROM products WHERE productoID=?");
			query.setInt(1,id ); 
			ResultSet data = query.executeQuery();

			if (data.next()) {
				// Get the quantity in the inventory
				int pdCurrent = data.getInt("Cantidad");
				if (pdCurrent >= quantity) {
					// Alter the inventory
					PreparedStatement update = c1.prepareStatement("UPDATE products SET Cantidad=? WHERE productoID=?;");
					update.setInt(1, pdCurrent - quantity);
					update.setInt(2, id);
					update.executeUpdate();
				}
				else{
					JOptionPane.showMessageDialog(null, "No hay suficientes inventario para el producto: "+id, "Error", JOptionPane.ERROR_MESSAGE);
				}

			}
			else{
				JOptionPane.showMessageDialog(null, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
			}


		} catch (SQLException e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Algo salio mal", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private boolean checkInventory(int invQT, int buy){
		if (invQT < buy) {
			return false;
		}else {
			return true;
		}
	}
    

}

