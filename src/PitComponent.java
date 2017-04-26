import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PitComponent extends JComponent {
	private PitShape pit;
	private Point mousePoint;
	Model m;
	HashMap<Integer, Integer> b;
	ArrayList<PitShape> pits;
	int selectedPit = -1;
		

	public PitComponent(Model model) {
		this.m = model;
		pits = new ArrayList<PitShape>();
		b = m.getBoard();
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event) {
				mousePoint = event.getPoint();
				System.out.println("(" + mousePoint.getX() + " ," + mousePoint.getY() + ")");

				for (PitShape p : pits) {
					if (p.contains(mousePoint)) {
						setSelectedPit(p.getpitIndex());
						System.out.println(getSelectedPit());
				
					}
				}
			}
		});
		int diameter = 70;
		int x = 0;
		int y;
		int height;
		// B's panel in the NORTH
		for (int i = 13; i >= 7; i--) {
			height = diameter;
			if (i == 13) {
				x = 0;
				y = diameter;
				height = diameter * 2;
			} else{
				x = x + diameter;
				y = 0;
			}
			pits.add(new PitShape(diameter, x, y, height, i));
		}

		// A's panel in the SOUTH
		height = diameter;
		x = 0;
		y = diameter * 3;
		for (int i = 0; i <= 6; i++) {
			height = diameter;
			if (i == 6) {
				x = 490;
				y = diameter;
				height = diameter * 2;
			} else
				x = x + diameter;
			pits.add(new PitShape(diameter, x, y, height, i));
		}


		m.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				b = m.getBoard();
				repaint();

			}
		});
	}

	public int getSelectedPit() {
		return selectedPit;
	}

	public void setSelectedPit(int selectedPit) {
		this.selectedPit = selectedPit;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		for (PitShape p : pits) {
			
			p.draw(g2);
			
			int stones = m.getBoard().get(p.pitIndex);
			for (int z = 0; z < stones; z++) {
				int collumn = z / 5;
				int row = z % 5;
				g2.fill(new Ellipse2D.Double(p.x + 10 + (row * 6), p.y + p.height/4 + (collumn * 6), 5, 4));
				
			}
		}
	}

	public void showMessage(String msg){
		JOptionPane.showMessageDialog(null, msg);
	}
//	@Override
//	public void stateChanged(ChangeEvent e) {
//		b = m.getBoard();
//		repaint();
//	}
	   private static final int PREF_W = 800;
	   private static final int PREF_H = 400;


	   @Override
	   public Dimension getPreferredSize() {
	      return new Dimension(PREF_W, PREF_H);
	   }

}
