package model.noStrategy.pieces;

import java.util.Collections;
import java.util.List;

import shared.ModelCoord;
import shared.PieceSquareColor;


/**
 * @author francoise.perrin - Alain BECKER
 * * Inspiration Jacques SARAYDARYAN, Adrien GUENARD
 */
public class Roi extends AbstractPieceMemorisantSonPremierMouvement {
	
	
	//private boolean isCastling;
	
	/**
	 * @param pieceSquareColor
	 * @param coord
	 */
	public Roi( PieceSquareColor pieceSquareColor, ModelCoord coord) {
		super(pieceSquareColor, coord);
		
	//	this.isCastling = false;
	}

	@Override
	public boolean isAlgoMoveOk(ModelCoord finalCoord) {
		int xFinal = finalCoord.getCol() -'a';
		int yFinal = 8 - finalCoord.getLigne();
		boolean ret = false;
		
		// cas général
		if ((Math.abs(yFinal - this.getY()) <= 1)
				&& (Math.abs(xFinal - this.getX()) <= 1)) {
			ret = true;
		}
		
		// Cas du roque
		// TODO : implémenter
		
		return ret;
	}
	
	@Override
	public List<ModelCoord> getMoveItinerary(ModelCoord finalCoord) {
		List<ModelCoord> ret = Collections.emptyList(); 

		// on vérifie que les coordonnées finales sont compatibles 
		//avec l'algo de déplacement  dans le cas du roque
		if (this.isAlgoMoveOk(finalCoord)) {
			// ToDo
		}
		return ret;
	}
	
}
