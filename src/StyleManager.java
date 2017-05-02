import java.awt.Color;

/** 
 * @author Ada
 *  StyleManager interface sets up the requirements for GUI style 
 *  of the mancala board
 */
public interface StyleManager {
	/**
	 * getInsidePitColor method return the color of this inside pit
	 */
	public Color getOutsidePitColor();

	/**
	 * getOutsidePitColor method returns the outside color of the pit
	 */
	public Color getInsidePitColor();


	/**
	 * getBeadFill method returns the color of the beads
	 * 
	 * @return Color dark gray
	 */
	public Color getBeadFill();

}
