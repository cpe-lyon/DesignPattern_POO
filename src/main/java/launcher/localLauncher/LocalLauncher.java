package launcher.localLauncher;


import controller.ChessController;
import controller.ChessControllerModel;
import controller.ChessControllerView;
import controller.localControler.ControllerLocal;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ChessModel;
import model.Model;
import view.ChessView;
import view.GuiFactory;
import view.View;

/**
 * @author francoise.perrin
 * Lance l'exécution d'un jeu d'échec en mode local.
 *
 * les éléments de configuration (taille, etc.) sont
 * stockés dans des fabriques
 * 
 */
public class LocalLauncher extends Application {

	private ChessModel chessModel = null;
	private ChessController chessController = null;
	private Parent chessGameGUI = null;
	
	private  IntegerProperty height = new SimpleIntegerProperty();
	private  IntegerProperty width = new SimpleIntegerProperty();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LocalLauncher.launch();
	}

	@Override
	public void init () throws Exception {
		super.init();
		height.bind(GuiFactory.height);
		width.bind(GuiFactory.width);

		this.chessModel = new Model();
		this.chessController = new ControllerLocal();
		this.chessGameGUI = new View(this.chessController);

		((ChessControllerView) this.chessController).setView((ChessView) this.chessGameGUI);
		((ChessControllerModel) this.chessController).setModel(this.chessModel);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new Scene(this.chessGameGUI, width.get(), height.get()));
		primaryStage.setTitle("Chessgame local - Version Elèves");
		primaryStage.show();
	}
}



