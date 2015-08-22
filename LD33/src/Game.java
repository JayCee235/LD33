import javax.swing.JFrame;


public class Game {
	JFrame frame;
	BoardComp comp;
	public Game() {
		frame = new JFrame("Ludum Dare 33!");
		comp = new BoardComp();
		frame.add(comp);
		frame.pack();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}
	
	public void start() {
		frame.setVisible(true);
	}
}
