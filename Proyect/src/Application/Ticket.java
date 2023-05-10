package Application;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Image;


/**
 * Displays the given ticket on a text field
 */
public class Ticket {

	private JFrame frmGraciasPorTu;
	private String ticket;

	/**
	 * Launch the application.
	 */
	public static void ShowTicket(String ticket) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ticket window = new Ticket(ticket);
					window.frmGraciasPorTu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application and innitialize the ticket to display
	 */
	public Ticket(String ticket) {
		this.ticket = ticket;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGraciasPorTu = new JFrame();
		frmGraciasPorTu.setTitle("Gracias por tu compra");
		frmGraciasPorTu.setBounds(100, 100, 408, 672);
		frmGraciasPorTu.getContentPane().setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		Double scale = 0.18;
		JLabel lblNewLabel = new JLabel("          Lumérico, S.A DE C.V");
		ImageIcon logo = new ImageIcon("Proyect/src/Resource/Empresa.png");
		Image scaledImage = logo.getImage().getScaledInstance((int)(logo.getIconWidth()*scale), (int)(logo.getIconHeight()*scale), Image.SCALE_SMOOTH);
		lblNewLabel.setIcon(new ImageIcon(scaledImage));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		frmGraciasPorTu.getContentPane().add(lblNewLabel, "cell 0 0");
		
		JScrollPane scrollPane = new JScrollPane();
		frmGraciasPorTu.getContentPane().add(scrollPane, "cell 0 1,grow");
		
		// Create the textArea witht all the ticket string
		JTextArea textArea = new JTextArea(ticket);
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
	}
}
