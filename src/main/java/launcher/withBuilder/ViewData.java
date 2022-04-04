package launcher.withBuilder;

import javafx.scene.Parent;

public class ViewData {
	public Parent view;
	public String title;
	public int xPos;
	
	public ViewData(Parent view, String title, int xPos) {
		super();
		this.view = view;
		this.title = title;
		this.xPos = xPos;
	}
}
