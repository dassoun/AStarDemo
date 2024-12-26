import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		
		JMenuClickEvent menuClickEvent = new JMenuClickEvent(window);
		window.setJMenuBar(menuClickEvent.menuBar);
//		window.add(new DemoPanel());
		
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

}
