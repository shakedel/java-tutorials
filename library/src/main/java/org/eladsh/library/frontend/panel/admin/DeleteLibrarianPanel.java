package org.eladsh.library.frontend.panel.admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Function;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.eladsh.library.backend.Library;
import org.eladsh.library.frontend.MainWindow.CARD;
import org.eladsh.library.frontend.panel.LibraryPanel;
import org.eladsh.library.frontend.panel.admin.dialog.DeleteLibrarianSuccess;
import org.eladsh.library.frontend.panel.admin.dialog.MustBeInteger;
import org.eladsh.library.frontend.panel.admin.dialog.NoSuchLibrarian;
import org.eladsh.library.model.Librarian;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class DeleteLibrarianPanel extends LibraryPanel {

	private static final long serialVersionUID = 1L;
	private JTextField idTextField;

	public DeleteLibrarianPanel(Library lib, Function<CARD, Object> cards) {
		super(lib, cards);
		
		this.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel lblEnter = new JLabel("Enter Id:");
		this.add(lblEnter, "2, 2, right, default");
		
		idTextField = new JTextField();
		this.add(idTextField, "4, 2, fill, default");
		idTextField.setColumns(10);
		
		JButton btnDetele = new JButton("Detele");
		btnDetele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					long id = Long.parseLong(idTextField.getText());
					Librarian deletedLibrarian = lib.deleteLibrarian(id);
					resetFields();
					if (deletedLibrarian == null) {
						JDialog dialog = new NoSuchLibrarian(id);
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setVisible(true);
					} else {
						JDialog dialog = new DeleteLibrarianSuccess(id);
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setVisible(true);
						cards.apply(CARD.ADMIN_PANEL);
					}
					resetFields();
				} catch(NumberFormatException nfe) {
					JDialog dialog = new MustBeInteger();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					resetFields();
				}
			}
		});
		this.add(btnDetele, "2, 4");
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetFields();
				cards.apply(CARD.ADMIN_PANEL);
			}
		});
		this.add(btnBack, "4, 4");
		
	}
	
	private void resetFields() {
		this.idTextField.setText("");
	}

}
