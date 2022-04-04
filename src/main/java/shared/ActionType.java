package shared;


public enum  ActionType { 
	// ------------------------------------------------------- coups anormaux
	/** coup non encore Ã©valuÃ© (valeur par dÃ©faut) */
	UNKNOWN,
	/** coup Ã©valuÃ© comme illÃ©gal */
	ILLEGAL,
	// ------------------------------------------------------- coups normaux
	/** coup normal de dÃ©placement (sans prise) */
	MOVE,
	/** coup normal de prise */
	TAKE,
	// ------------------------------------------------------- coup spÃ©ciaux
	/** mouvement de roque (tentative) */
	ROQUE, 
	/** promotion du pion */
	PROMOTION, 
	/** promotion du pion + prise */
	TAKEPROMOTION, 
	/** prise en passant du pion */
	PRISE_EN_PASSANT,
	// ------------------------------------------------------- coups Ã  refuser
	/** mouvement lÃ©gal Ã  refuser car aboutirait en un Ã©chec du roi */
	ROI_EN_ECHEC,
	/** fin de partie - echec et mat ou pat */
	END
	; // ----------------------------------------------------- 
}