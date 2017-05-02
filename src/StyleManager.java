import java.awt.Color;

public interface StyleManager {

	/**
	 * getBackGroundColor method returns the background color of the mancala
	 */
	public Color getBackGroundColor();

	/**
	 * getInsidePitColor method return the color of this inside pit
	 */
	public Color getOutsidePitColor();

	/**
	 * getOutsidePitColor method returns the outside color of the pit
	 */
	public Color getInsidePitColor();

	/**
	 * getFontColor method returns the color of the stones font
	 */
	public Color getFontColor();

	/**
	 * getBeadFill method returns the color of the beads
	 * 
	 * @return Color dark gray
	 */
	public Color getBeadFill();

}
