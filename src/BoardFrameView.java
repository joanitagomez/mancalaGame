import javax.swing.JComponent;
import javax.swing.JFrame;

public class BoardFrameView extends JFrame {

	
	public BoardFrameView(final Model m) {
		JComponent p = new PitComponent(m);
		
		


		add(p);
		setSize(600, 400);
	//	pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}