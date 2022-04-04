package model;

import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import model.noStrategy.pieces.ChessPieceModel;
import model.noStrategy.pieces.PieceModelFactory;

import shared.GameMode;
import shared.ModelCoord;
import shared.PieceSquareColor;

public class ModelFactory {

	public static ObjectProperty<PieceSquareColor> beginColor = new SimpleObjectProperty<PieceSquareColor>(PieceSquareColor.WHITE);
	public static IntegerProperty nbLigne = new SimpleIntegerProperty(8);
	public static IntegerProperty nbColonne = new SimpleIntegerProperty(8);
	public static ObjectProperty<GameMode> gameMode = new SimpleObjectProperty<GameMode>(GameMode.NORMAL);

	public final static ObjectProperty<GameMode> gameModeProperty() {		
		return gameMode;
	}

	//////////////////////////////////////////////////////////////////////////////////////
	//
	// fabriques des pièces
	//
	///////////////////////////////////////////////////////////////////////////////////////

	public static List<ChessPieceModel> createPieceModelList() {

		return PieceModelFactory.createPieceModelList();
	}

	public static ChessPieceModel createPiece(PieceSquareColor pieceCouleur, String type, ModelCoord pieceModelCoord){
		return PieceModelFactory.createPiece(pieceCouleur, type, pieceModelCoord);
	}

	
	//////////////////////////////////////////////////////////////////////////////////////
	//
	// Controle des coordonnées
	//
	///////////////////////////////////////////////////////////////////////////////////////

	/**
	 * @param x
	 * @param y
	 * @return true si les coordonnées passées en paramètre sont dans les limites du
	 *         plateau
	 */
	public static boolean coordonnees_valides(ModelCoord coord) {
		boolean ret = false;
		if (coord != null) {
			int x = coord.getCol() - 'a';
			int y = 8 - coord.getLigne();
			ret = (x <= nbColonne.get() - 1) && (x >= 0) && (y <= nbLigne.get()) && (y >= 0);
		}
		return ret;
	}



}
