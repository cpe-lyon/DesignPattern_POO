package launcher.withBuilder;

/**
 * @author francoiseperrin
 *
 * Cette interface défini le comportement attendu des 
 * Buider de jeu d'échec
 * utilisables en mode local et en mode client/server
 * 
 * certaines méthodes pouvant dans certains cas ne rien faire
 * (pas de model pour les clients, pas de vue pour le server)
 * .
 */
public interface ChessBuilder {
	
	public void buildModel() ;
	
//	public void buildCommunicationChannel();
	
	public void buildControler() ;
	
	public void buildView() ;

	public void setMediator() ;
	
	public ViewData getViewData();
	
}
