package org.eladsh.library.frontend.panel.librarian;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Function;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.eladsh.library.backend.Library;
import org.eladsh.library.frontend.MainWindow.CARD;
import org.eladsh.library.frontend.panel.LibraryPanel;
import org.eladsh.library.frontend.panel.librarian.dialog.LibrarianLoginFailed;
import org.eladsh.library.model.Librarian;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class LibrarianLoginPanel extends LibraryPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField emailTextField;
	private JPasswordField passwordField;
	
	private void resetFields() {
		emailTextField.setText("");
		passwordField.setText("");
	}
	
	public LibrarianLoginPanel(Library lib, Function<CARD, Object> cards) {
		super(lib, cards);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JLabel lblNewLabel = new JLabel("Librarian Login Form");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(lblNewLabel);

		Component verticalStrut_2 = Box.createVerticalStrut(40);
		this.add(verticalStrut_2);

		JPanel panel = new JPanel();
		this.add(panel);
		panel.setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("8dlu"), FormSpecs.MIN_COLSPEC, ColumnSpec.decode("6dlu"),
						ColumnSpec.decode("180px"), FormSpecs.DEFAULT_COLSPEC, },
				new RowSpec[] { RowSpec.decode("31px"), FormSpecs.PARAGRAPH_GAP_ROWSPEC, RowSpec.decode("31px"), }));

		JLabel lblNewLabel_1 = new JLabel("Enter Email:");
		panel.add(lblNewLabel_1, "2, 1, fill, fill");

		emailTextField = new JTextField();
		panel.add(emailTextField, "4, 1, fill, fill");
		emailTextField.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Enter Password:");
		panel.add(lblNewLabel_2, "2, 3, fill, fill");

		passwordField = new JPasswordField();
		panel.add(passwordField, "4, 3, fill, fill");

		Component verticalStrut_3 = Box.createVerticalStrut(40);
		this.add(verticalStrut_3);

		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = emailTextField.getText();
				String pass = new String(passwordField.getPassword());
				resetFields();
				Librarian librarian = lib.getLibrarian(email, pass);
				if (librarian == null) {
					LibrarianLoginFailed dialog = new LibrarianLoginFailed(email);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					resetFields();
				} else {
					lib.logInLibrarian(librarian);
					cards.apply(CARD.LIBRARIAN_PANEL);
				}
			}
		});
		loginButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(loginButton);

		JButton btnNewButton_7 = new JButton("Back");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.apply(CARD.MAIN_PANEL);
			}
		});
		btnNewButton_7.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(btnNewButton_7);

		Component verticalStrut_4 = Box.createVerticalStrut(20);
		this.add(verticalStrut_4);

	}

}
