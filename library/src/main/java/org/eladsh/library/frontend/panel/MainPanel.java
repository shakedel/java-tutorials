package org.eladsh.library.frontend.panel;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Function;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.eladsh.library.backend.Library;
import org.eladsh.library.frontend.MainWindow.CARD;

public class MainPanel extends LibraryPanel {

	private static final long serialVersionUID = 1L;

	public MainPanel(Library lib, Function<CARD, Object> cards) {
		super(lib, cards);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel lblFirstPanel = new JLabel("Library Management");
		lblFirstPanel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblFirstPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblFirstPanel.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(lblFirstPanel);
		
		JButton btnNewButton = new JButton("Admin Login");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.apply(CARD.ADMIN_LOGIN_PANEL);
			}
		});
		
		Component verticalStrut = Box.createVerticalStrut(40);
		this.add(verticalStrut);
		this.add(btnNewButton);
		
		Component verticalStrut_1 = Box.createVerticalStrut(30);
		this.add(verticalStrut_1);
		
		JButton btnNewButton_1 = new JButton("Librarian Login");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.apply(CARD.LIBRARIAN_LOGIN_PANEL);
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(btnNewButton_1);
		
	}

}
