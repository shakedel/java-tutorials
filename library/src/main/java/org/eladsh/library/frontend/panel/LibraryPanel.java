package org.eladsh.library.frontend.panel;

import java.util.function.Function;

import javax.swing.JPanel;

import org.eladsh.library.backend.Library;
import org.eladsh.library.frontend.MainWindow.CARD;

public abstract class LibraryPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	protected final Library lib;
	protected final Function<CARD, Object> cards;
	
	public LibraryPanel(Library lib, Function<CARD, Object> cards) {
		this.lib = lib;
		this.cards = cards;
	}

}
