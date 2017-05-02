
import java.awt.Color;
/**
 * @author Ada
 * AltStyle class set up the
 * alternative GUI style of the mancala board
 */
public class AltStyle implements StyleManager {

	
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
	 * getBeadFill method returns the color of the beads
	 * 
	 * @return Color dark gray
	 */
	public Color getBeadFill() {
		// TODO Auto-generated method stub
		return Color.DARK_GRAY;
	}

}
