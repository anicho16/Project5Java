/**
 * 
 */

package edu.westga.cs6910.mancala.view;

import edu.westga.cs6910.mancala.model.strategies.FarStrategy;
import edu.westga.cs6910.mancala.model.strategies.NearStrategy;
import edu.westga.cs6910.mancala.model.strategies.RandomStrategy;
import edu.westga.cs6910.mancala.model.strategies.SelectStrategy;
import edu.westga.cs6910.mancala.model.Game;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Creates Menu for Game
 * 
 * @author Amber Nicholas
 * @version 7.2.22
 *
 */
public class MancalaMenuBar {

	private Game theGame;
	private MancalaHelpDialog helpDialog;
	private MancalaPane mainPane;

	/**
	 * Constructor for the Mancanla Menu Bar
	 * 
	 * @param theGame  - represents current Game
	 * @param mainPane - represents MancalaPane which describes all board panes
	 */
	public MancalaMenuBar(Game theGame, MancalaPane mainPane) {
		if (theGame == null) {
			throw new IllegalArgumentException("Invalid Game object");
		}

		this.theGame = theGame;
		this.helpDialog = new MancalaHelpDialog();
		this.mainPane = mainPane;
	}

	/**
	 * Creates Menu Bar of all menu items
	 * 
	 * @return - Menu Bar
	 */
	public VBox createMenu() {
		VBox vbxMenuHolder = new VBox();

		MenuBar mnuMain = new MenuBar();

		Menu mnuFile = this.createFileMenu();

		Menu mnuSettings = this.createStrategyMenu();

		Menu mnuHelp = this.createHelpMenu();

		mnuMain.getMenus().addAll(mnuFile, mnuSettings, mnuHelp);
		vbxMenuHolder.getChildren().addAll(mnuMain);

		return vbxMenuHolder;
	}

	private Menu createStrategyMenu() {
		Menu mnuSettings = new Menu("_Computer Player");
		mnuSettings.setMnemonicParsing(true);

		RadioMenuItem mnuNear = this.addStrategyItem("N_ear", KeyCode.E, new NearStrategy());

		RadioMenuItem mnuFar = this.addStrategyItem("F_ar", KeyCode.A, new FarStrategy());

		RadioMenuItem mnuRandom = this.addStrategyItem("_Random", KeyCode.R, new RandomStrategy());

		SelectStrategy currentStrategy = this.theGame.getComputerPlayer().getStrategy();
		if (currentStrategy.getClass() == NearStrategy.class) {
			mnuNear.setSelected(true);
		} else if (currentStrategy.getClass() == RandomStrategy.class) {
			mnuRandom.setSelected(true);
		} else {
			mnuFar.setSelected(true);
		}

		mnuSettings.getItems().addAll(mnuNear, mnuFar, mnuRandom);
		return mnuSettings;
	}

	private RadioMenuItem addStrategyItem(String shortCutText, KeyCode key, SelectStrategy strategyType) {
		ToggleGroup tglStrategy = new ToggleGroup();

		RadioMenuItem mnu = new RadioMenuItem(shortCutText);
		mnu.setAccelerator(new KeyCodeCombination(key, KeyCombination.SHORTCUT_DOWN));
		mnu.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				MancalaMenuBar.this.theGame.getComputerPlayer().setStrategy(strategyType);
			}
		});

		mnu.setMnemonicParsing(true);
		mnu.setToggleGroup(tglStrategy);

		return mnu;
	}

	private Menu createFileMenu() {
		Menu mnuFile = new Menu("_Game");
		mnuFile.setMnemonicParsing(true);

		MenuItem mnuNew = new MenuItem("_New");
		mnuNew.setMnemonicParsing(true);
		mnuNew.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN));
		mnuNew.setOnAction(new MnuNewListener());

		MenuItem mnuExit = new MenuItem("E_xit");
		mnuExit.setMnemonicParsing(true);
		mnuExit.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.SHORTCUT_DOWN));
		mnuExit.setOnAction(event -> System.exit(0));

		mnuFile.getItems().addAll(mnuNew, mnuExit);
		return mnuFile;
	}

	private Menu createHelpMenu() {
		Menu mnuHelp = new Menu("_Help");
		mnuHelp.setMnemonicParsing(true);

		MenuItem mnuContents = new MenuItem("_Contents");
		mnuContents.setMnemonicParsing(true);
		mnuContents.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.SHORTCUT_DOWN));
		mnuContents.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MancalaMenuBar.this.helpDialog.setShouldShowHelpDialog(true);
				MancalaMenuBar.this.helpDialog.showHelpDialog();
			}
		});

		MenuItem mnuAbout = new MenuItem("_About");
		mnuAbout.setMnemonicParsing(true);
		mnuAbout.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.SHORTCUT_DOWN));
		mnuAbout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Alert message = new Alert(AlertType.INFORMATION);
				message.setContentText("Game created: July 4, 2022. \nEditor: Amber Nicholas");
				message.show();
			}
		});
		mnuHelp.getItems().addAll(mnuContents, mnuAbout);
		return mnuHelp;
	}

	/**
	 * Defines the listener for New Menu Item.
	 */
	private class MnuNewListener implements EventHandler<ActionEvent> {
		@Override
		/**
		 * Sets up user interface and starts a new game. Event handler for a click in
		 * the new menu item.
		 */
		public void handle(ActionEvent event) {
			MancalaMenuBar.this.helpDialog.showHelpDialog();
			if (MancalaMenuBar.this.mainPane.getPnChooseFirstPlayer().isHumanFirst()) {
				MancalaMenuBar.this.theGame.startNewGame(MancalaMenuBar.this.theGame.getHumanPlayer());
			} else if (MancalaMenuBar.this.mainPane.getPnChooseFirstPlayer().isComputerFirst()) {
				MancalaMenuBar.this.theGame.startNewGame(MancalaMenuBar.this.theGame.getComputerPlayer());
			} else if (MancalaMenuBar.this.mainPane.getPnChooseFirstPlayer().isRandomFirst()) {
				MancalaMenuBar.this.mainPane.getPnChooseFirstPlayer().chooseRandomPlayer();
			}
		}
	}

}
