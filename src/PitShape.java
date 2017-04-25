import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class PitShape {

	int height;
	int diameter;
	int x;
	int y;
	int pitIndex;

	PitShape(int diameter, int x, int y, int height, int pitIndex) {
		this.diameter = diameter;
		this.height = height;
		this.x = x;
		this.y = y;
		setpitIndex(pitIndex);
	}

	public void setpitIndex(int pitIndex) {
		this.pitIndex = pitIndex;
	}

	public int getpitIndex() {
		return pitIndex;
	}

	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		Rectangle2D rect = new Rectangle2D.Double(x, y, diameter, height);
		g2.draw(rect);

		Ellipse2D ell = new Ellipse2D.Double();
		ell.setFrame(rect);
		g2.draw(ell);

	}

	public boolean contains(Point mousePoint) {
		double x0 = this.x;
		double y0 = this.y;
		return mousePoint.getX() >= x0 && mousePoint.getY() >= y0 && mousePoint.getX() < x0 + diameter
				&& mousePoint.getY() < y0 + diameter;
	}

}
