import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import tool.Misc;

public class DemoPanel extends JPanel {

	// READ MAP
	String fileName = "inputs/map2.txt";
	String[][] map = Misc.fileTo2DArray(fileName);
	
	// SCREEN SETTINGS
	final int MAX_COL = map[0].length;
	final int MAX_ROW = map.length;
	final int NODE_SIZE = 60;
	final int SCREEN_WIDTH = NODE_SIZE * MAX_COL;
	final int SCREEN_HEIGHT = NODE_SIZE * MAX_ROW;
	final int MAX_STEP = 300;
	
	// NODES
	Node[][] node = new Node[MAX_COL][MAX_ROW];
	Node startNode, goalNode, currentNode;
	ArrayList<Node> openList = new ArrayList<Node>();
	ArrayList<Node> checkedList = new ArrayList<Node>();
	
	// OTHERS
	boolean goalReached = false;
	int step = 0;
	
	
	public DemoPanel() {
		
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.BLACK);
		this.setLayout(new GridLayout(MAX_ROW, MAX_COL));
		this.addKeyListener(new KeyHandler(this));
		this.setFocusable(true);
		
		// PLACE NODES
		int col = 0;
		int row = 0;
		
		while (col < MAX_COL && row < MAX_ROW) {
			node[col][row] = new Node(col, row, map[row][col]);
			this.add(node[col][row]);
			
			if (node[col][row].start) {
				startNode = node[col][row];
				currentNode = startNode;
			} else if (node[col][row].goal) {
				node[col][row].setAsGoal();
				goalNode = node[col][row];
			}
			
			col++;
			if (col == MAX_COL) {
				col = 0;
				row++;
			}
		}
		
		// SET COSTS
		setCostOnNodes();
	}
	
	public void setCostOnNodes() {
		int col = 0;
		int row = 0;
		while (col < MAX_COL && row < MAX_ROW) {
			getCost(node[col][row]);
			col++;
			if (col == MAX_COL) {
				col = 0;
				row++;
			}
		}
	}
	
	public void getCost(Node node) {
		// G Cost (distance from the start node)
		int xDistance = Math.abs(node.col - startNode.col);
		int yDistance = Math.abs(node.row - startNode.row);
		node.gCost = xDistance + yDistance;
		
		// H Cost (distance from the end node)
		xDistance = Math.abs(node.col - goalNode.col);
		yDistance = Math.abs(node.row - goalNode.row);
		node.hCost = xDistance + yDistance;
		
		// F Cost (total cost)
		node.fCost = node.gCost + node.hCost;
		
		// DISPLAY THE COST ON NODE
		if (node != startNode && node != goalNode) {
			node.setText("<html>F:" + node.fCost + "<br>G:" + node.gCost + "</html>");
		}
	}
	
	public void search() {
		if (!goalReached) {
			int col = currentNode.col;
			int row = currentNode.row;
			
			currentNode.setAsChecked();
			checkedList.add(currentNode);
			openList.remove(currentNode);
			
			// OPEN TOP NODE
			if (row > 0) {
				openNode(node[col][row - 1]);
			}
			// OPEN RIGHT NODE
			if (col < MAX_COL - 1) {
				openNode(node[col + 1][row]);
			}
			// OPEN BOTTOM NODE
			if (row < MAX_ROW - 1) {
				openNode(node[col][row + 1]);
			}
			// OPEN LEFT NODE
			if (col > 0) {
				openNode(node[col - 1][row]);
			}
			
			// Find the best node
			int bestNodeIndex = 0;
			int bestNodeCost = 999;
			
			for (int i = 0; i < openList.size(); i++) {
				if (openList.get(i).fCost < bestNodeCost) {
					bestNodeIndex = i;
					bestNodeCost = openList.get(i).fCost;
				} else if (openList.get(i).fCost == bestNodeCost) {
					if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}
			}
			
			// After the loop, we have the best node which is our next step
			currentNode = openList.get(bestNodeIndex);
			
			if (currentNode == goalNode) {
				goalReached = true;
				//trackPath();
			}
		}
	}
	
	public void autoSearch() {
		while (!goalReached && step < MAX_STEP) {
			int col = currentNode.col;
			int row = currentNode.row;
			
			currentNode.setAsChecked();
			checkedList.add(currentNode);
			openList.remove(currentNode);
			
			// OPEN TOP NODE
			if (row > 0) {
				openNode(node[col][row - 1]);
			}
			// OPEN RIGHT NODE
			if (col < MAX_COL - 1) {
				openNode(node[col + 1][row]);
			}
			// OPEN BOTTOM NODE
			if (row < MAX_ROW - 1) {
				openNode(node[col][row + 1]);
			}
			// OPEN LEFT NODE
			if (col > 0) {
				openNode(node[col - 1][row]);
			}
			
			// Find the best node
			int bestNodeIndex = 0;
			int bestNodeCost = 999;
			
			for (int i = 0; i < openList.size(); i++) {
				if (openList.get(i).fCost < bestNodeCost) {
					bestNodeIndex = i;
					bestNodeCost = openList.get(i).fCost;
				} else if (openList.get(i).fCost == bestNodeCost) {
					if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}
			}
			
			// After the loop, we have the best node which is our next step
			currentNode = openList.get(bestNodeIndex);
			
			if (currentNode == goalNode) {
				goalReached = true;
				trackPath();
			}
			
			step++;
		}
	}
	
	private void openNode(Node node) {
		if (!node.open && !node.checked && !node.solid) {
			
			// if the node is node opened yet, add it to the open list
			node.setAsOpen();
			node.parent = currentNode;
			openList.add(node);
		}
	}
	
	private void trackPath() {
		// Backtrack and draw the best path
		Node current = goalNode;
		
		while (current != startNode) {
			current = current.parent;
			
			if (current != startNode) {
				current.setAsPath();
			}
		}
	}
}
