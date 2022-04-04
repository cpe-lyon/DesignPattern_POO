package model.noStrategy.pieces;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import model.ModelFactory;
import shared.ActionType;
import shared.ModelCoord;
import shared.PieceSquareColor;




/**
 * @author francoise.perrin - Alain BECKER
 * Inspiration Jacques SARAYDARYAN, Adrien GUENARD*
 */
public abstract class AbstractPion extends  AbstractPieceMemorisantSonPremierMouvement  {

	/**
	 * @param pieceSquareColor
	 * @param coord
	 */
	public AbstractPion(PieceSquareColor pieceSquareColor, ModelCoord coord) {
		super(pieceSquareColor, coord);

	}


	/* (non-Javadoc)
	 * @see model.AbstractPiece#isAlgoMoveOk(int, int)
	 * Déplacement vertical sans prise
	 */
	@Override
	public final boolean isAlgoMoveOk(ModelCoord finalCoord) {
		int xFinal = finalCoord.getCol() -'a';
		int yFinal = 8 - finalCoord.getLigne();
		
		boolean ret = false;
		
		if ((xFinal == this.getX())
				&& (Math.abs(yFinal - this.getY()) <= 1 || 
				(Math.abs(yFinal - this.getY()) <= 2 && !this.hasMoved()))) {

			ret = isAlgoMoveVertOk(yFinal);
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see model.AbstractPiece#isAlgoMoveOk(int, int, model.ActionType)
	 * Déplacement en diagonal avec prise
	 */
	@Override
	public final boolean isAlgoMoveOk(ModelCoord finalCoord, ActionType actionType) {
		int xFinal = finalCoord.getCol() -'a';
		int yFinal = 8 - finalCoord.getLigne();
		
		return isAlgoMoveDiagOk(xFinal, yFinal);
	}

	protected abstract boolean isAlgoMoveVertOk(int yFinal) ;

	protected abstract boolean isAlgoMoveDiagOk(int xFinal, int yFinal) ;
	
	
	/* (non-Javadoc)
	 * @see model.AbstractPiece#getMoveItinerary(int, int)
	 * dans le cas du pion, il n'y a pas d'itinéraire
	 * puisqu'il se déplace sur une case adjacente
	 * sauf pour le 1er coup où il se déplace de 2 cases
	 */
	@Override
	public List<ModelCoord> getMoveItinerary(ModelCoord finalCoord) {
		int yFinal = 8 - finalCoord.getLigne();
		List<ModelCoord> ret = Collections.emptyList(); 
		if (this.getY()==yFinal-2 || this.getY()==yFinal+2){
			ret = new LinkedList<ModelCoord>();

			int yEtape = (this.getY() + yFinal) / 2;			// Y est la ligne entre départ et arrivée
			ModelCoord coordEtape = new ModelCoord((char)('a'+this.getX()), (8-yEtape));	// et X est dans la même colonne
			
			ret.add(coordEtape);
		}
		return ret;
	}



	/* (non-Javadoc)
	 * @see model.AbstractPiece#movePiece(int, int)
	 * gère le code de retour lorsqu'il faut promouvoir le pion
	 */
	@Override
	public ActionType doMove(ModelCoord finalCoord){
		ActionType ret = ActionType.UNKNOWN;
		ret = super.doMove(finalCoord);

		if(this.getY() == ModelFactory.nbLigne.get()-1 || this.getY() == 0) {
			ret = ActionType.PROMOTION;
		}
		return ret;
	}

}
