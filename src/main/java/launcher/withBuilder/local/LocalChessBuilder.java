package launcher.withBuilder.local;

import controller.localControler.ControllerLocal;
import launcher.withBuilder.AbstractChessBuilder;
import launcher.withBuilder.ChessBuilder;
import launcher.withBuilder.ViewData;
import model.Model;
import view.View;

public class LocalChessBuilder extends AbstractChessBuilder implements ChessBuilder {

	public LocalChessBuilder() {
		super();
	}

	@Override
	public void buildModel() {

		this.chessModel = new Model();
	}

	@Override
	public void buildControler() {

		this.chessController = new ControllerLocal();
	}

	@Override
	public void buildView() {

		if (this.chessController != null) {
			this.chessGUI = new View(this.chessController);
		}
	}

	@Override
	public ViewData getViewData() {
		
		return new ViewData(this.chessGUI,
				"Chessgame en mode local - Avec Builder - Version élèves",
				500);
	}
}