/**
 * 
 */

package edu.westga.cs6910.mancala.view;

import edu.westga.cs6910.mancala.model.Game;
import edu.westga.cs6910.mancala.model.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

/**
 * Defines the panel in which the user selects which Player plays first.
 * 
 * @author Amber Nicholas
 * @version 7.4.22
 *
 */
public class NewGamePane extends GridPane {
	private RadioButton radHumanPlayer;
	private RadioButton radComputerPlayer;
	private RadioButton radRandomPlayer;
	private ComboBox<Integer> cmbGoalScore;

	private Game theGame;
	private Player theHuman;
	private Player theComputer;
	private MancalaPane mainPane;

	/**
	 * Constructor for NewGamePane
	 * 
	 * @param theGame  - represents current Game
	 * @param mainPane - represents MancalaPane
	 */
	public NewGamePane(Game theGame, MancalaPane mainPane) {
		this.theGame = theGame;

		this.theHuman = this.theGame.getHumanPlayer();
		this.theComputer = this.theGame.getComputerPlayer();
		this.mainPane = mainPane;

		this.buildPane();
	}

	private void buildPane() {
		this.setHgap(20);

		this.createNumberOfStonesChoice();

		this.createFirstPlayerItems();
	}

	private void createFirstPlayerItems() {
		this.radHumanPlayer = new RadioButton(this.theHuman.getName() + " first");
		this.radHumanPlayer.setOnAction(new HumanFirstListener());

		this.radComputerPlayer = new RadioButton(this.theComputer.getName() + " first");
		this.radComputerPlayer.setOnAction(new ComputerFirstListener());

		this.radRandomPlayer = new RadioButton("Random player first");
		this.radRandomPlayer.setOnAction(new RandomFirstListener());

		ToggleGroup group = new ToggleGroup();
		this.radHumanPlayer.setToggleGroup(group);
		this.radComputerPlayer.setToggleGroup(group);
		this.radRandomPlayer.setToggleGroup(group);

		this.add(this.radHumanPlayer, 2, 0);
		this.add(this.radComputerPlayer, 3, 0);
		this.add(this.radRandomPlayer, 4, 0);
	}

	private void createNumberOfStonesChoice() {
		Label lblGoalScore = new Label("Starting Stones: ");
		this.add(lblGoalScore, 0, 0);

		this.cmbGoalScore = new ComboBox<Integer>();
		this.cmbGoalScore.getItems().addAll(1, 2, 3);
		this.cmbGoalScore.setValue(1);
		this.cmbGoalScore.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int startingStones = NewGamePane.this.cmbGoalScore.getValue();
				NewGamePane.this.theGame.setStartingStones(startingStones);
			}
		});
		this.add(this.cmbGoalScore, 1, 0);
	}

	/**
	 * Returns true if human player is first
	 * 
	 * @return - whether or not human player is first
	 */
	public boolean isHumanFirst() {
		return this.radHumanPlayer.isSelected();
	}

	/**
	 * Returns true if computer player is first
	 * 
	 * @return - whether or not computer player is first
	 */
	public boolean isComputerFirst() {
		return this.radComputerPlayer.isSelected();
	}

	/**
	 * Returns true if random player is first
	 * 
	 * @return - whether or not random player is first
	 */
	public boolean isRandomFirst() {
		return this.radRandomPlayer.isSelected();
	}

	/**
	 * Chooses a random player to be first at the beginning of a game
	 */
	public void chooseRandomPlayer() {
		if (Math.random() * 10 <= 4) {
			NewGamePane.this.theGame.startNewGame(NewGamePane.this.theGame.getHumanPlayer());
		} else {
			NewGamePane.this.theGame.startNewGame(NewGamePane.this.theGame.getComputerPlayer());
		}
	}

	/**
	 * Defines the listener for computer player first button.
	 */
	private class RandomFirstListener implements EventHandler<ActionEvent> {
		@Override
		/**
		 * Enables the ComputerPlayerPanel and starts a new game. Event handler for a
		 * click in the computerPlayerButton.
		 */
		public void handle(ActionEvent arg0) {
			NewGamePane.this.setDisable(true);

			NewGamePane.this.chooseRandomPlayer();
		}
	}

	/**
	 * Defines the listener for computer player first button.
	 */
	private class ComputerFirstListener implements EventHandler<ActionEvent> {
		@Override
		/**
		 * Enables the ComputerPlayerPanel and starts a new game. Event handler for a
		 * click in the computerPlayerButton.
		 */
		public void handle(ActionEvent arg0) {
			NewGamePane.this.mainPane.getPnComputerPlayer().setDisable(false);
			NewGamePane.this.setDisable(true);
			NewGamePane.this.theGame.startNewGame(NewGamePane.this.theComputer);
		}
	}

	/**
	 * Defines the listener for human player first button.
	 */
	private class HumanFirstListener implements EventHandler<ActionEvent> {
		@Override
		/**
		 * Sets up user interface and starts a new game. Event handler for a click in
		 * the human player button.
		 */
		public void handle(ActionEvent event) {
			NewGamePane.this.setDisable(true);
			NewGamePane.this.mainPane.getPnHumanPlayer().setDisable(false);
			NewGamePane.this.theGame.startNewGame(NewGamePane.this.theHuman);
		}
	}
}
