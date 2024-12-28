import javax.swing.JFrame;

public class AStarDemo {

	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		
		JMenuClickEvent menuClickEvent = new JMenuClickEvent(window);
		window.setJMenuBar(menuClickEvent.menuBar);
//		window.add(new DemoPanel());
		
		window.pack();
		window.setSize(400, 300);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

}
