package model.noStrategy.pieces;

import shared.ModelCoord;
import shared.PieceSquareColor;




/**
 * @author francoise.perrin - Alain BECKER
 * Inspiration Jacques SARAYDARYAN, Adrien GUENARD*
 */
public class PionBlanc extends  AbstractPion  {

	/**
	 * @param pieceSquareColor
	 * @param coord
	 */
	public PionBlanc(PieceSquareColor pieceSquareColor, ModelCoord coord) {
		super(pieceSquareColor, coord);
	}

	protected boolean isAlgoMoveVertOk(int yFinal) {

		return yFinal - this.getY() < 0;
	}

	protected boolean isAlgoMoveDiagOk(int xFinal, int yFinal) {
		boolean ret = false;

		if ((yFinal == this.getY()-1 && xFinal == this.getX()+1) 
				|| (yFinal == this.getY()-1 && xFinal == this.getX()-1)) {
			ret = true;
		}
		return ret;
	}

}
