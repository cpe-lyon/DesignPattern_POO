package model.noStrategy.pieces;

import java.util.List;

import model.ModelFactory;
import shared.ActionType;
import shared.ModelCoord;
import shared.PieceSquareColor;



/**
 * @author francoise.perrin - Alain BECKER
 * Inspiration Jacques SARAYDARYAN, Adrien GUENARD
 * 
 * Gère le comportement commun à toutes les pièces
 * Chaque classe dérivée (Pion, etc.) sera capable de dire 
 * si le déplacement est OK.
 * 
 * 
 * 
 * ATTENTION, DANS LA VERSION PRECEDENTE,
 * TOUS LES ALGOS SUR LES PIECES CONSIDERAIENT DES COORDONNEES x et y DE TYPE int
 * L'INTRODUCTION DES Coord SERT POUR L'INTERFACE AVEC LE ChessImplementor
 * MAIS EN INTERNE, LES COORDONÉES RESTENT IMPLEMENTÉES AVEC x et y 
 * 
 * 
 * 
 */
public abstract class  AbstractPiece implements ChessPieceModel {

	private int x, y;
	private PieceSquareColor pieceSquareColor;

	/** Constante représentant l'absence d'"état antérieur" pour une pièce **/
	private static final int THERE_IS_NO_BEFORE = -1;
	/** Constante représentant l'état "capturé" d'une pièce **/
	private static final int CAPTURED = -1;

	private int xBeforeCapture = THERE_IS_NO_BEFORE;
	private int yBeforeCapture = THERE_IS_NO_BEFORE;
	private int xBeforeMove = THERE_IS_NO_BEFORE;
	private int yBeforeMove = THERE_IS_NO_BEFORE;


	/**
	 * @param pieceSquareColor
	 * @param coord
	 */
	public AbstractPiece(PieceSquareColor pieceSquareColor, ModelCoord coord){
		super();
		this.x = coord.getCol() - 'a';
		this.y = 8 - coord.getLigne();
		this.pieceSquareColor=pieceSquareColor;
	}

	@Override
	public ModelCoord getCoord() {
		return new ModelCoord((char)(this.x + 'a'), 8 - this.y);
	}


	@Override
	public PieceSquareColor getCouleur(){
		return this.pieceSquareColor;
	}
	@Override
	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public ActionType doMove (ModelCoord coord){
		ActionType isMoveOk = ActionType.UNKNOWN;

		if(ModelFactory.coordonnees_valides(coord)){	// vérifie aussi que coord != null
			this.xBeforeMove = this.x;
			this.yBeforeMove = this.y; // Mémorisation pour undoLastMove()
			this.x = coord.getCol() - 'a';
			this.y = 8 - coord.getLigne();
			isMoveOk = ActionType.MOVE;
		}
		return isMoveOk;

	}

	@Override
	public boolean undoLastMove(){
		boolean ret = false;
		if ( (this.x == THERE_IS_NO_BEFORE) || (this.y == THERE_IS_NO_BEFORE)) {
			throw new IllegalStateException("Tentative d'annulation de mouvement incohérente!");
		}
		this.x=this.xBeforeMove;
		this.y=this.yBeforeMove;
		this.xBeforeMove = this.yBeforeMove = THERE_IS_NO_BEFORE;


		return ret;
	}

	@Override
	public boolean catchPiece(){

		if ( (this.x == CAPTURED) || (this.y == CAPTURED)) {
			throw new IllegalStateException("Tentative de capture d'une pièce déjà capturée ?!");
		}
		this.xBeforeCapture = this.x;
		this.yBeforeCapture = this.y; // Mémorisation pour undoCaptured()
		this.x = CAPTURED;
		this.y = CAPTURED;
		return true;
	}

	@Override
	public final boolean undoLastCatch(){
		if ( (this.x != CAPTURED) || (this.y != CAPTURED)) {
			throw new IllegalStateException("Tentative de restauration d'une pièce non capturée ?!");
		}
		this.x = this.xBeforeCapture;
		this.y = this.yBeforeCapture;
		this.xBeforeCapture = this.yBeforeCapture = THERE_IS_NO_BEFORE;
		return true;
	}

	@Override
	public String toString(){	
		String S = (this.getClass().getSimpleName()).substring(0, 2) 
				+ " " + (char)(this.x+'a') + " " + (8-this.y);
		return S;
	}

	@Override
	public boolean isAlgoMoveOk(ModelCoord finalCoord, ActionType type) {
		// par défaut, les pièces prennent comme elles se déplacent
		return isAlgoMoveOk( finalCoord); 
	}

	@Override
	public abstract boolean isAlgoMoveOk(ModelCoord finalCoord);

	@Override
	public abstract List<ModelCoord> getMoveItinerary(ModelCoord finalCoord);


	int getX(){
		return this.x;
	}

	int getY(){
		return this.y;
	}
}

