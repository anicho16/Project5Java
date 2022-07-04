package edu.westga.cs6910.mancala.view;

import edu.westga.cs6910.mancala.model.Game;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Defines a GUI for the Mancala game. This class was started by CS6910
 * 
 * @author Amber Nicholsa
 * @version 7.2.22
 */
public class MancalaPane extends BorderPane {
	private Game theGame;
	private GridPane pnContent;
	private HumanPane pnHumanPlayer;
	private ComputerPane pnComputerPlayer;
	private StatusPane pnStatus;
	private NewGamePane pnChooseFirstPlayer;
	private MancalaMenuBar menu;

	/**
	 * Creates a pane object to provide the view for the specified Game model
	 * object.
	 * 
	 * @param theGame the domain model object representing the Mancala game
	 * 
	 * @requires theGame != null
	 * @ensures the pane is displayed properly
	 */
	public MancalaPane(Game theGame) {
		if (theGame == null) {
			throw new IllegalArgumentException("Invalid game");
		}
		this.theGame = theGame;

		this.pnContent = new GridPane();

		this.menu = new MancalaMenuBar(this.theGame, this);
		this.setTop(this.menu.createMenu());

		this.addFirstPlayerChooserPane(theGame);

		this.addComputerPlayerPane(theGame);

		this.addHumanPlayerPane(theGame);

		this.addGameStatusPane(theGame);

		this.setCenter(this.pnContent);
	}

	/**
	 * Accessor for pnChooseFirstPlayer pane
	 * 
	 * @return - pane which describes choosing first player
	 */
	public NewGamePane getPnChooseFirstPlayer() {
		return this.pnChooseFirstPlayer;
	}

	/**
	 * Accessor for pnComputerPlayer pane
	 * 
	 * @return - pane which describes Computer side of the board
	 */
	public ComputerPane getPnComputerPlayer() {
		return this.pnComputerPlayer;
	}

	/**
	 * Accessor for pnHumanPlayer pane
	 * 
	 * @return - pane which describes Human side of the board
	 */
	public HumanPane getPnHumanPlayer() {
		return this.pnHumanPlayer;
	}

	private void addGameStatusPane(Game theGame) {
		this.pnStatus = new StatusPane(theGame);
		HBox centerBox = new HBox();
		centerBox.getStyleClass().add("pane-border");
		centerBox.getChildren().add(this.pnStatus);
		this.pnContent.add(centerBox, 0, 3);
	}

	private void addHumanPlayerPane(Game theGame) {
		this.pnHumanPlayer = new HumanPane(theGame);
		this.pnHumanPlayer.setDisable(true);
		HBox leftBox = new HBox();
		leftBox.getStyleClass().add("pane-border");
		leftBox.getChildren().add(this.pnHumanPlayer);
		this.pnContent.add(leftBox, 0, 2);
	}

	private void addComputerPlayerPane(Game theGame) {
		this.pnComputerPlayer = new ComputerPane(theGame);
		this.pnComputerPlayer.setDisable(true);
		HBox rightBox = new HBox();
		rightBox.getStyleClass().add("pane-border");
		rightBox.getChildren().add(this.pnComputerPlayer);
		this.pnContent.add(rightBox, 0, 1);
	}

	private void addFirstPlayerChooserPane(Game theGame) {
		HBox topBox = new HBox();
		topBox.getStyleClass().add("pane-border");
		this.pnChooseFirstPlayer = new NewGamePane(theGame, this);
		topBox.getChildren().add(this.pnChooseFirstPlayer);
		this.pnContent.add(topBox, 0, 0);
	}

}
