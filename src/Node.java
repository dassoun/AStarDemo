import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Node extends JButton implements ActionListener {
	
	Node parent;
	int col;
	int row;
	int gCost;			// distance between current node and start node
	int hCost;			// distance between current node and end node
	int fCost;			// total cost (g + h)
	boolean start;
	boolean goal;
	boolean solid;
	boolean open;
	boolean checked;
	
	public Node() {
		
		super();
		// TODO Auto-generated constructor stub
	}

	public Node(int col, int row) {
		
		super();
		this.col = col;
		this.row = row;
		
		setBackground(Color.WHITE);
		setForeground(Color.BLACK);
		
		Font font = new Font("Arial", Font.PLAIN, 9);
		setFont(font);
		
		addActionListener(this);
	}
	
	public Node(int col, int row, String type) {
		
		super();
		this.col = col;
		this.row = row;
		
		Font font = new Font("Arial", Font.PLAIN, 9);
		setFont(font);
		
		switch (type) {
		case "#" :
			setAsSolid();
			break;
		case "." :
			break;
		case "S" :
			setAsStart();
			break;
		case "E" :
			setAsGoal();
			break;
		default :
			break;
		}
		
		if (!solid && !start && !goal) {
			addActionListener(this);
		}
	}

	public void setAsStart() {
		setBackground(Color.BLUE);
		setForeground(Color.WHITE);
		setText("Start");
		start = true;
	}
	
	public void setAsGoal() {
		setBackground(Color.YELLOW);
		setForeground(Color.BLACK);
		setText("Goal");
		goal = true;
	}
	
	public void setAsSolid() {
		setBackground(Color.BLACK);
		setForeground(Color.BLACK);
		solid = true;
	}
	
	public void setAsOpen() {
		open = true;
	}
	
	public void setAsChecked() {
		if (!start && !goal) {
			setBackground(Color.ORANGE);
			setForeground(Color.BLACK);
		}
		solid = true;
	}
	
	public void setAsPath() {
		setBackground(Color.GREEN);
		setForeground(Color.BLACK);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		setBackground(Color.ORANGE);
	}

}
