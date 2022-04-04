package launcher.withBuilder;


import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.GuiFactory;

/**
 * @author francoise.perrin
 * Lance l'execution d'un jeu d'echec en utilisant un Builder
 * pour créer les différents éléments (model, controller, view, etc.).
 * 
 * les éléments de configuration (taille, etc.) sont
 * stockés dans des fabriques
 * 
 */
public abstract class AbstractLauncher extends Application {

	ChessBuilder chessBuilder;

	@Override
	public void init () throws Exception {
		super.init();

		this.chessBuilder = this.createChessBuilder();
		this.chessBuilder.buildModel();	
		this.chessBuilder.buildControler();
		this.chessBuilder.buildView();	
		this.chessBuilder.setMediator();

	}

	protected ViewData getViewData() {
		return this.chessBuilder.getViewData();
	}

	/**
	 * @return le Builder nécessaire qui est différent 
	 * selon le mode de jeu (local, client ou server)
	 */
	protected abstract ChessBuilder createChessBuilder();


	/* La méthode main() sera définie dans les classes dérivées */

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		ViewData viewData = this.getViewData();
		
		if (viewData != null) {
			
			IntegerProperty height = new SimpleIntegerProperty();
			IntegerProperty width = new SimpleIntegerProperty();
			height.bind(GuiFactory.height);
			width.bind(GuiFactory.width);
			
			primaryStage.setScene(new Scene(viewData.view, width.get(), height.get()));
			primaryStage.setTitle(viewData.title);
			primaryStage.setX(viewData.xPos);
			primaryStage.show();
		}
	}
}

