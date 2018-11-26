package org.eladsh.library.frontend.panel.librarian;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Function;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import org.eladsh.library.backend.Library;
import org.eladsh.library.frontend.MainWindow.CARD;
import org.eladsh.library.frontend.panel.LibraryPanel;
import org.eladsh.library.frontend.panel.librarian.dialog.AddBookFailed;
import org.eladsh.library.frontend.panel.librarian.dialog.AddBookSuccess;
import org.eladsh.library.model.Book;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class AddBooksPanel extends LibraryPanel {

	private static final long serialVersionUID = 1L;
	
	private static final Integer DEFAULT_QUANTITY = new Integer(1);
	private JTextField txtCallnofield;
	private JTextField txtNameField;
	private JTextField txtAuthorField;
	private JTextField txtPublisherField;
	private JSpinner quantitySpinner;
	
	private void resetFields() {
		txtCallnofield.setText("");   
		txtNameField.setText("");     
		txtAuthorField.setText("");   
		txtPublisherField.setText("");
		quantitySpinner.setValue(DEFAULT_QUANTITY);
	}

	public AddBooksPanel(Library lib, Function<CARD, Object> cards) {
		super(lib, cards);
		setLayout(new GridLayout(4, 1, 0, 20));
		
		JLabel lblAddBooks = new JLabel("Add Books");
		lblAddBooks.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblAddBooks.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblAddBooks);
		
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel lblCallNo = new JLabel("Call No:");
		panel.add(lblCallNo, "1, 1, right, default");
		
		txtCallnofield = new JTextField();
		panel.add(txtCallnofield, "2, 1, fill, default");
		txtCallnofield.setColumns(10);
		
		JLabel lblName = new JLabel("Name:");
		panel.add(lblName, "1, 2, right, default");
		
		txtNameField = new JTextField();
		panel.add(txtNameField, "2, 2, fill, default");
		txtNameField.setColumns(10);
		
		JLabel lblAuthor = new JLabel("Author:");
		panel.add(lblAuthor, "1, 3, right, default");
		
		txtAuthorField = new JTextField();
		panel.add(txtAuthorField, "2, 3, fill, default");
		txtAuthorField.setColumns(10);
		
		JLabel lblPublisher = new JLabel("Publisher:");
		panel.add(lblPublisher, "1, 4, right, default");
		
		txtPublisherField = new JTextField();
		panel.add(txtPublisherField, "2, 4, fill, default");
		txtPublisherField.setColumns(10);
		
		JLabel lblQuantity = new JLabel("Quantity:");
		panel.add(lblQuantity, "1, 5");
		
		quantitySpinner = new JSpinner();
		quantitySpinner.setModel(new SpinnerNumberModel(DEFAULT_QUANTITY, new Integer(1), null, new Integer(1)));
		panel.add(quantitySpinner, "2, 5");
		
		JButton btnAddBooks = new JButton("Add Books");
		btnAddBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Book book = new Book();
				book.setCallNo(txtCallnofield.getText());
				book.setName(txtNameField.getText());
				book.setPublisher(txtPublisherField.getText());
		        book.setAuthor(txtAuthorField.getText());
		        book.setQuantity(quantitySpinner.getComponentCount());
				
		        resetFields();

				Book addedBook = lib.addBook(book);
				if (addedBook == null) {
					JDialog dialog = new AddBookFailed(book);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} else {
					JDialog dialog = new AddBookSuccess();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					cards.apply(CARD.LIBRARIAN_PANEL);
				}
			}
		});
		add(btnAddBooks);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetFields();
				cards.apply(CARD.LIBRARIAN_PANEL);
			}
		});
		add(btnBack);

	}

}
