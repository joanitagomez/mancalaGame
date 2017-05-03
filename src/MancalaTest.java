import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
/**
 * @author Ada
 * Team members: Han , Joanitha  
 *  MancalaTester Runs the main program. It also acts as the controller.
 */
public class MancalaTest {

	 /**
	    * The main method starts the program.
	    * @param args String [] no value
	    */
	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setSize(900, 600);
		frame.setTitle("Mancala");

		
//		String strStones = JOptionPane.showInputDialog("How many stones do you want to play with (3 or 4)? ");
//		 if(strStones.equals(""))
//				System.exit(0);
//		 
//		int stones = Integer.parseInt(strStones);
//		if (!strStones.equals("3") && !strStones.equals("4")){
//			JOptionPane.showMessageDialog(null,"Invalid number. You've been assigned 3 stones.");
//			stones = 3;
//		}
//		
		
		Object[] strStones = { "4", "3" };
		int stones = JOptionPane.showOptionDialog(null, "Pick number of stones", "Stones", JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, strStones, strStones[1]);
		
		if(stones == 1)
			stones = 3;
		else
			stones = 4;
		
		Object[] strategy = { "Alternate", "Normal" };
		int style = JOptionPane.showOptionDialog(null, "Choose a Style", "Style", JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, strategy, strategy[1]);
		
		StyleManager sm;
		
		if(style == 1)
			 sm = new DefaultStyle ();
		else
			sm = new AltStyle();

		final Model model = new Model(stones);
		final MancalaView view = new MancalaView(model, sm);
		
		final JButton undoButton = new JButton("Undo");
		undoButton.setEnabled(false);

		undoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.undo();
				undoButton.setEnabled(false);
			}
		});

		view.attachActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int pit = view.getSelectedPit();

				if (pit == -1)
					return;
				if (model.isValidPit(pit)) {
					model.updatePits(pit);
					
					if (model.getGameState() == 1 && model.getUndoA() > 0)
						undoButton.setEnabled(true);
					
					else if (model.getGameState() == 0 && model.getUndoB() > 0)
						undoButton.setEnabled(true);
					
					else
						undoButton.setEnabled(false);
				}
				else
					JOptionPane.showMessageDialog(null, "Invalid Pit");
					
				if (model.isGameOver()) {
					view.label.setHorizontalAlignment(SwingConstants.LEFT);
					view.label.setText("Game Over. Player " + view.stringGameState(model.declareWinner()) + " wins!");
				}
			}
		});

		frame.add(view, BorderLayout.NORTH);
		frame.add(undoButton, BorderLayout.SOUTH);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}
