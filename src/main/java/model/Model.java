package model;

import java.util.LinkedList;
import java.util.List;


import shared.ActionType;
import shared.ModelCoord;
import shared.PieceSquareColor;


/**
 * @author francoise.perrin - Alain BECKER
 * Inspiration Jacques SARAYDARYAN, Adrien GUENARD
 * 
 * <p>La clase ChessModel sert de facade aux ChessGameImplementor et Pieces
 * qui ne sont pas accessibles de l'extérieur
 * C'est elle qui gére toute la logique métier des déplacements 
 * mais elle n'accède pas directement aux Pieces
 * 
 * L'accès aux Pieces est de la responsabilité d'un ChessGameImplementor</p>
 *
 * (Mise en oeuvre DP Bridge)
 */

public class Model implements ChessModel {

	private PieceSquareColor currentPlayer, unCurrentPlayer;
	private ChessImplementor chessImplementor;
	private int nbLigne;
	private int nbColonne;

	public Model() {
		super();
		this.chessImplementor = new Implementor();
		this.currentPlayer = ModelFactory.beginColor.get();
		this.unCurrentPlayer = (PieceSquareColor.WHITE.equals(this.currentPlayer) ? PieceSquareColor.BLACK : PieceSquareColor.WHITE);
		this.nbLigne = ModelFactory.nbLigne.get();
		this.nbColonne = ModelFactory.nbColonne.get();
		//		this.currentPlayer = ChessModelConfig.getBeginColor();
		//		this.unCurrentPlayer = (PieceSquareColor.WHITE.equals(this.currentPlayer) ? PieceSquareColor.BLACK : PieceSquareColor.WHITE);
		//		this.nbLigne = ChessModelConfig.getNbLigne();
		//		this.nbColonne = ChessModelConfig.getNbColonne();
	}

	@Override
	public String toString() {		
		String st = "";
		st +=  this.chessImplementor.toString() + "\n";
		return st;
	}

	@Override
	public boolean pawnPromotion(ModelCoord promotionCoord, String promotionType) {
		return chessImplementor.pawnPromotion(promotionCoord, promotionType);
	}

	@Override
	public boolean isEnd(){
		// ToDo
		return false;		
	}


	/* (non-Javadoc)
	 * @see model.ChessGameModel#getPieceListMoveOK(int, int)
	 * 
	 * Attention version simplifiée :
	 * propose toutes les destinations possibles y compris celles qui
	 * mettraient le roi en échec ...
	 * 
	 */
	@Override
	public List<ModelCoord> getPieceListMoveOK(ModelCoord initCoord) {
		List<ModelCoord> ret = new LinkedList<ModelCoord>();
		ModelCoord finalCoord = null;

		for (int xFinal = 0; xFinal < this.nbColonne; xFinal++)
			for (int yFinal = 0; yFinal < this.nbLigne; yFinal++){
				finalCoord = new ModelCoord((char)(xFinal + 'a'), 8 - yFinal);
				// Si déplacement possible
				ActionType action = this.isMoveOK(initCoord, finalCoord);
				if (!ActionType.ILLEGAL.equals(action)){	
					ret.add(finalCoord);
				}
			}
		return ret;
	}


	@Override
	public ActionType move(ModelCoord initCoord, ModelCoord finalCoord) {

		ActionType effectiveAction = null;
		final boolean TRACE = false;	// booléen à activer si l'on veut suivre la trace d'exécution

		///////////////////////////////////////////////////////////////////
		// Si le déplacement est possible, c'est à dire que :
		// c'est bien au bon joueur de jouer 
		// il n'y a pas de collision ou bien il y a une piece à capturer
		// alors on continue, sinon on sort
		/////////////////////////////////////////////////////////////////////

		effectiveAction = this.isMoveOK(initCoord, finalCoord);
		if (ActionType.ILLEGAL.equals(effectiveAction)){
			if(TRACE) {System.out.println("Déplacement impossible : KO, " + effectiveAction);}
			return ActionType.ILLEGAL;
		}

		//////////////////////////////////////////////////////////////////////////////
		// à ce niveau, on veut vraiment deplacer la piece
		// on active donc le deplacement effectif et la capture eventuelle
		/////////////////////////////////////////////////////////////////////////////

		effectiveAction = this.moveAndCatch(initCoord, finalCoord, effectiveAction) ;
		if (effectiveAction == ActionType.ILLEGAL){
			if(TRACE) {System.out.println("Déplacement effectif : KO, " + effectiveAction);}
			return effectiveAction; // if illegal move, break early
		}

		///////////////////////////////////////////////////////////////////////////
		// Après le déplacement et ou la capture, 
		// effectiveAction vaut MOVE, TAKE, PROMOTION 
		// on sera peut etre amene a remobiner si le deplacement ou la capture ont 
		// mis le roi en echec
		//////////////////////////////////////////////////////////////////////////

		this.switchJoueur();

		if (this.isCheck( )){
			if(TRACE) {System.out.println("Roi du joueur courant est en échec : ROLLBACK " 
					+ effectiveAction);}
			this.switchJoueur();
			this.undoMoveCatch(initCoord, finalCoord, effectiveAction);
			return ActionType.ILLEGAL;
		}

		/////////////////////////////////////////////////////////////////////////
		// le déplacement s'est effectué correctement on a déjà changé de joueur
		// pour vérifier la non mise en échec
		// Si ce n'est pas la fin de la partie, effectiveAction aura la valeur
		// précédemment acquise
		/////////////////////////////////////////////////////////////////////////
		effectiveAction = this.isEnd() ? ActionType.END : effectiveAction;

		if(TRACE) {System.out.println("Déplacement et capture éventuelle OK "
				+ "et changement joueur effectué " + effectiveAction);}

		return effectiveAction;

	}

