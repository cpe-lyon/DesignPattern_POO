package view;

import shared.PieceSquareColor;

/**
 * @author francoiseperrin
 * 
 * interface qui définit le comportement attendu des  pièces
 * les carrés seront implémentées par des ImageView dont l'image est
 * fixée à la création
 * 
 */
public interface ChessPieceGui   {

	public PieceSquareColor getCouleur() ;
}
