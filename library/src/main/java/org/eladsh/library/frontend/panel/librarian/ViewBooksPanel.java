package org.eladsh.library.frontend.panel.librarian;

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
import org.eladsh.library.model.Book;

public class ViewBooksPanel extends LibraryPanel {

	private static final long serialVersionUID = 1L;

	private static final String[] colNames = { "id", "Call No.", "name", "author", "publisher", "quantity", "issued",
			"date added" };

	private final DefaultTableModel model;

	public ViewBooksPanel(Library lib, Function<CARD, Object> cards) {
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
				cards.apply(CARD.LIBRARIAN_PANEL);
			}
		});
		add(btnBack);

	}

	private void updateTable() {
		List<Book> books = this.lib.getBooks();
		String[][] rows = new String[books.size()][colNames.length];
		int rowIdx = 0;
		for (Book book: books) {
			rows[rowIdx][0] = Long.toString(book.getId());
			rows[rowIdx][1] = book.getCallNo();
			rows[rowIdx][2] = book.getName();
			rows[rowIdx][3] = book.getAuthor();
			rows[rowIdx][4] = book.getPublisher();
			rows[rowIdx][5] = Integer.toString(book.getQuantity());
			rows[rowIdx][6] = Integer.toString(book.getIssued());
			rows[rowIdx][7] = book.getAddedDate().toString();
			rowIdx++;
		}
		model.setDataVector(rows, colNames);
	}
}
