package controller;

import shared.GUICoord;
import shared.GameMode;
import shared.PieceSquareColor;

/**
 * @author francoiseperrin
 * 
 * Cette interface définit le comportement attendu de tous les controller
 * qui gèrent les communications entre la view et le model
 * qui ne se connaissent pas (DP Mediator) 
 * 
 * Cette interface est dérivée en 2 interfaces qui définissent les responsabilités 
 * côté view et côté model
 * Chacune étant implémentées par des controleur qui permettent d'éxécuter le progrramme
 * en mode local ou en mode client/server
 * 
 *
 */
public interface ChessController {

	/**
	 * @param pieceToMoveCoord
	 * 
	 * Cette méthode est appelée après sélection d'une pièce dans une view 
	 * (MousePressed sur ChessPieceGui).
	 * 
	 * Elle permet d'invoquer des méthodes du model (par exemple récupérer la liste 
	 * cases de destinations possibles d'une pièce sélectionnée)
	 * ou les méthodes de la view (par exemple,
	 * allumer les cases de destinations possibles)  
	 * 
	 * elle retourne true, si c'était bien à la pièce de se déplacer
	 */
	public boolean actionsWhenPieceIsSelectedOnGui(PieceSquareColor pieceSquareColor, GUICoord pieceToMoveCoord);
	
	/**
	 * @param pieceSquareColor
	 * @param pieceToMoveCoord
	 * 
	 * lorsque la pièce est déplacée, elle quite sa position d'origine 
	 * (DragDetected sur ChessPieceGui)
	 * et on peut la suivre des yeux au cours du déplacement
	 * 
	 * @return true, si c'était bien à la pièce de se déplacer
	 */
	public abstract boolean actionsWhenPieceIsDraggedOnGui(PieceSquareColor pieceSquareColor, GUICoord pieceToMoveCoord) ;
		
	/**
	 * @param pieceToMoveCoord
	 * @param targetCoord
	 * 
	 * Cette méthode est appelé lorsque la pièce a atteint sa destination dans une view 
	 * (DragDrop sur ChessSquareGui).
	 * 
	 * Elle permet d'invoquer des méthodes du model (par exemple move() ou pawnPromotion())
	 * ou les méthodes de la view (par exemple, déplacer effectivement la pièce 
	 * ou la repositionner à sa place initiale)  
	 */
	public void actionsWhenPieceIsMovedOnGui(GUICoord targetCoord);

	/**
	 * @param targetCoord
	 * 
	 * Cette méthode est appelé lorsque la pièce a atteint sa destination dans une view 
	 * (MouseReleased sur ChessSquareGui).
	 */
	public void actionsWhenPieceIsReleasedOnGui(GUICoord targetCoord) ;
	
	
	/**
	 * @param gameMode
	 * 
	 * Fixe le mode de jeu et par conséquent 
	 * le comportement attendu des pièces : Normal  ou Tempête 
	 * (MouseClicked sur GridView)
	 */
	public void actionWhenGameModeIsChanged(GameMode gameMode);
	
}
