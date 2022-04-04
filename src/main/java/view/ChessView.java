package view;


import java.util.List;

import shared.GUICoord;


/**
 * @author francoise.perrin
 *
 * Cette interface défini le comportement attendu par le damier de la view
 * 
 */

public interface ChessView {


	
	/**
	 * @param gUICoord
	 * Cette méthode est appelée par le CONTROLLER  en cas de 
	 * DragDetected() pour la pièce à déplacer invisible
	 */
	public void setPieceToMoveVisible(GUICoord gUICoord, boolean visible) ;

	/**
	 * @param gUICoords
	 * @param isLight
	 * 
	 * Cette méthode est appelée par le CONTROLLER  en cas de 
	 * mousePressed() : isLight = true, ou de
	 * mouseReleased() : isLignt = false
	 * Elle éclaire ou remet dans leur couleur d'origine les cases concernées 
	 * par les destinations possibles de la pièce à déplacer
	 */
	public void resetLight(List<GUICoord> gUICoords, boolean isLight) ;

	/**
	 * @param initCoord
	 * @param targetCoord
	 * 
	 * Cette méthode est appelée par le CONTROLLER en cas de dragDropped()
	 * uniquement dans le cas ou l'appel model.move() est OK
	 * la case de destination est recherchée :
	 * si elle contient une pièce, celle ci est supprimée
	 * puis la pièce est effectivement déplacée 
	 *  
	 * */
	 void movePiece(GUICoord initCoord, GUICoord targetCoord) ;

	/**
	 * @param coord
	 * 
	 * Cette méthode est appelée par le CONTROLLER  en cas de dragDropped()
	 * lorsque le déplacement est illégal
	 * la pièce est réaffichée à ses coordonnées d'origine 
	 *  
	 * */
	public void undoMovePiece(GUICoord pieceToMoveInitCoord) ;

	/**
	 * @return promotionType
	 * Cette méthode est appelée par le CONTROLLER  en cas de dragDropped()
	 * et que le model a indiqué qu'on était dans un cas de promotion du pion
	 * elle demande en quoi l'utilisateur veut promouvoir le pion
	 */
	public String getPromotionType() ;

	/**
	 * @param gUICoord
	 * @param promotionType
	 * 
	 * Cette méthode est appelée par le CONTROLLER  en cas de dragDropped()
	 * et que lemodel a indiqué qu'on était dans un cas de promotion du pion
	 * Elle change l'image de la pièce en fonction du choix de l'utilisateur
	 */
	public void promotePiece(GUICoord gUICoord, String promotionType);

}

