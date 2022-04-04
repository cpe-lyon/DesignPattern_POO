package model.noStrategy.pieces;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import shared.ModelCoord;
import shared.PieceSquareColor;



/**
 * @author francoise.perrin - Alain BECKER
 * Inspiration Jacques SARAYDARYAN, Adrien GUENARD *
 */
public class Fou extends AbstractPiece {

	/**
	 * @param pieceSquareColor
	 * @param coord
	 */
	public Fou(PieceSquareColor pieceSquareColor, ModelCoord coord) {
		super(pieceSquareColor, coord);
	}

	@Override
	public boolean isAlgoMoveOk(ModelCoord finalCoord) {
		int xFinal = finalCoord.getCol() -'a';
		int yFinal = 8 - finalCoord.getLigne();
		boolean ret = false;

		if (Math.abs(yFinal - this.getY()) == Math.abs(xFinal - this.getX())) {
			ret  = true;
		}		

		return ret;
	}

	@Override
	public List<ModelCoord> getMoveItinerary(ModelCoord finalCoord) {
		int xFinal = finalCoord.getCol() -'a';
		int yFinal = 8 - finalCoord.getLigne();
		List<ModelCoord> ret = Collections.emptyList(); 

		// on vérifie que les coordonnées finales sont compatibles 
		//avec l'algo de déplacement 
		if (this.isAlgoMoveOk(finalCoord)) {

			ret = new LinkedList<ModelCoord>();
			int xVector = (int) Math.signum(xFinal - this.getX());
			int yVector = (int) Math.signum(yFinal - this.getY());

			int y = this.getY() + yVector;
			for (int x = this.getX() + xVector ; x!=xFinal ; x+=xVector) {
				
				ret.add(new ModelCoord((char)('a'+x), (8-y)));
			
				y+=yVector;
			}
		}
		
		return ret;
	}


}
