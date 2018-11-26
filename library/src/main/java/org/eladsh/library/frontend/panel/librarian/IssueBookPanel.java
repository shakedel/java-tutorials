package org.eladsh.library.frontend.panel.librarian;

import java.util.function.Function;

import org.eladsh.library.backend.Library;
import org.eladsh.library.frontend.MainWindow.CARD;
import org.eladsh.library.frontend.panel.LibraryPanel;

public class IssueBookPanel extends LibraryPanel {

	private static final long serialVersionUID = 1L;

	public IssueBookPanel(Library lib, Function<CARD, Object> cards) {
		super(lib, cards);
	}

}
