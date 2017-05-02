import java.awt.Color;
/**
 * @author Ada
 * DefaultStyle class set up the
 * alternative GUI style of the mancala board
 */
public class DefaultStyle implements StyleManager {

	   /**
	    * getInsidePitColor method return the color of this inside pit
	    * @ return Color blue
	    */
	   public Color getInsidePitColor() {
	      return Color.BLUE;
	   }

	   /**
	    * getOutsidePitColor method returns the outside color of the pit
	    * @return Color white
	    */
	   public Color getOutsidePitColor() 
	   {
	      return Color.WHITE;
	   }

	   /**
	    * getBeadFill method returns the color of the beads
	    * @return Color black
	    */
	   public Color getBeadFill() 
	   {
	      return Color.black;
	   }
	
}
