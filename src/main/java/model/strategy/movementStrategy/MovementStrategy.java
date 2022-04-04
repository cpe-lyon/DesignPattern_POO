package model.strategy.movementStrategy;

import shared.ActionType;

/**
 * @author francoise.perrin
 * 
 * Interface des différentes Stratégies de Mouvement
 */

public interface MovementStrategy {
	
	
	/**
	 * @param coord
	 * @return  true si déplacement légal en fonction des algo
	 * de déplacement spécifique de chaque pièce
	 */
	public boolean isMoveOk(int xInit, int yInit, int xFinal, int yFinal);

	/**
	 * @param coord
	 * @return ActionType
	 * @return  true si déplacement légal en fonction des algo
	 * de déplacement spécifique de chaque pièce dans le cas d'un déplacement avec prise
	 */
	public boolean isMoveOk(int xInit, int yInit, int xFinal, int yFinal, ActionType type);

	/**
	 * @param xInit
	 * @param yInit
	 * @param xFinal
	 * @param yFinal
	 * @param hasMoved
	 * @return
	 */
	boolean isMoveOk(int xInit, int yInit, int xFinal, int yFinal, boolean hasMoved);
}
