package model.noStrategy.pieces;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import shared.ModelCoord;
import shared.PieceSquareColor;


/**
 * @author francoise.perrin - Alain BECKER
 * Inspiration Jacques SARAYDARYAN, Adrien GUENARD *
 */
public class Reine extends AbstractPiece {


	/**
	 * @param pieceSquareColor
	 * @param coord
	 */
	public Reine(PieceSquareColor pieceSquareColor, ModelCoord coord) {
		super(pieceSquareColor, coord);
	}

	@Override
	public boolean isAlgoMoveOk(ModelCoord finalCoord) { 
		int xFinal = finalCoord.getCol() -'a';
		int yFinal = 8 - finalCoord.getLigne();
		boolean ret = false;
		ret = isAlgoDiagOk(xFinal, yFinal) || isAlgoLigneOk(xFinal, yFinal);
		return ret;
	}
	
	private boolean isAlgoDiagOk(int xFinal, int yFinal) { 
		boolean ret = false;

		if (Math.abs(yFinal - this.getY()) == Math.abs(xFinal - this.getX())) {
			ret =  true;
		}
		return ret;
	}
	
	private boolean isAlgoLigneOk(int xFinal, int yFinal) { 
		boolean ret = false;

		if (((yFinal == this.getY()) || (xFinal == this.getX()))) {
			ret =  true;
		}
		return ret;
	}
	
	@Override
	public List<ModelCoord> getMoveItinerary(ModelCoord finalCoord) {
		int xFinal = finalCoord.getCol() -'a';
		int yFinal = 8 - finalCoord.getLigne();
		List<ModelCoord> ret = new LinkedList<ModelCoord>();
		
		ret.addAll(getEtapesDiag(xFinal, yFinal));
		ret.addAll(getEtapesLigne(xFinal, yFinal));
		
		return ret;
	}

	
	private List<ModelCoord> getEtapesLigne(int xFinal, int yFinal) {
		List<ModelCoord> ret = Collections.emptyList(); 

		// on vérifie que les coordonnées finales sont compatibles 
		//avec l'algo de déplacement 
		if (this.isAlgoLigneOk(xFinal, yFinal)) {

			ret = new LinkedList<ModelCoord>();
			int xVector = xFinal - this.getX();
			int yVector = yFinal - this.getY();
			
			// mouvement sur l'axe des X (y fixe)
			if (xVector != 0) {		
			
				int step = (int) Math.signum(xVector); 	// déplacement par pas de +1 ou -1
				for (int x = this.getX() + step ; x != xFinal ; x += step) {
					ret.add(new ModelCoord((char)('a'+x), (8-this.getY()))); 
				}
			} 
			// mouvement sur l'axe des Y (x fixe)
			else {
				int step = (int) Math.signum(yVector);	// déplacement par pas de +1 ou -1
				for (int y = this.getY() + step ; y != yFinal ; y += step) {
					ret.add(new ModelCoord((char)('a'+this.getX()), (8-y)));
				}
			}
		}
		return ret;
	}
	

	private List<ModelCoord> getEtapesDiag(int xFinal, int yFinal) {
		List<ModelCoord> ret = Collections.emptyList(); 
		
		// on vérifie que les coordonnées finales sont compatibles 
		//avec l'algo de déplacement 
		if (this.isAlgoDiagOk(xFinal, yFinal)) {

			ret = new LinkedList<ModelCoord>();
			int xVector = (int) Math.signum(xFinal - this.getX());
			int yVector = (int) Math.signum(yFinal - this.getY());

			int y = this.getY() + yVector;
			for (int x = this.getX() + xVector ; x!=xFinal ; x+=xVector) {
				
				ret.add(new ModelCoord((char)('a'+x), (8-y))); 
				y+=yVector;
			}
		}
		return ret;
	}


}
