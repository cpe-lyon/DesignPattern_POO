package controller;

import shared.GUICoord;
import shared.GameMode;
import shared.ModelCoord;
import shared.PieceSquareColor;
/**
 * @author francoiseperrin
 * Cette classe contient les méthodes communes à tous les controller
 * 
 */
public abstract class AbstractController implements ChessController {

	public AbstractController() {
		super();
	}

	@Override
	public abstract boolean actionsWhenPieceIsSelectedOnGui(PieceSquareColor pieceSquareColor, GUICoord pieceToMoveCoord);

	@Override
	public abstract void actionsWhenPieceIsMovedOnGui(GUICoord targetCoord);

	@Override
	public abstract boolean actionsWhenPieceIsDraggedOnGui(PieceSquareColor pieceSquareColor, GUICoord pieceToMoveCoord);

	@Override
	public abstract void actionWhenGameModeIsChanged(GameMode gameMode);


	/**
	 * @param gUICoord
	 * @return GUICoord convertie en ModelCoord
	 */
	protected ModelCoord CoordToModelCoord(GUICoord gUICoord) {
		ModelCoord modelCoord = null;
		if(gUICoord != null) {
			char col = (char)(gUICoord.getX() + 'a');
			int ligne = 8 - gUICoord.getY();
			modelCoord = new ModelCoord(col, ligne);
		}
		return modelCoord;
	}

	/**
	 * @param modelCoord
	 * @return ModelCoord convertie en GUICoord
	 */
	protected GUICoord ModelCoordToCoord(ModelCoord modelCoord) {
		GUICoord gUICoord = null;
		if (modelCoord != null) {
			int x = modelCoord.getCol() -'a';
			int y = 8 - modelCoord.getLigne();
			gUICoord = new GUICoord(x, y);
		}
		return gUICoord;
	}

	
}
