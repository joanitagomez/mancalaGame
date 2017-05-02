import java.awt.Color;

public class DefaultStyle implements StyleManager {

	   /**
	    * getBackGroundColor method returns the background color of the mancala
	    *  @return Color blue
	    */
	   public Color getBackGroundColor() {
	      return Color.BLUE;
	   }

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
	    * getFontColor method returns the color of the stones font
	    * @return Color red
	    */
	   public Color getFontColor() 
	   {
	      return Color.RED;
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
