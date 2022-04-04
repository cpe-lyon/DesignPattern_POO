package view;

//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.scene.image.Image;
import shared.GUICoord;
import shared.PieceSquareColor;
/**
 * @author francoise.perrin
 * Inspiration Jacques SARAYDARYAN, Adrien GUENARD
 * 

 *  
 */
public class PieceGUIFactory {

	private static Map<String, List<GUICoord>> mapPieceInitCoords = new HashMap<String, List<GUICoord>>();
	private static Map<String, String> mapPieceImage = new HashMap<String, String> ();
	private static Map<PieceSquareColor, List<String>> mapPieceColor = new HashMap<PieceSquareColor, List<String>> ();

	static {	
		mapPieceInitCoords.put("TourWHITE", Arrays.asList(new GUICoord[] {new GUICoord(0,7), new GUICoord(7,7)}));
		mapPieceInitCoords.put("CavalierWHITE", Arrays.asList(new GUICoord[] {new GUICoord(1,7), new GUICoord(6,7)}));
		mapPieceInitCoords.put("FouWHITE", Arrays.asList(new GUICoord[] {new GUICoord(2,7), new GUICoord(5,7)}));
		mapPieceInitCoords.put("ReineWHITE", Arrays.asList(new GUICoord[] {new GUICoord(3,7)}));
		mapPieceInitCoords.put("RoiWHITE", Arrays.asList(new GUICoord[] {new GUICoord(4,7)}));
		mapPieceInitCoords.put("PionWHITE", Arrays.asList(new GUICoord[] {new GUICoord(0,6), new GUICoord(1,6), new GUICoord(2,6), new GUICoord(3,6),
				new GUICoord(4,6),  new GUICoord(5,6), new GUICoord(6,6), new GUICoord(7,6)}));
		mapPieceInitCoords.put("TourBLACK", Arrays.asList(new GUICoord[] {new GUICoord(0,0), new GUICoord(7,0)}));
		mapPieceInitCoords.put("CavalierBLACK", Arrays.asList(new GUICoord[] {new GUICoord(1,0), new GUICoord(6,0)}));
		mapPieceInitCoords.put("FouBLACK", Arrays.asList(new GUICoord[] {new GUICoord(2,0), new GUICoord(5,0)}));
		mapPieceInitCoords.put("ReineBLACK", Arrays.asList(new GUICoord[] {new GUICoord(3,0)}));
		mapPieceInitCoords.put("RoiBLACK", Arrays.asList(new GUICoord[] {new GUICoord(4,0)}));
		mapPieceInitCoords.put("PionBLACK", Arrays.asList(new GUICoord[] {new GUICoord(0,1), new GUICoord(1,1), new GUICoord(2,1), new GUICoord(3,1),
				new GUICoord(4,1), new GUICoord(5,1), new GUICoord(6,1), new GUICoord(7,1)}));
	}

	static {	
		mapPieceImage.put("TourWHITE", "tourBlancS.png");
		mapPieceImage.put("CavalierWHITE", "cavalierBlancS.png");
		mapPieceImage.put("FouWHITE",  "fouBlancS.png");
		mapPieceImage.put("ReineWHITE", "reineBlancS.png");
		mapPieceImage.put("RoiWHITE", "roiBlancS.png");
		mapPieceImage.put("PionWHITE", "pionBlancS.png");

		mapPieceImage.put("TourBLACK", "tourNoireS.png");
		mapPieceImage.put("CavalierBLACK", "cavalierNoirS.png");
		mapPieceImage.put("FouBLACK", "fouNoirS.png");
		mapPieceImage.put("ReineBLACK", "reineNoireS.png");
		mapPieceImage.put("RoiBLACK", "roiNoirS.png");
		mapPieceImage.put("PionBLACK", "pionNoirS.png")  ;
	}

	static {	
		mapPieceColor.put(PieceSquareColor.WHITE, Arrays.asList(new String[] {"TourWHITE","CavalierWHITE","FouWHITE","ReineWHITE","RoiWHITE","PionWHITE"}));
		mapPieceColor.put(PieceSquareColor.BLACK,Arrays.asList(new String[] {"TourBLACK","CavalierBLACK","FouBLACK","ReineBLACK","RoiBLACK","PionBLACK"}));
	}
	

	/**
	 * private pour ne pas instancier d'objets
	 */
	private PieceGUIFactory() {

	}	

	public static ChessPieceGui createChessPieceGUI(GUICoord gUICoord) {
		ChessPieceGui chessPieceGUI = null;
		Image image = null;
		PieceSquareColor pieceSquareColor = null;
		
		// Recherche type de la piece aux coordonnées coord
		Set<String> pieceTypeSet = mapPieceInitCoords.keySet();
		for (String pieceType : pieceTypeSet) {
			List<GUICoord> listCoords = mapPieceInitCoords.get(pieceType);
			if (listCoords!= null && listCoords.contains(gUICoord)) {
				
				// recheche de la couleur correspondante
				pieceSquareColor = getCouleur(pieceType);
				
				// recherche du fichier de l'image correspondante et fabrication image
				image = createImage(pieceType);		
				
				// création pièce
				chessPieceGUI = new PieceGui( pieceSquareColor,  image );
			}
		}
		
		return chessPieceGUI;
	}

	public static ChessPieceGui createChessPieceGUI(String promotionType, PieceSquareColor pieceSquareColor) {
		return new PieceGui(pieceSquareColor, createImage(promotionType + pieceSquareColor.name()) );
	}

	private static Image createImage(String pieceType) {
		
		Image image = null;
//		String pieceImageFile = null;
		String nomImageFile = null;
//		File g=new File("");
		
		nomImageFile = mapPieceImage.get(pieceType);
		
//		pieceImageFile = g.getAbsolutePath()+"/images/" + nomImageFile;	// TODO - attention au chemin
//		try {
//			image = new Image(new FileInputStream(pieceImageFile));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
		image = new Image( PieceGUIFactory.class.getResourceAsStream("images/" + nomImageFile));
		return image;
	}
	
	private static PieceSquareColor getCouleur(String pieceType) {
		PieceSquareColor couleurPiece = null;
		
		Set<PieceSquareColor> couleurSet = mapPieceColor.keySet();
		for (PieceSquareColor pieceSquareColor : couleurSet) {	
			List<String> listPieceType = mapPieceColor.get(pieceSquareColor);
			if (listPieceType!= null && listPieceType.contains(pieceType)) {
				couleurPiece = pieceSquareColor;
			}
		}
		return couleurPiece;
	}
	

}
