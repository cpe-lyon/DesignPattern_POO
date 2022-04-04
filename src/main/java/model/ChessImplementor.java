package model;

import shared.ActionType;
import shared.ModelCoord;
import shared.PieceSquareColor;




/**
 * @author francoise.perrin
 * Inspiration Jacques SARAYDARYAN, Adrien GUENARD 
 * 
 * Interface de toutes les implémentation de jeu d'échec
 * un objet ChessImplementor sera l'implémentation d'un objet ChessModel
 * il ne connait pas la logique métier d'un jeu d'échec
 * il sait communiquer avec les pièces
 * 
 * = mise en oeuvre du DP Bridge
 */
interface ChessImplementor  {

	/** 
	 * @param coord
	 * @param couleur
	 * @return true si une pièce se trouve aux coordonnées indiquées, dans le bon jeu
	 */
	boolean isPieceHere(ModelCoord coord, PieceSquareColor currentColor);

	/** 
	 * @param initCoord
	 * @param finalCoord
	 * @return true si  piece du jeu peut être déplacée aux coordonnées finales,
	 *  false sinon
	 */
	boolean isAlgoMoveOk(ModelCoord initCoord, ModelCoord finalCoord, ActionType type);

	/**
	 * Permet de deplacer une piece connaissant ses coordonnees initiales 
	 * vers ses coordonnees finales  
	 * @param initCoord
	 * @param finalCoord
	 * @return le type de déplacement (avec ou sans prise, etc.) 
	 */
	ActionType doMove(ModelCoord initCoord, ModelCoord finalCoord);

	/** 
	 * @param coord
	 * @return true si la piece aux coordonnées finales
	 * a été capturée
	 */
	ActionType doCatch(ModelCoord coord);

	/**
	 * @return
	 * rollback de la dernière capture effectuée
	 */
	boolean undoLastCapture();

	/**
	 * @return
	 * rollback du dernier déplacement effectué
	 */
	boolean undoLastMove();

	/** 
	 * @param promotionCoord
	 * @param promotionType
	 * @return true si la promotion du pion s'est bien effectuée
	 */
	public boolean pawnPromotion(ModelCoord promotionCoord, String promotionType);

	/**
	 * @param currentColor
	 * @return les coordonnées du roi
	 */
	ModelCoord getKingCoord(PieceSquareColor currentColor);

	/** 
	 * @param initCoord
	 * @param finalCoord
	 * @return true si l'itinéraire d'une case de départ vers une case d'arrivée est vide
	 */
	boolean isItineraryEmpty(ModelCoord initCoord, ModelCoord finalCoord);










}

