package org.eladsh.library;

import java.awt.EventQueue;

import org.eladsh.library.frontend.MainWindow;

public class App {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				MainWindow window = new MainWindow();
				window.setVisibleFrame(true);
				}
			}
		);
	}
}
