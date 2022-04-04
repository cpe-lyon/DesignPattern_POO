package model.noStrategy.pieces;


import java.util.LinkedList;
import java.util.List;

import shared.ModelCoord;
import shared.PieceSquareColor;
import tools.introspection.Introspection;

/**
 * @author francoise.perrin
 * Inspiration Jacques SARAYDARYAN, Adrien GUENARD
 * 
 * Classe qui fabrique une liste de pieces de jeu d'échec
 * de la couleur passée en paramètre
 *
 */
public class PieceModelFactory {

	static String chemin = "model.noStrategy.pieces.";

	/**
	 * private pour ne pas instancier d'objets
	 */
	private PieceModelFactory() {

	}
	enum ChessPiecePos {

		TOURBLANC("Tour", PieceSquareColor.WHITE, new ModelCoord[] {new ModelCoord('a',1), new ModelCoord('h',1)}),
		CAVALIERBLANC("Cavalier", PieceSquareColor.WHITE, new ModelCoord[] {new ModelCoord('b',1), new ModelCoord('g',1)}), 
		FOUBLANC("Fou", PieceSquareColor.WHITE, new ModelCoord[] {new ModelCoord('c',1), new ModelCoord('f',1)}), 
		REINEBLANC("Reine", PieceSquareColor.WHITE, new ModelCoord[] {new ModelCoord('d',1)}), 
		ROIBLANC("Roi", PieceSquareColor.WHITE, new ModelCoord[] {new ModelCoord('e',1)}),
//		PIONBLANC("Pion", Couleur.BLANC, new ModelCoord[] {new ModelCoord('a',2), new ModelCoord('b',2), new ModelCoord('c',2), new ModelCoord('d',2),
//				new ModelCoord('e',2), new ModelCoord('f',2), new ModelCoord('g',2), new ModelCoord('h',2)}),
		PIONBLANC("PionBlanc", PieceSquareColor.WHITE, new ModelCoord[] {new ModelCoord('a',2), new ModelCoord('b',2), new ModelCoord('c',2), new ModelCoord('d',2),
				new ModelCoord('e',2), new ModelCoord('f',2), new ModelCoord('g',2), new ModelCoord('h',2)}),
		
		TOURNOIR("Tour", PieceSquareColor.BLACK, new ModelCoord[] {new ModelCoord('a',8), new ModelCoord('h',8)}),
		CAVALIERNOIR("Cavalier", PieceSquareColor.BLACK, new ModelCoord[] {new ModelCoord('b',8), new ModelCoord('g',8)}), 
		FOUNOIR("Fou", PieceSquareColor.BLACK, new ModelCoord[] {new ModelCoord('c',8), new ModelCoord('f',8)}), 
		REINENOIR("Reine", PieceSquareColor.BLACK, new ModelCoord[] {new ModelCoord('d',8)}), 
		ROINOIR("Roi", PieceSquareColor.BLACK, new ModelCoord[] {new ModelCoord('e',8)}),
//		PIONNOIR("Pion", Couleur.NOIR, new ModelCoord[] {new ModelCoord('a',7), new ModelCoord('b',7), new ModelCoord('c',7), new ModelCoord('d',7),
//				new ModelCoord('e',7), new ModelCoord('f',7), new ModelCoord('g',7), new ModelCoord('h',7)})  
		PIONNOIR("PionNoir", PieceSquareColor.BLACK, new ModelCoord[] {new ModelCoord('a',7), new ModelCoord('b',7), new ModelCoord('c',7), new ModelCoord('d',7),
				new ModelCoord('e',7), new ModelCoord('f',7), new ModelCoord('g',7), new ModelCoord('h',7)})  
		; 

		public String nom;
		public PieceSquareColor pieceSquareColor;
		public  ModelCoord[] modelCoords = new ModelCoord[8] ;   

		ChessPiecePos( String nom, PieceSquareColor pieceSquareColor, ModelCoord[] modelCoords) { 
			this.nom = nom;
			this.pieceSquareColor = pieceSquareColor;
			this.modelCoords = modelCoords;
		} 
	}

	/**
	 * @param pieceCouleur
	 * @return liste de pièces de jeu d'échec
	 */
	public static List<ChessPieceModel> createPieceModelList(){
		String className;
		ModelCoord pieceModelCoord;
		PieceSquareColor pieceCouleur;
		List<ChessPieceModel> chessPieceModel = new LinkedList<ChessPieceModel>();

		for (int i = 0; i < ChessPiecePos.values().length; i++) {

			for (int j = 0; j < (ChessPiecePos.values()[i].modelCoords).length; j++) {
				className = chemin + ChessPiecePos.values()[i].nom;	// attention au chemin...
				pieceModelCoord = ChessPiecePos.values()[i].modelCoords[j];
				pieceCouleur = ChessPiecePos.values()[i].pieceSquareColor;
				chessPieceModel.add((ChessPieceModel) Introspection.newInstance (
						className, new Object[] {pieceCouleur, pieceModelCoord}));
			}
		}
		return chessPieceModel;
	}

	public static ChessPieceModel createPiece(PieceSquareColor pieceCouleur, String type, ModelCoord pieceModelCoord){

		ChessPieceModel piece = null;
		String className = chemin + type;	// attention au chemin		
		piece = (ChessPieceModel) Introspection.newInstance(className,new Object[] {pieceCouleur, pieceModelCoord});
		return piece;
	}

	/**
	 * Tests unitaires
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i = 0; i < ChessPiecePos.values().length; i++) {
			System.out.print(ChessPiecePos.values()[i].name() + " \t"); 
			System.out.print(ChessPiecePos.values()[i].nom + " \t"); 
			for (int j = 0; j < (ChessPiecePos.values()[i].modelCoords).length; j++) {
				System.out.print(ChessPiecePos.values()[i].modelCoords[j] + " "); 				
			}
			System.out.println();
		}
		System.out.println(PieceModelFactory.createPieceModelList()); 
		System.out.println(PieceModelFactory.createPiece(PieceSquareColor.WHITE, "Tour", new ModelCoord('a', 8))); 
		System.out.println(PieceModelFactory.createPiece(PieceSquareColor.WHITE, "PionNoir", new ModelCoord('e', 7))); 
	
		System.out.println(new Fou(PieceSquareColor.WHITE , new ModelCoord('c',8)));
	}


}