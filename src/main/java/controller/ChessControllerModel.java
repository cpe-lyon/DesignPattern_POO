package controller;

import model.ChessModel;

public interface ChessControllerModel extends ChessController {
	
	/**
	 * @param chessGameModel
	 * 
	 * Fixe le model avec lequel le controller dialogue
	 */
	public void setModel(ChessModel chessModel);
	
}
