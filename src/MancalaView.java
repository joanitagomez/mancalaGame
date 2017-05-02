import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
/**
 * @author Han
 * MancalaView class has the view for the mancala game
 */
public class MancalaView extends JPanel {	

	private static final int PREF_W = 560;
	private static final int PREF_H = 280;

	private Point mousePoint;
	Model m;
	HashMap<Integer, Integer> b;
	ArrayList<PitShape> pits;
	int selectedPit = -1;
	StyleManager s;
	JLabel label;
	ArrayList<ActionListener> listeners;
	int turn;

	public MancalaView(Model model, StyleManager s) {
		setStyle(s);
		this.m = model;
		listeners = new ArrayList<ActionListener>();
		pits = new ArrayList<PitShape>();
		b = m.getBoard();
		turn = m.getGameState();

		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event) {
				mousePoint = event.getPoint();
				for (PitShape p : pits) {
					if (p.contains(mousePoint)) {
						setSelectedPit(p.getpitIndex());

						for (ActionListener l : listeners) {
							l.actionPerformed(new ActionEvent(event.getSource(), event.getID(), "move"));
						}

					}
				}
			}
		});

		int diameter = 70;
		int x = PREF_W / (diameter * 8);
		int y = diameter;
		int height;

		// B's panel NORTH
		for (int i = 13; i >= 7; i--) {
			height = diameter;
			if (i == 13) {
				y = diameter;
				height = diameter * 2;
			} else {
				x = x + diameter;
				y = 0;
			}
			pits.add(new PitShape(diameter, x, y, height, i));
		}

		// A's panel SOUTH
		height = diameter;
		x = PREF_W / (diameter * 8);
		y = diameter * 3;
		for (int i = 0; i <= 6; i++) {
			height = diameter;
			if (i == 6) {
				x = 7 * diameter;
				y = diameter;
				height = diameter * 2;
			} else
				x = x + diameter;
			pits.add(new PitShape(diameter, x, y, height, i));
		}

		label = new JLabel(stringGameState(turn) + "'s turn");

		m.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (m.freeTurn)
					label.setText("Free turn!");
				else if (m.undoA) {
					label.setText(m.getUndoA() + " undo(s) left");
				} else if (m.undoB)
					label.setText(m.getUndoB() + " undo(s) left");
				else {
					turn = m.getGameState();
					label.setText((stringGameState(turn) + "'s turn"));
				}
				b = m.getBoard();
				repaint();
			}
		});

		setLayout(null);
		setFont(new Font("Courier", Font.BOLD, 12));
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setFont(new Font("Courier", Font.PLAIN, 14));
		label.setBounds(3 * diameter + 20, height, 560, 50);
		add(label);
	}

	public String stringGameState(int t) {
		if (t == 0)
			return "A";
		else
			return "B";
	}

	/**
	 * Attach a actionListener to this view to notify controller when a pit is
	 * selected
	 * 
	 * @param a
	 *            the listener
	 */
	public void attachActionListener(ActionListener a) {
		listeners.add(a);
	}

	/**
	 * returns selected pit
	 */
	public int getSelectedPit() {
		return selectedPit;
	}

	/**
	 * sets selected pit
	 * 
	 * @param selectedPit
	 */
	public void setSelectedPit(int selectedPit) {
		this.selectedPit = selectedPit;
	}

	/**
	 * paintComponent draws pits and stones
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		for (PitShape p : pits) {
			p.draw(g2, s);

			int stones = m.getBoard().get(p.pitIndex);
			for (int z = 0; z < stones; z++) {
				int collumn = z / 5;
				int row = z % 5;
				g2.fill(new Ellipse2D.Double(p.x + 10 + (row * 6), p.y + p.height / 4 + (collumn * 6), 5, 4));
			}
		}
	}

	/**
	 * setStyle sets strategy of GUI
	 * 
	 * @param strategy
	 *            MancalaStrategy
	 */
	public void setStyle(StyleManager s) {
		this.s = s;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PREF_W, PREF_H);
	}

}