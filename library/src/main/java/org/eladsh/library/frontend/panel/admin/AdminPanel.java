package org.eladsh.library.frontend.panel.admin;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Function;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.eladsh.library.backend.Library;
import org.eladsh.library.frontend.MainWindow.CARD;
import org.eladsh.library.frontend.panel.LibraryPanel;

public class AdminPanel extends LibraryPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public AdminPanel(Library lib, Function<CARD, Object> cards) {
		super(lib, cards);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JLabel lblNewLabel_3 = new JLabel("Admin Section");
		lblNewLabel_3.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(lblNewLabel_3);

		Component verticalStrut_5 = Box.createVerticalStrut(20);
		this.add(verticalStrut_5);

		JButton btnNewButton_2 = new JButton("Add Librarian");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.apply(CARD.ADD_LIBRARIAN_PANEL);
			}
		});
		btnNewButton_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(btnNewButton_2);

		Component verticalStrut_6 = Box.createVerticalStrut(20);
		this.add(verticalStrut_6);

		JButton btnNewButton_3 = new JButton("View Librarian");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.apply(CARD.VIEW_LIBRARIAN_PANEL);
			}
		});
		btnNewButton_3.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(btnNewButton_3);

		Component verticalStrut_7 = Box.createVerticalStrut(20);
		this.add(verticalStrut_7);

		JButton btnNewButton_5 = new JButton("Delete Librarian");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.apply(CARD.DELETE_LIBRARIAN_PANEL);
			}
		});
		btnNewButton_5.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(btnNewButton_5);

		Component verticalStrut_8 = Box.createVerticalStrut(20);
		this.add(verticalStrut_8);

		JButton btnNewButton_6 = new JButton("Logout");
		btnNewButton_6.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.apply(CARD.MAIN_PANEL);
			}
		});
		this.add(btnNewButton_6);

	}

}
