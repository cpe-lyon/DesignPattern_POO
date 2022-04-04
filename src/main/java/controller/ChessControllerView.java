package controller;

import view.ChessView;

public interface ChessControllerView extends ChessController {
	
	/**
	 * @param chessGUI
	 * 
	 * Fixe la view avec laquelle le controller dialogue
	 */
	public void setView (ChessView chessGUI);
	
}
