import java.awt.Dimension;

import javax.swing.JComponent;


public class Comp extends JComponent {

	public Comp() {
		super();
		Dimension d = new Dimension(800, 600);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setMaximumSize(d);
	}
}
