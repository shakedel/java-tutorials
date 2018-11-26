package org.eladsh.library.frontend.panel.admin;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Function;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.eladsh.library.backend.Library;
import org.eladsh.library.frontend.MainWindow.CARD;
import org.eladsh.library.frontend.panel.LibraryPanel;
import org.eladsh.library.frontend.panel.admin.dialog.AddLibrarianFailed;
import org.eladsh.library.frontend.panel.admin.dialog.AddLibrarianSuccess;
import org.eladsh.library.model.Librarian;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class AddLibrarianPanel extends LibraryPanel {

	private static final long serialVersionUID = 1L;

	private JTextField addLibrarianNameField;
	private JPasswordField addLibrarianPasswordField;
	private JTextField addLibrarianEmailField;
	private JTextField addLibrarianAddressField;
	private JTextField addLibrarianCityField;
	private JTextField addLibrarianContactField;
	
	private void resetFields() {
		addLibrarianNameField.setText("");
		addLibrarianPasswordField.setText("");
		addLibrarianEmailField.setText("");
		addLibrarianAddressField.setText("");
		addLibrarianCityField.setText("");
		addLibrarianContactField.setText("");
	}
	
	public AddLibrarianPanel(Library lib, Function<CARD, Object> cards) {
		super(lib, cards);
		this.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("left:default"),
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel lblNewLabel_4 = new JLabel("Add Librarian");
		lblNewLabel_4.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 24));
		this.add(lblNewLabel_4, "1, 2, fill, fill");
		
		JLabel addLibrarianNameLabel = new JLabel("Name:");
		addLibrarianNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(addLibrarianNameLabel, "1, 3, fill, fill");
		
		addLibrarianNameField = new JTextField();
		this.add(addLibrarianNameField, "2, 3, fill, fill");
		addLibrarianNameField.setColumns(10);
		
		JLabel addLibrarianPassLabel = new JLabel("Password:");
		this.add(addLibrarianPassLabel, "1, 4, left, default");
		
		addLibrarianPasswordField = new JPasswordField();
		this.add(addLibrarianPasswordField, "2, 4, fill, default");
		
		JLabel addLibrarianEmailLabel = new JLabel("email:");
		this.add(addLibrarianEmailLabel, "1, 5, left, default");
		
		addLibrarianEmailField = new JTextField();
		this.add(addLibrarianEmailField, "2, 5, fill, default");
		addLibrarianEmailField.setColumns(10);
		
		JLabel addLibrarianAddressLabel = new JLabel("Address:");
		this.add(addLibrarianAddressLabel, "1, 6, left, default");
		
		addLibrarianAddressField = new JTextField();
		this.add(addLibrarianAddressField, "2, 6, fill, default");
		addLibrarianAddressField.setColumns(10);
		
		JLabel addLibrarianCityLabel = new JLabel("City:");
		this.add(addLibrarianCityLabel, "1, 7, left, default");
		
		addLibrarianCityField = new JTextField();
		this.add(addLibrarianCityField, "2, 7, fill, default");
		addLibrarianCityField.setColumns(10);
		
		JLabel addLibrarianContactLabel = new JLabel("Contact No");
		this.add(addLibrarianContactLabel, "1, 9, left, default");
		
		addLibrarianContactField = new JTextField();
		this.add(addLibrarianContactField, "2, 9, fill, default");
		addLibrarianContactField.setColumns(10);
		
		JButton btnNewButton_4 = new JButton("Add Librarian");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Librarian librarian = new Librarian();
				librarian.setName(addLibrarianNameField.getText());
				librarian.setPassword(new String(addLibrarianPasswordField.getPassword()));
				librarian.setEmail(addLibrarianEmailField.getText());
		        librarian.setAddress(addLibrarianAddressField.getText());
		        librarian.setCity(addLibrarianCityField.getText());
		        librarian.setContactNo(addLibrarianContactField.getText());
				
		        resetFields();

				Librarian addedLibrarian = lib.addLibrarian(librarian);
				if (addedLibrarian == null) {
					JDialog dialog = new AddLibrarianFailed(librarian);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} else {
					JDialog dialog = new AddLibrarianSuccess();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					cards.apply(CARD.ADMIN_PANEL);
				}
			}
		});
		this.add(btnNewButton_4, "1, 11");
		
		JButton btnBack = new JButton("back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetFields();
				cards.apply(CARD.ADMIN_PANEL);
			}
		});
		this.add(btnBack, "2, 11");
		
	}

}
