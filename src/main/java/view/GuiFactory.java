package view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import shared.GUICoord;
import shared.PieceSquareColor;

public class GuiFactory {
	public static ObjectProperty<PaintStyle> paintStyle = new SimpleObjectProperty<PaintStyle>(PaintStyle.GRADIENT);
	public static ObjectProperty<PieceSquareColor> beginColor = new SimpleObjectProperty<PieceSquareColor>(PieceSquareColor.WHITE);
	public static ObjectProperty<Color> blackSquareColor = new SimpleObjectProperty<Color>(Color.rgb(139,69,0,1.0));
	public static ObjectProperty<Color> whiteSquareColor = new SimpleObjectProperty<Color>(Color.rgb(255,250,240,1.0));
	public static ObjectProperty<Color> lightColor = new SimpleObjectProperty<Color>(Color.BLUE);
	public static IntegerProperty height = new SimpleIntegerProperty(700);
	public static IntegerProperty width = new SimpleIntegerProperty(700);
	public static IntegerProperty nbLigne = new SimpleIntegerProperty(8);
	public static IntegerProperty nbColonne = new SimpleIntegerProperty(8);


	//////////////////////////////////////////////////////////////////////////////////////
	//
	// fabriques des cases et des pièces
	//
	///////////////////////////////////////////////////////////////////////////////////////

	public static ChessSquareGui createSquare(int col, int ligne) {
		ChessSquareGui square = null;
		PieceSquareColor squareColor;
		if((col%2==0 && ligne%2==0) || (col%2!=0 && ligne%2!=0)){
			squareColor = PieceSquareColor.BLACK;
		}
		else{
			squareColor = PieceSquareColor.WHITE;
		}
		GUICoord gUICoord = new GUICoord(col, ligne);
		
		/* Version sans Decorator ni Observer  */
		square = new SquareGui(gUICoord, squareColor);
		
		/* Version avec Decorator et Observer */
//		square = new SquareGuiDecorator(new SquareGui(gUICoord, squareColor)); 

		return square;
	}
	
	public static ChessPieceGui createPiece(int col, int ligne) {
		return PieceGUIFactory.createChessPieceGUI(new GUICoord(col, ligne) );
	}
	
	public static ChessPieceGui createPiece(String promotionType, PieceSquareColor pieceSquareColor) {
		return PieceGUIFactory.createChessPieceGUI(promotionType, pieceSquareColor);
	}
	
}
