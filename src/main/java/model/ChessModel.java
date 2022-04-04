package model;

import java.util.List;

import shared.ActionType;
import shared.ModelCoord;



/**
 * @author francoise.perrin
 * 
 * Cette interface défini le comportement (métier) attendu 
 * des jeux d'échec
 *
 */
public interface ChessModel {
	
	/**
	 * @param initCoord
	 * @return la liste des destination possibles d'une Pieces
	 * connaissant ses coordonnees  
	 */
	List<ModelCoord> getPieceListMoveOK(ModelCoord initCoord);

	/**
	 * Permet de deplacer une piece connaissant ses coordonnees initiales 
	 * vers ses coordonnees finales 	 
	 * @param initCoord
	 * @param finalCoord
	 * @return le type de déplacement (avec ou sans prise, etc.) 
	 */
	ActionType move(ModelCoord initCoord, ModelCoord finalCoord);
	
	/** 
	 * @param promotionCoord
	 * @param promotionType
	 * Permet de promouvoir un pion
	 * @return true si la promotion a été effectuée
	 */
	public boolean pawnPromotion(ModelCoord promotionCoord, String promotionType) ;

	/**
	 * @return true si c'est la fin du jeu
	 */
	public boolean isEnd();

	
}

