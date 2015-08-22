import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.xml.soap.Node;


public class BoardComp extends Comp{
	boolean[][] board;
	int size;
	public BoardComp() {
		super();
		size = 17;
		board = new boolean[size][size];
		
	}
	
	public void testFill() {
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				board[i][j] = (Math.random() < 0.5);
			}
		}
	}
	
	private static class Node {
		Node u, d, l, r;
		int x, y;
		Color c;
		
		private Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		private Node(Node u, Node d, Node l, Node r, int x, int y) {
			this.u = u;
			this.d = d;
			this.l = l;
			this.r = r;
			this.x = x;
			this.y = y;
		}
		
		private void setUp(Node n) {
			n.d = this;
			n.x = this.x;
			n.y = this.y+1;
			
			this.u = n;
		}
		
		private Node rand() {
			double roll = Math.random() * 4;
			if(roll < 1) return u;
			if(roll < 2) return d;
			if(roll < 3) return l;
			return r;
		}
		
		private Node[] shuffle() {
			ArrayList<Node> slot = new ArrayList<Node>();
			slot.add(u); 
			slot.add(d);
			slot.add(l);
			slot.add(r);
			Node[] out = new Node[4];
			for(int i = 0; i < 4; i++) {
				int roll = (int) (Math.random() * slot.size());
				out[i] = slot.get(roll);
				slot.remove(roll);
			}
			return out;
		}
		
		
		private static Node mesh(int x, int y) {
			Node[][] fill = new Node[x][y];
			for(int i = 0; i < x; i++) {
				for(int j = 0; j < y; j++) {
					fill[i][j] = new Node(i, j);
					if(i > 0) {
						Node up = fill[i-1][j];
						Node down = fill[i][j];
						up.r = down;
						down.l = up;
					}
					if(j > 0) {
						Node up = fill[i][j-1];
						Node down = fill[i][j];
						up.d = down;
						down.u = up;
					}
				}
			}
			return fill[0][0];
		}
	}
	
	public void generate() {
		Node top = Node.mesh(size/2, size/2);
		ArrayList<Node> open = new ArrayList<Node>();
		ArrayList<Node> closed = new ArrayList<Node>();
		
		Node bottom = top;
		Node mid1 = top;
		Node mid2 = top;
		while(bottom.r != null) {
			bottom = bottom.r;
		}
		mid1 = bottom;
		while(bottom.d != null) {
			bottom = bottom.d;
		}
		while(mid2.d != null) {
			mid2 = mid2.d;
		}
		
		open.add(top);
		open.add(bottom);
		open.add(mid1);
		open.add(mid2);
		board[top.x * 2 + 1][top.y * 2 + 1] = true;
		board[bottom.x * 2 + 1][bottom.y * 2 + 1] = true;
		board[mid1.x * 2 + 1][mid1.y * 2 + 1] = true;
		board[mid2.x * 2 + 1][mid2.y * 2 + 1] = true;
		
		while(open.size() > 0) {
			int roll = (int) (Math.random() * open.size());
			Node current = open.get(roll);
			
			Node[] fill = current.shuffle();
			
			Node check = fill[0];
			boolean okay = true;
			for(int i = 0; okay && i < 4; i++) {
				check = fill[i];
				okay = (check == null || open.contains(check) || closed.contains(check));
			}
			
			
			if(okay) {
				open.remove(current);
				closed.add(current);
			} else {
				board[check.x + current.x + 1][check.y + current.y + 1] = true;
				board[2*check.x + 1][2*check.y + 1] = true;
				if(!open.contains(check)) {
					open.add(check);
				}
			}
		
		
		}
	}
	
	public void paintComponent(Graphics g) {
		g.translate(400-board.length*16, 300-board[0].length*16);
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				Color set = board[i][j]?Color.black:Color.white;
				g.setColor(set);
				g.fillRect(32*i, 32*j, 32, 32);
			}
		}
	}
}