	/**
	 * Change le joueur courant
	 */
	private void switchJoueur() {
		PieceSquareColor temp = this.currentPlayer;
		this.currentPlayer = this.unCurrentPlayer;
		this.unCurrentPlayer = temp;
	}

	/**
	 * @param initCoord
	 * @param finalCoord
	 * @return ActionType correspondant à action possible
	 */
	private ActionType isMoveOK(ModelCoord initCoord, ModelCoord finalCoord) {

		final boolean TRACE = false;	// booléen à activer si l'on veut suivre la trace d'exécution
		ActionType action = ActionType.UNKNOWN;

		//////////////////////////////////////////////
		// Si les coordonnées initiales ou finales ne sont pas
		// dans les limites du plateau, on sort
		//////////////////////////////////////////////

		if ( ! ModelFactory.coordonnees_valides(initCoord) ||
				! ModelFactory.coordonnees_valides(finalCoord)) {
			action = ActionType.ILLEGAL;
			if(TRACE) {System.out.println("KO, coordonnées en dehors du plateau.");}
			return action;
		}

		//////////////////////////////////////////////
		// Si les coordonnées initiales ne correspondent pas
		// à une pièce du joueur courant, on sort
		//////////////////////////////////////////////

		if ( ! this.chessImplementor.isPieceHere(initCoord, this.currentPlayer) ) {
			action = ActionType.ILLEGAL;
			if(TRACE) {System.out.println("KO, pas une pièce du joueur courant.");}
			return action;
		} 

		//////////////////////////////////////////////
		// S'il existe une piece de la même couleur
		// aux coordonnées finales, on sort
		// sinon, elle pourrait être capturée
		//////////////////////////////////////////////


		// Si c'est une piece du joueur courant, le déplacement n'est pas possible
		if (this.chessImplementor.isPieceHere(finalCoord, this.currentPlayer)) { 
			action = ActionType.ILLEGAL;
			if(TRACE) {System.out.println("pièce du joueur courant " 
					+ this.currentPlayer 
					+ " à destination." + action);
			}
			return action; 

		} 
		else {
			// si c'est pièce du joueur adverse à l'arrivée, elle sera prise
			if (this.chessImplementor.isPieceHere(finalCoord, this.unCurrentPlayer)) { 
				action = ActionType.TAKE;	
				if(TRACE) {System.out.println("OK " + action);}

			}
			// s'il n'y a pas de pièce à l'arrivée 
			else { 
				action = ActionType.MOVE;
				if(TRACE) {System.out.println("OK " + action);}
			}
		}

		/////////////////////////////////////////////////////////////////////////////
		// ArrivÃ© à ce stade, l'action potentielle est MOVE ou TAKE
		// On vérifie que l'algorithme de déplacement est conforme au type de piece
		/////////////////////////////////////////////////////////////////////////////

		if (!this.chessImplementor.isAlgoMoveOk(initCoord, finalCoord, action)) {	
			action = ActionType.ILLEGAL;
			if(TRACE) {
				System.out.println("KO : algo déplacement non conforme" + action);}
			return action; 
		}

		//////////////////////////////////////////////////////////////////
		// Si l'algorithme de déplacement est conforme
		// on  vérifie alors s'il n'y a pas de pieces sur la trajectoire
		//
		// REMARQUE : on pourrait supprimer la vérification précédente parce que
		// isItineraryEmpty() va aussi vérifier que l'algo est conforme ...
		//
		/////////////////////////////////////////////////////////////////

		boolean laVoieEstLibre = chessImplementor.isItineraryEmpty(initCoord, finalCoord);
		if (!laVoieEstLibre) {
			action = ActionType.ILLEGAL;
			if(TRACE) {
				System.out.println("KO : Piece sur la trajectoire" + action);
			}
			return action; 
		}

		//////////////////////////////////////////////////////////////////
		// Si on n'est pas déjà sorti de la fonction
		// c'est que le déplacement est possible avec prise éventuelle
		/////////////////////////////////////////////////////////////////

		if(TRACE) {System.out.println(" DéplacementOK " + action);}

		return action;
	}

