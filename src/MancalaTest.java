import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class MancalaTest {


	public static void main1(String[] args) {

		String strStones = JOptionPane.showInputDialog("How many stones do you want to play with?");
		if (strStones == null)
			System.exit(0);

		int stones = Integer.parseInt(strStones);

		final Model model = new Model(stones);

		Object[] strategy = { "Normal", "Alternate" };
		int style = JOptionPane.showOptionDialog(null, "Choose a Style", "Style", JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, strategy, strategy[1]);

	}

	// while (!model.isGameOver()) {
	//
	// int pit = view.getPit();
	// // initially selected pit is set to -1
	// if (pit == -1)
	// continue;
	// if (model.isValidPit(pit))
	// model.updatePits(pit);
	// }
	// System.out.println("*Game Over*");
	// System.out.println("Winner: " + model.declareWinner());
	// }

	public static void main(String[] args) {
		

		JFrame frame = new JFrame();
		frame.setSize(900, 600);
		frame.setTitle("Mancala");

		
		String strStones = JOptionPane.showInputDialog("How many stones do you want to play with?");
		if (strStones == null)
			System.exit(0);

		int stones = Integer.parseInt(strStones);

		Object[] strategy = { "Normal", "Alternate" };
		int style = JOptionPane.showOptionDialog(null, "Choose a Style", "Style", JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, strategy, strategy[1]);
		StyleManager sm;
		if(style == 1)
			 sm = new AltStyle();
		else
			sm = new DefaultStyle();

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
					
				//	undoButton.setEnabled(true);
//					System.out.println((model.getGameState() == 0 && model.getUndoA() > 0));
//					System.out.println("undo a : " + model.getUndoA());
					

//					if (model.getGameState() == 1 && model.getUndoA() > 0){
//						undoButton.setEnabled(true);
//					}
//					else if (model.getGameState() == 1 && model.getUndoB() > 0){
//						undoButton.setEnabled(true);
//					}
//					else{
//						undoButton.setEnabled(false);
//					}
				}
				if (model.isGameOver()) {
					view.label.setHorizontalAlignment(SwingConstants.LEFT);
					view.label.setText("Game Over. Player " + view.stringGameState(model.declareWinner()) + " wins!");
				}
			}
		});

		frame.add(view, BorderLayout.NORTH);
		frame.add(undoButton, BorderLayout.SOUTH);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}
