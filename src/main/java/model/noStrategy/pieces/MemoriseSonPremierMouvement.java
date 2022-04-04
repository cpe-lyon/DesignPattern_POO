package model.noStrategy.pieces;

/**
 *  <p>
 *  Cette interface est porteuse de la définition d'un attribut, via son getter,
 *  qui concerne les pièces dont le comportement varie selon qu'elles ont été déplacées
 *  au moins une fois depuis le début de la partie, à savoir :
 *  </p>
 *  <ul>
 *  	<li>le pion (qui peut avancer de 2 cases (2 fois une case) s'il n'a jamais bougé)</li>
 *  	<li>le roi (qui ne peut roquer, entre autres, que s'il n'a jamais été déplacé)</li>
 *  	<li>la tour (cas du roque, <em>idem supra</em>)</li>
 *  </ul>
 * 
 * @author Alain BECKER
 *
 */
public interface MemoriseSonPremierMouvement {
	
	/**
	 * Permet de savoir si la pièce a effectué (au moins) un mouvement depuis le début de la partie
	 * (ou si elle n'a jamais quitté sa position initiale)
	 * @return <code>true</code> si la pièce n'a jamais été déplacée, 
	 * 			<code>false</code> sinon.
	 */
	 boolean hasMoved();
	
}