	/**
	 * @param initCoord
	 * @param finalCoord
	 * @param actionToDo
	 * @return
	 * Dans cette fonction, on va vraiment deplacer la piece
	 * on active donc le deplacement effectif et la capture eventuelle 
	 * on sera peut etre amene a rembobiner si le deplacement ou la capture ont 
	 * mis le roi en echec
	 */
	private ActionType moveAndCatch(ModelCoord initCoord, ModelCoord finalCoord, ActionType actionToDo) {
		ActionType actionEffective = null; 
		boolean thereWasACatch = false; 
		boolean thereWasAPromotion = false;
		final boolean TRACE = false;	// booléen à activer si l'on veut suivre la trace d'exécution

		//////////////////////
		// Capture eventuelle 
		//////////////////////

		if (ActionType.TAKE.equals(actionToDo)) {

			// TODO: le Roi n'a pas le droit de capturer lors du Roque
			// Son code le prend en principe en compte ; vérifier, tester

			actionEffective = this.chessImplementor.doCatch(finalCoord);
			if (ActionType.TAKE.equals(actionEffective)) {
				thereWasACatch = true; 
			}

			if(TRACE) {System.out.println("capture : " + actionEffective);}
		}

		//////////////////////////////////////////////////////////////
		// Déplacement dans tous les cas : actionToDo = TAKE ou MOVE
		//////////////////////////////////////////////////////////////

		actionEffective = this.chessImplementor.doMove(initCoord, finalCoord);
		if (ActionType.PROMOTION.equals(actionEffective)) {
			thereWasAPromotion = true; 
		}
		if(TRACE) {System.out.println("mouvement : " + actionEffective);}


		// Sémantique : Prise = Déplacement + Prise
		if (thereWasACatch && !(ActionType.ILLEGAL.equals(actionEffective))) {
			if (!thereWasAPromotion){
				actionEffective = ActionType.TAKE;
			}
			else {
				actionEffective = ActionType.TAKEPROMOTION;
				if(TRACE) {System.out.println("mouvement (Déplacement + Prise + Promotion) => " + actionEffective); }
			}
		}

		return actionEffective;
	}

	/**

	 * @return true si le roi du jeu testé est en echec
	 */
	private boolean isCheck() {		
		boolean ret = false;
		final boolean TRACE = false;	// booléen à activer si l'on veut suivre la trace d'exécution
		ModelCoord initCoord = null;

		ModelCoord kingCoord = (this.chessImplementor).getKingCoord(this.currentPlayer); 

		if(TRACE) {
			System.out.println("isCheck :" + " King color = " + this.currentPlayer );
		}
		for (int xInit = 0; xInit < this.nbColonne; xInit++){
			for (int yInit = 0; yInit < this.nbLigne; yInit++){

				// Si déplacement avec prise du roi possible
				initCoord = new ModelCoord((char)(xInit + 'a'), 8 - yInit);
				ActionType action = this.isMoveOK(initCoord, kingCoord);
				if (!ActionType.ILLEGAL.equals(action) ) {
					if(TRACE) {System.out.println("isCheck : "  + initCoord + " " + kingCoord);}
					ret = true;
				}
			}	
		}
		return ret;
	}

	/**
	 * @param initCoord
	 * @param finalCoord
	 * @param effectiveAction
	 * @return true si les actions MOVE et TAKE ont été rembobinées
	 * 
	 */
	private boolean undoMoveCatch(ModelCoord initCoord, ModelCoord finalCoord, ActionType effectiveAction) {
		boolean ret = true;
		final boolean TRACE = false;	// booléen à activer si l'on veut suivre la trace d'exécution

		//Rollback de la Capture éventuelle
		if (ActionType.TAKE.equals(effectiveAction)) {
			boolean rollbackCapture = this.chessImplementor.undoLastCapture();
			if(TRACE) {System.out.println("Rollback de la capture " 
					+ (rollbackCapture ? "" : "non ") 
					+ "effectuÃ©.");
			}
			if (!rollbackCapture) { // si ça n'a pas fonctionné...
				ret = false;
				throw new IllegalStateException("Chef, y'a un bug !"); // on arrête tout
			}
		}

		// Rollback du déplacement
		boolean rollbackMove = this.chessImplementor.undoLastMove();

		if(TRACE) {System.out.println("Rollback du dÃ©placement " 
				+ (rollbackMove ? "" : "non ") 
				+ "effectuÃ©.");
		}
		if (!rollbackMove) { // si ça n'a pas fonctionnÃ©...
			ret = false;
			throw new IllegalStateException("Chef, y'a un bug !"); // on arrÃªte tout
		}
		return ret;
	}



}
