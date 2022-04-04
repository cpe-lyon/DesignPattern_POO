package view;


import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import view.GuiFactory;
import view.PaintStyle;


/**
 * @author francoise.perrin
 * Cette classe est le menu du jeu d'échec
 */
public class MenuView extends MenuBar {

	public MenuView () {
		super();

		this.getMenus().add(newMenuStyle());
		this.getMenus().add(newMenuColor());
		this.getMenus().add(newMenuEdit());
	}

	private Menu newMenuColor () {

		Menu menu = new Menu("Couleur d'affichage");
		MenuItem color1 = new MenuItem("Couleur cases blanches");
		MenuItem color2 = new MenuItem("Couleur cases noires");

		color1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle (ActionEvent event) {
				Dialog<Void> colorDialog = new Dialog<>();
				colorDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

				ColorPicker colorPicker = new ColorPicker(GuiFactory.whiteSquareColor.get());
				colorPicker.setOnAction(colorEvent -> {
										/* sans pattern Command */
										GuiFactory.whiteSquareColor.set(colorPicker.getValue());

					
					colorDialog.close();
				});

				colorDialog.getDialogPane().setContent(colorPicker);
				colorDialog.show();
			}
		});

		color2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle (ActionEvent event) {
				Dialog<Void> colorDialog = new Dialog<>();
				colorDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

				ColorPicker colorPicker = new ColorPicker(GuiFactory.blackSquareColor.get());
				colorPicker.setOnAction(colorEvent -> {
										/* sans pattern Command */
										GuiFactory.blackSquareColor.set(colorPicker.getValue());

					
					colorDialog.close();
				});

				colorDialog.getDialogPane().setContent(colorPicker);
				colorDialog.show();
			}
		});

		menu.getItems().addAll(color1, color2);
		return menu;
	}

	private Menu newMenuStyle () {

		Menu menu = new Menu("Style d'affichage");
		RadioMenuItem style1 = new RadioMenuItem("Dégradé");
		RadioMenuItem style2 = new RadioMenuItem("Uni");
		ToggleGroup btnGroup = new ToggleGroup();

		style1.setToggleGroup(btnGroup);
		style2.setToggleGroup(btnGroup);

		Map<MenuItem, PaintStyle> btnMap = new HashMap<>();
		btnMap.put(style1, PaintStyle.GRADIENT);
		btnMap.put(style2, PaintStyle.SOLID);

		Object style = GuiFactory.paintStyle.get();
		btnMap.forEach((menuItem, paintstyle) -> {
			if (paintstyle.equals(style)) {
				((RadioMenuItem) menuItem).setSelected(true);
			}
		});


		style1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle (ActionEvent event) {
								 /* sans pattern Command */
								GuiFactory.paintStyle.set(PaintStyle.GRADIENT);			

				
			}
		});

		style2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle (ActionEvent event) {
								 /* sans pattern Command */
								GuiFactory.paintStyle.set(PaintStyle.SOLID);			

				
			}
		});

		menu.getItems().addAll(style1, style2);
		return menu;
	}

	private Menu newMenuEdit () {

		Menu menu = new Menu("Gestion Commandes");
		MenuItem undo = new MenuItem("undo");
		MenuItem redo = new MenuItem("redo");

		undo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle (ActionEvent event) {
				// TODO
			}
		});

		redo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle (ActionEvent event) {
				// TODO
			}
		});

		menu.getItems().addAll(undo, redo);
		return menu;
	}


}
