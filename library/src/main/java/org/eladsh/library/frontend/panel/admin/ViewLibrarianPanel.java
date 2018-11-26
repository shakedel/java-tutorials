package org.eladsh.library.frontend.panel.admin;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;
import java.util.function.Function;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.eladsh.library.backend.Library;
import org.eladsh.library.frontend.MainWindow.CARD;
import org.eladsh.library.frontend.panel.LibraryPanel;
import org.eladsh.library.model.Librarian;

public class ViewLibrarianPanel extends LibraryPanel {

	private static final long serialVersionUID = 1L;

	private static final String[] colNames = {"id", "name", "password", "email", "address", "city", "contact"};

	private final DefaultTableModel model;
	
	public ViewLibrarianPanel(Library lib, Function<CARD, Object> cards) {
		super(lib, cards);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
				
		model = new DefaultTableModel(new String[0][0], colNames);
		JTable table = new JTable(model);
		model.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				table.updateUI();
			}
		});
		JScrollPane scrollPane = new JScrollPane(table);
		
		addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateTable();
			}
			@Override public void componentResized(ComponentEvent e) { }
			@Override public void componentMoved(ComponentEvent e) { }
			@Override public void componentHidden(ComponentEvent e) { }
		});
		
		add(scrollPane);
		
		JButton btnBack = new JButton("Back");
		btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.apply(CARD.ADMIN_PANEL);
			}
		});
		add(btnBack);

	}
	
	private void updateTable() {
		List<Librarian> librarians = this.lib.getLibrarians();
		String[][] rows = new String[librarians.size()][colNames.length];
		int rowIdx = 0;
		for (Librarian librarian: librarians) {
			rows[rowIdx][0] = Long.toString(librarian.getId());
			rows[rowIdx][1] = librarian.getName();
			rows[rowIdx][2] = librarian.getPassword();
			rows[rowIdx][3] = librarian.getEmail();
			rows[rowIdx][4] = librarian.getAddress();
			rows[rowIdx][5] = librarian.getCity();
			rows[rowIdx][6] = librarian.getContactNo();
			rowIdx++;
		}
		model.setDataVector(rows, colNames);
	}

}
