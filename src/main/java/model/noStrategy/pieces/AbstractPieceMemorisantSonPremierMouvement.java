package model.noStrategy.pieces;

import shared.ActionType;
import shared.ModelCoord;
import shared.PieceSquareColor;



/**
* @author francoise.perrin - Alain BECKER
 * cette pièce va concerner les Pion, Roi, Tour
 *
 */
public abstract class AbstractPieceMemorisantSonPremierMouvement 
extends AbstractPiece
implements MemoriseSonPremierMouvement {

	private boolean hasMoved;
	private boolean hasMovedBeforeMove;

	public AbstractPieceMemorisantSonPremierMouvement(PieceSquareColor pieceSquareColor, ModelCoord coord) {
		super(pieceSquareColor, coord);
		this.hasMoved = false;
		this.hasMovedBeforeMove = false;
	}

	@Override
	public boolean hasMoved() {
		return this.hasMoved;
	}


	@Override
	public ActionType doMove(ModelCoord coord){
		ActionType ret = ActionType.UNKNOWN;
		ret = super.doMove(coord);
		if (!ActionType.UNKNOWN.equals(ret)) {
			this.hasMovedBeforeMove = this.hasMoved;
			this.hasMoved = true;
		}
		return ret;
	}

	@Override
	public boolean undoLastMove(){
		boolean ret = false;
		ret = super.undoLastMove();
		this.hasMoved = this.hasMovedBeforeMove;
		return ret;
	}


}
