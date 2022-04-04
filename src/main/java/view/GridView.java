package view;


import java.util.List;
import java.util.Optional;

import controller.ChessController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import shared.GUICoord;
import shared.GameMode;
import shared.PieceSquareColor;



/**
 * @author francoise.perrin
 *
 * cette classe implÃ©mente le damier de la view
 * elle a 2 responsabilitÃ©s :
 * elle gÃ©re les affichages
 * elle invoque les mÃ©thodes du controler Ã  travers ses Ã©couteurs
 *  
 * 
 */

class GridView extends GridPane implements ChessView{

	private ChessController chessController;
	public  IntegerProperty nbLigne = new SimpleIntegerProperty();
	public  IntegerProperty nbColonne = new SimpleIntegerProperty();
	private int nbLig ;
	private int nbCol;


	/**
	 * le constructeur construit les cases noires et blanches
	 * et positionne les images de piÃ¨ces dessus
	 * @param chessController 
	 * 
	 */
	GridView(ChessController chessController) {
		super();
		this.chessController = chessController;	
		nbLigne.bind(GuiFactory.nbLigne);
		nbColonne.bind(GuiFactory.nbColonne);
		nbLig = nbLigne.get();
		nbCol = nbColonne.get();

		// Les clics droits de souris sont Ã©coutÃ©s pour changer le mode de jeu
		// Normal ou Tempete
		this.addBoardListener();

		
		//////////////////////////////////////////////////////////////////////////////
		//
		// Mise en forme du quadrillage et ajout des piÃ¨ces sur le damier
		//
		//////////////////////////////////////////////////////////////////////////////

		BorderPane square = null;
		ImageView piece = null;

		for (int ligne = 0; ligne<nbLig; ligne++){
			for (int col = 0; col<nbCol; col++) {
				
				// CrÃ©ation du ChessSquareGui par la fabrique qui dÃ©cide de la couleur
				// en fonction des coordonnÃ©es
				square =   (BorderPane) GuiFactory.createSquare(col, ligne);
				
				// ajout du carre sur le damier
				square.prefWidthProperty().bind(this.widthProperty().divide(nbCol));
				square.prefHeightProperty().bind(this.heightProperty().divide(nbLigne));
				this.add(square, col, ligne);

				// ajout Ã©couteurs sur square
				addSquareListener(square) ;

				// Si une piÃ¨ce doit se trouver sur cette case :
				// fabrication de la piÃ¨ceGUI
				piece = (ImageView) GuiFactory.createPiece(col, ligne);


				// ajout de la piÃ¨ceGUI sur le carre
				if (piece != null) {
					piece.fitWidthProperty().bind(square.widthProperty().divide(1.5));
					piece.fitHeightProperty().bind(square.heightProperty().divide(1.1));
					square.getChildren().add(piece);
				}

				// ajout Ã©couteurs sur piece
				if (piece != null) {
					addPieceListener(piece);
				}

			}
		}
	}

	//////////////////////////////////////////////////////////////////////////////
	//
	// Gestion des Ã©vÃ¨nements souris et du drag & drop
	//
	//////////////////////////////////////////////////////////////////////////////

	private void addBoardListener() {
		this.setOnMouseClicked(event -> {
			if (event.getButton() == MouseButton.SECONDARY) {
				String[] choices = {"Normal", "Tempête"};
				ChoiceDialog<String> cDial = new ChoiceDialog<>(choices[0], choices);
				cDial.setTitle("Choix mode de jeu");
				cDial.setHeaderText("Veuillez choisir le mode de jeu");
				cDial.setContentText("Normal/Tempête :");
				Optional<String> selection = cDial.showAndWait();
				String choice = selection.orElseGet(()->"null");
				GameMode mode  = "Normal".equals(choice) ? GameMode.NORMAL : GameMode.STORM;
				GridView.this.chessController.actionWhenGameModeIsChanged(mode);
			}
			event.consume();
		});
	}

	private void addPieceListener(ImageView piece) {

		piece.setOnMousePressed(new EventHandler <MouseEvent>() {
			public void handle(MouseEvent event) {

				// Si la piÃ¨ce sÃ©lectionnÃ©e appartient bien au joueur courant
				// le controleur demande au damier d'allumer les cases de destinations possibles

				GridView.this.chessController.actionsWhenPieceIsSelectedOnGui(((ChessPieceGui) piece).getCouleur(), ((ChessSquareGui) piece.getParent()).getCoord());
				event.consume();

			}
		});

		piece.setOnDragDetected(new EventHandler <MouseEvent>() {
			public void handle(MouseEvent event) {
				if(GridView.this.chessController.actionsWhenPieceIsDraggedOnGui(
				((ChessPieceGui) piece).getCouleur(), ((ChessSquareGui) piece.getParent()).getCoord() )){
	
					/* allow any transfer mode */
					Dragboard db = GridView.this.startDragAndDrop(TransferMode.ANY);

					/* put a image on dragboard */
					ClipboardContent content = new ClipboardContent();
					content.putImage(((ImageView) piece).getImage());
					db.setContent(content);
				}
				event.consume();

			}
		});
	}

