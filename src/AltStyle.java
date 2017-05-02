
import java.awt.Color;

/**
 * @author Team Name: Team members: MancalaStrategyAlt class set up the
 *         alternative GUI style of the mancala board
 */
public class AltStyle implements StyleManager {

	/**
	 * getBackGroundColor method returns the background color of the mancala
	 * 
	 * @return Color red
	 */
	public Color getBackGroundColor() {
		return Color.RED;

	}

	/**
	 * getInsidePitColor method return the color of this inside pit @ return
	 * Color white
	 */
	public Color getInsidePitColor() {
		return Color.WHITE;
	}

	/**
	 * getOutsidePitColor method returns the outside color of the pit
	 * 
	 * @return Color blue
	 */
	public Color getOutsidePitColor() {
		return Color.BLUE;
	}

	/**
	 * getFontColor method returns the color of the stones font
	 * 
	 * @return Color black
	 */
	public Color getFontColor() {
		// TODO Auto-generated method stub
		return Color.BLACK;
	}

	/**
	 * getBeadFill method returns the color of the beads
	 * 
	 * @return Color dark gray
	 */
	public Color getBeadFill() {
		// TODO Auto-generated method stub
		return Color.DARK_GRAY;
	}

}
