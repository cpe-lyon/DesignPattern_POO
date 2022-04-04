package view;

import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import shared.GUICoord;

import controller.ChessController;

public class View extends GridPane implements ChessView {

	// le damier composé de carrés noirs et blancs
	// sur lesquels sont positionnés des pièces noires ou blanches
	GridView gridView ;

	public View(ChessController controller) {
		super();

		// le menu qui permet de changer la configuration d'affichage
		MenuBar customMenuBar = new MenuView();


		Pane board = new GridView(controller);
		gridView = (GridView) board;

		// les tailles du menu et du damier sont fonction de taille de la Scene
		customMenuBar.prefWidthProperty().bind(this.widthProperty());
		board.prefWidthProperty().bind(this.widthProperty());
		board.prefHeightProperty().bind(this.heightProperty());

		// ajout du damier et des axes à la vue
		BorderPane checkersBoard = new BorderPane();	
		checkersBoard.setTop(createHorizontalAxis());
		checkersBoard.setBottom(createHorizontalAxis());
		checkersBoard.setLeft(createVerticalAxis());
		checkersBoard.setRight(createVerticalAxis());
		checkersBoard.setCenter(board);
		this.add(checkersBoard, 0, 1);

		// ajout menu à la vue
		this.add(customMenuBar, 0, 0);
	}

	private GridPane createHorizontalAxis() {
		GridPane pane = new GridPane();

		for (char c = 'a'; c<='h'; c++){
			Label label1 = new Label(String.valueOf(c));
			label1.setAlignment(Pos.CENTER);
			label1.setPrefHeight(10);
			label1.setPrefWidth(100);
			pane.add(label1, c-'a', 0);
		}
		return pane;
	}

	private GridPane createVerticalAxis() {
		GridPane pane = new GridPane();
		for (int c = 8; c>=1; c--){
			Label label1 = new Label(String.valueOf(c));
			label1.setPrefHeight(100);
			label1.setPrefWidth(15);
			pane.add(label1, 0, 8-c+1);
		}
		return pane;
	}


	@Override
	public void setPieceToMoveVisible(GUICoord gUICoord, boolean visible){
		this.gridView.setPieceToMoveVisible( gUICoord, visible);
	}

	@Override
	public void resetLight(List<GUICoord> gUICoords, boolean isLight) {
		this.gridView.resetLight( gUICoords,  isLight);
	}

	@Override
	public
	void movePiece(GUICoord initCoord, GUICoord targetCoord) {
		this.gridView.movePiece(initCoord, targetCoord);
	}

	@Override
	public void undoMovePiece(GUICoord pieceToMoveInitCoord) {
		this.gridView.undoMovePiece( pieceToMoveInitCoord);
	}

	@Override
	public String getPromotionType() {
		return this.gridView.getPromotionType() ;
	}

	@Override
	public void promotePiece(GUICoord gUICoord, String promotionType) {
		this.gridView.promotePiece( gUICoord,  promotionType);
	}


}