	private void addSquareListener(BorderPane square) {

		square.setOnDragOver(new EventHandler <DragEvent>() {
			public void handle(DragEvent event) {

				/* accept it only if it is  not dragged from the same node 
				 * and if it has a image data */
				if (event.getGestureSource() != square &&
						event.getDragboard().hasImage()) {

					/* allow for both copying and moving, whatever user chooses */
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}
				event.consume();
			}
		});


		square.setOnDragDropped(new EventHandler <DragEvent>() {
			public void handle(DragEvent event) {

				/* if there is a image data on dragboard, read it and use it */
				Dragboard db = event.getDragboard();
				boolean success = false;
				if (db.hasImage()) {
					success = true;

					// Le controller invoque la mÃ©thode move() du model
					// si move OK, il deande Ã  la vue de valider le dÃ©placement
					// sinon de repositionner la piÃ¨ce Ã  ses coordonnÃ©es initiales
					chessController.actionsWhenPieceIsMovedOnGui(((ChessSquareGui) square).getCoord());
					// le controller demande Ã  la vue de rÃ©nitialiser 
					// les couleurs des cases de destinations possibles
					chessController.actionsWhenPieceIsReleasedOnGui(((ChessSquareGui) square).getCoord());
						
				}
				/* let the source know whether the image was successfully 
				 * transferred and used */
				event.setDropCompleted(success);
				event.consume();
			}
		});

		square.setOnMouseReleased(new EventHandler <MouseEvent>() {
			public void handle(MouseEvent event) {

				// le controller demande Ã  la vue de rÃ©nitialiser 
				// les couleurs des cases de destinations possibles
				chessController.actionsWhenPieceIsReleasedOnGui(((ChessSquareGui) square).getCoord());
				
				event.consume();
			}
		});
	}


	//////////////////////////////////////////////////////////////////////////////
	//
	// MÃ©thodes invoquÃ©es par le controller aprÃ¨s rÃ©ponse du model
	// En rÃ©alitÃ©, le controller invoque les mÃ©thodes Ã©ponymes de la classe View
	// qui propage l'appel au damier
	//
	//////////////////////////////////////////////////////////////////////////////

	@Override
	public void resetLight(List<GUICoord> listGUICoords, boolean isLight) {
		ChessSquareGui square;

		if (listGUICoords != null) {
			for (GUICoord gUICoord : listGUICoords) {
				square = (ChessSquareGui) this.getChildren().get(nbLig*gUICoord.getY()+gUICoord.getX());
				
				square.resetColor(isLight);
			}
		}

	}

	@Override
	public void movePiece(GUICoord initCoord, GUICoord targetCoord) {
		
		// Suppression piÃ¨ce Ã©ventuelle sur case de destination
		BorderPane targetSquare = this.findSquare(targetCoord);;
		if (targetSquare != null && !targetSquare.getChildren().isEmpty()){
			int index = targetSquare.getChildren().size()-1;
			if (targetSquare.getChildren().get(index) instanceof ImageView)
			targetSquare.getChildren().remove(index);
			
		}

		// Ajout piÃ¨ce dÃ©placÃ©e sur case de destination
		ImageView pieceToMove = this.findPiece(initCoord);
		if (pieceToMove != null) { 
			pieceToMove.setVisible(true);
			targetSquare.getChildren().add(pieceToMove);
		}
		
	}

	@Override
	public void setPieceToMoveVisible(GUICoord gUICoord, boolean visible) {
		ImageView pieceToMove = this.findPiece(gUICoord);
		if (pieceToMove != null) {
			pieceToMove.setVisible(visible);
		}
	}

	@Override
	public void undoMovePiece(GUICoord pieceToMoveInitCoord) {
		this.setPieceToMoveVisible(pieceToMoveInitCoord, true);
	}

	@Override
	public String getPromotionType() {
		String promotionType = null;

		String[] choices = {"Reine", "Tour", "Fou", "Cavalier"};
		ChoiceDialog<String> cDial = new ChoiceDialog<>(choices[2], choices);
		cDial.setTitle("Promotion du pion");
		cDial.setHeaderText("Veuillez indiquer en quelle piÃ¨ce vous souhaitez promouvoir votre pion");
		cDial.setContentText("PiÃ¨ce:");
		Optional<String> selection = cDial.showAndWait();
		promotionType = selection.get();

		return promotionType;
	}

	/**
	 * @param gUICoord
	 * @param promotionType
	 * 
	 * Supprime la piÃ¨ce du carrÃ©
	 * et la remplace par celle du type de la promotion
	 */
	@Override
	public void promotePiece(GUICoord gUICoord, String promotionType) {

		BorderPane square = this.findSquare(gUICoord);
		
		if (square != null && !square.getChildren().isEmpty()){
			int index = square.getChildren().size()-1;
			PieceSquareColor pieceSquareColor = ((ChessPieceGui) square.getChildren().get(index)).getCouleur();
			square.getChildren().remove(index);
			ImageView newPieceGUI = (ImageView) GuiFactory.createPiece(promotionType, pieceSquareColor);
			this.addPieceListener(newPieceGUI);
			square.getChildren().add(newPieceGUI);
		}

	}


	private ImageView findPiece(GUICoord gUICoord) {
		ImageView pieceGui = null;
		BorderPane square = (BorderPane) this.getChildren().get(nbLig*gUICoord.getY()+gUICoord.getX());
		if (square != null && !square.getChildren().isEmpty()){
			
			pieceGui = (ImageView) square.getChildren().get(square.getChildren().size()-1);
		}
		return pieceGui;
	}

	private BorderPane findSquare(GUICoord gUICoord) {
		BorderPane square = null;
		square = (BorderPane) this.getChildren().get(nbLig*gUICoord.getY()+gUICoord.getX());
		return square;
	}
	

}

