package view;

import shared.GUICoord;

/**
 * @author francoiseperrin
 * 
 * interface qui définit le comportement attendu des cases du damier
 * les carrés seront implémentées par des BorderPane dont la couleur est
 * fixée à la création
 * 
 */
public interface ChessSquareGui   {

	/**
	 * @return the coord
	 */
	public GUICoord getCoord() ;

	/**
	 * @param isLight
	 * positionne la couleur en fonction du booléen :
	 * couleur d'origine du carré ou
	 * couleur d'affichage des cases de destinations possibles
	 */
	public void resetColor(boolean isLight) ;

	/**
	 * Permet de redessiner le carr� en cas de changement de couleur
	 * dans la factory
	 */
	public void paint();


}
