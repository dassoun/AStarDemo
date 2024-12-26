import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.event.MenuEvent;

import tool.Misc;

public class JMenuClickEvent implements ActionListener {

	JMenuBar menuBar;
	JMenu menuFile;
	JMenu menuMap;
	ArrayList<JMenuItem> mapItemList;
	JMenuItem menuItemResolve;
	
	JFrame frame;
	DemoPanel panel;
	
	public JMenuClickEvent(JFrame frame) {
		super();
		
		this.frame = frame;
		
		menuBar = new JMenuBar();
		menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_F);
		menuMap = new JMenu("Map");
		
		menuBar.add(menuFile);
		menuFile.add(menuMap);
		
		File mapDirectory = new File("inputs/");
		String[] mapList = mapDirectory.list();
		ArrayList<JMenuItem> mapItemList = new ArrayList<JMenuItem>();
        if (mapList != null) {         
            for (int i = 0; i < mapList.length; i++) {
            	mapItemList.add(new JMenuItem(mapList[i]));
            }
        }
        for (JMenuItem mi : mapItemList) {
        	menuMap.add(mi);
        	mi.addActionListener(this);
        }
        
        menuItemResolve = new JMenuItem("Resolve");
        menuItemResolve.setMnemonic(KeyEvent.VK_R);
        menuItemResolve.addActionListener(this);
        menuFile.add(menuItemResolve);
        
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem menuItem = (JMenuItem)e.getSource();
		
		JPopupMenu fromParent = (JPopupMenu)menuItem.getParent();
		JMenu parentMenu = (JMenu)fromParent.getInvoker();
		
		if (parentMenu == menuMap) {
			String mapFile = menuItem.getText();
			
			// READ MAP
			String fileName = "inputs/" + mapFile;
			String[][] map = Misc.fileTo2DArray(fileName);
			
			// SCREEN SETTINGS
			int max_col = map[0].length;
			int max_row = map.length;
			int node_size = 60;
			
			if (panel != null) {
				frame.remove(panel);
			}
			frame.setSize(max_col * node_size, max_row * node_size);
			panel = new DemoPanel(map);
			frame.add(panel);
			
			panel.repaint();
		} else if (menuItem == menuItemResolve) {
			if (panel != null) {
				panel.autoSearch();
			}
		}
	}
	
}
