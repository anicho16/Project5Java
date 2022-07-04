/**
 * 
 */

package edu.westga.cs6910.mancala.view;

import edu.westga.cs6910.mancala.model.strategies.FarStrategy;
import edu.westga.cs6910.mancala.model.strategies.NearStrategy;
import edu.westga.cs6910.mancala.model.strategies.RandomStrategy;
import edu.westga.cs6910.mancala.model.strategies.SelectStrategy;
import edu.westga.cs6910.mancala.model.Game;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
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
	
	public MancalaMenuBar(Game theGame) {
		if (theGame == null) {
			throw new IllegalArgumentException("Invalid Game object");
		}
		
		this.theGame = theGame;
		this.helpDialog = new MancalaHelpDialog();
		this.mainPane = mainPane;
	}
	
	public VBox createMenu() {
		VBox vbxMenuHolder = new VBox();
		
		MenuBar mnuMain = new MenuBar();
		
		Menu mnuFile = this.createFileMenu();
		
		Menu mnuSettings = this.createStrategyMenu();
				
		mnuMain.getMenus().addAll(mnuFile, mnuSettings);
		vbxMenuHolder.getChildren().addAll(mnuMain);
		
		return vbxMenuHolder;
	}

	private Menu createStrategyMenu() {
		Menu mnuSettings = new Menu("_Computer Player");
		mnuSettings.setMnemonicParsing(true);
		
		ToggleGroup tglStrategy = new ToggleGroup();
		
		RadioMenuItem mnuNear = new RadioMenuItem("N_ear");
		mnuNear.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.SHORTCUT_DOWN));
		mnuNear.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				MancalaMenuBar.this.theGame.getComputerPlayer().setStrategy(new NearStrategy());
			}
		});
		
		mnuNear.setMnemonicParsing(true);
		mnuNear.setToggleGroup(tglStrategy);
		
		RadioMenuItem mnuFar = new RadioMenuItem("F_ar");
		mnuFar.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.SHORTCUT_DOWN));
		mnuFar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				MancalaMenuBar.this.theGame.getComputerPlayer().setStrategy(new FarStrategy());
			}
		});
		mnuFar.setMnemonicParsing(true);
		mnuFar.setToggleGroup(tglStrategy);
		
		RadioMenuItem mnuRandom = new RadioMenuItem("_Random");
		mnuRandom.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.SHORTCUT_DOWN));
		mnuRandom.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				MancalaMenuBar.this.theGame.getComputerPlayer().setStrategy(new RandomStrategy());
			}
		});
		mnuRandom.setMnemonicParsing(true);
		mnuRandom.setToggleGroup(tglStrategy);
		
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

	private Menu createFileMenu() {
		Menu mnuFile = new Menu("_Game");
		mnuFile.setMnemonicParsing(true);
	
		MenuItem mnuNew = new MenuItem("_New");
		mnuNew.setMnemonicParsing(true);
		mnuNew.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN));
		mnuNew.setOnAction(new EventHandler<ActionEvent>() {
			@Override
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
		});
		
		MenuItem mnuExit = new MenuItem("E_xit");
		mnuExit.setMnemonicParsing(true);
		mnuExit.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.SHORTCUT_DOWN));
		mnuExit.setOnAction(event -> System.exit(0));
		
		mnuFile.getItems().addAll(mnuNew, mnuExit);
		return mnuFile;
	}

}
