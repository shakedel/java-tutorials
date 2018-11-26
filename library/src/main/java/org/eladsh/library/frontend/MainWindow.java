package org.eladsh.library.frontend;

import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.function.Function;

import javax.swing.JFrame;

import org.eladsh.library.backend.Library;
import org.eladsh.library.frontend.panel.MainPanel;
import org.eladsh.library.frontend.panel.admin.AddLibrarianPanel;
import org.eladsh.library.frontend.panel.admin.AdminLoginPanel;
import org.eladsh.library.frontend.panel.admin.AdminPanel;
import org.eladsh.library.frontend.panel.admin.DeleteLibrarianPanel;
import org.eladsh.library.frontend.panel.admin.ViewLibrarianPanel;
import org.eladsh.library.frontend.panel.librarian.AddBooksPanel;
import org.eladsh.library.frontend.panel.librarian.IssueBookPanel;
import org.eladsh.library.frontend.panel.librarian.LibrarianLoginPanel;
import org.eladsh.library.frontend.panel.librarian.LibrarianPanel;
import org.eladsh.library.frontend.panel.librarian.ReturnBookPanel;
import org.eladsh.library.frontend.panel.librarian.ViewBooksPanel;
import org.eladsh.library.frontend.panel.librarian.ViewIssuedBooksPanel;

public class MainWindow {

	public static enum CARD {
		MAIN_PANEL,
		ADMIN_LOGIN_PANEL,
		ADMIN_PANEL,
		ADD_LIBRARIAN_PANEL,
		VIEW_LIBRARIAN_PANEL,
		DELETE_LIBRARIAN_PANEL,
		LIBRARIAN_LOGIN_PANEL,
		LIBRARIAN_PANEL,
		ADD_BOOKS_PANEL,
		VIEW_BOOKS_PANEL,
		ISSUE_BOOK_PANEL,
		VIEW_ISSUED_BOOKS_PANEL,
		RETURN_BOOK_PANEL
	}
	
	private JFrame frame;
	
	public void setVisibleFrame(boolean visible) {
		this.frame.setVisible(visible);
	}
	
	private final Library lib;
	
	public MainWindow() {
		this.lib = Library.get();
		initialize();
	}
	
	private Function<CARD, Object> cards = new Function<CARD, Object>() {
		@Override
		public Object apply(CARD c) {
			CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
			cl.show(frame.getContentPane(), c.name());
			return null;
		}
	};

	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(1, 5));
		
		frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	try {
					lib.close();
				} catch (IOException e1) {
					throw new RuntimeException("Failed closing lib");
				}
            }
		});
		
		frame.getContentPane().add(new MainPanel(lib, cards), CARD.MAIN_PANEL.name());
		frame.getContentPane().add(new AdminLoginPanel(lib, cards), CARD.ADMIN_LOGIN_PANEL.name());
		frame.getContentPane().add(new AdminPanel(lib, cards), CARD.ADMIN_PANEL.name());
		frame.getContentPane().add(new AddLibrarianPanel(lib, cards), CARD.ADD_LIBRARIAN_PANEL.name());
		frame.getContentPane().add(new ViewLibrarianPanel(lib, cards), CARD.VIEW_LIBRARIAN_PANEL.name());
		frame.getContentPane().add(new DeleteLibrarianPanel(lib, cards), CARD.DELETE_LIBRARIAN_PANEL.name());
		frame.getContentPane().add(new LibrarianLoginPanel(lib, cards), CARD.LIBRARIAN_LOGIN_PANEL.name());
		frame.getContentPane().add(new LibrarianPanel(lib, cards), CARD.LIBRARIAN_PANEL.name());
		frame.getContentPane().add(new AddBooksPanel(lib, cards), CARD.ADD_BOOKS_PANEL.name());
		frame.getContentPane().add(new ViewBooksPanel(lib, cards), CARD.VIEW_BOOKS_PANEL.name());
		frame.getContentPane().add(new IssueBookPanel(lib, cards), CARD.ISSUE_BOOK_PANEL.name());
		frame.getContentPane().add(new ViewIssuedBooksPanel(lib, cards), CARD.VIEW_ISSUED_BOOKS_PANEL.name());
		frame.getContentPane().add(new ReturnBookPanel(lib, cards), CARD.RETURN_BOOK_PANEL.name());
	}
}
