package org.eladsh.library.frontend.panel.librarian;

import java.util.function.Function;

import org.eladsh.library.backend.Library;
import org.eladsh.library.frontend.MainWindow.CARD;
import org.eladsh.library.frontend.panel.LibraryPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LibrarianPanel extends LibraryPanel {

	private static final long serialVersionUID = 1L;

	public LibrarianPanel(Library lib, Function<CARD, Object> cards) {
		super(lib, cards);
		setLayout(new GridLayout(7, 1, 0, 10));
		
		JLabel lblNewLabel = new JLabel("Librarian Section");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel);
		
		JButton addBooksButton = new JButton("Add Books");
		addBooksButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.apply(CARD.ADD_BOOKS_PANEL);
			}
		});
		add(addBooksButton);
		
		JButton viewBooksButton = new JButton("View Books");
		viewBooksButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.apply(CARD.VIEW_BOOKS_PANEL);
			}
		});
		add(viewBooksButton);
		
		JButton IssueBookButton = new JButton("Issue Book");
		IssueBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.apply(CARD.ISSUE_BOOK_PANEL);
			}
		});
		add(IssueBookButton);
		
		JButton btnViewIssuedBooks = new JButton("View Issued Books");
		btnViewIssuedBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.apply(CARD.VIEW_ISSUED_BOOKS_PANEL);
			}
		});
		add(btnViewIssuedBooks);
		
		JButton btnReturnBook = new JButton("Return Book");
		btnReturnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.apply(CARD.RETURN_BOOK_PANEL);
			}
		});
		add(btnReturnBook);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lib.logoutLibrarian();
				cards.apply(CARD.MAIN_PANEL);
			}
		});
		add(btnLogout);

	}

}
