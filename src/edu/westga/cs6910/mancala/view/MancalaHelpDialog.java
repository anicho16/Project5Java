/**
 * 
 */

package edu.westga.cs6910.mancala.view;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * Creates Help Dialog which is shown or not shown based on user's selection
 * 
 * @author Amber Nicholas
 * @version 7.2.22
 *
 */
public class MancalaHelpDialog {
	private boolean shouldShowHelpDialog;

	/**
	 * Constructor for Help Dialog
	 * 
	 */
	public MancalaHelpDialog() {
		this.shouldShowHelpDialog = true;
		this.shouldShowHelpDialog = this.showHelpDialog();
	}

	public void setShouldShowHelpDialog(boolean shouldShow) {
		this.shouldShowHelpDialog = shouldShow;
	}

	/**
	 * Alerts the user of rules. Allows the user to decide if these rules should be
	 * displayed at the beginning of the game.
	 * 
	 * @return - answer type of user
	 */
	public boolean showHelpDialog() {
		if (!this.shouldShowHelpDialog) {
			return false;
		}

		Alert message = new Alert(AlertType.CONFIRMATION);
		message.setTitle("CS6910 - Better Mancala");

		String helpMessage = "Mancala rules:\nPlay against the computer.\n"
				+ "Alternate taking turns, selecting a pit with stones.\n"
				+ "The stones are taken from this pit and placed, one at a\n"
				+ "   time into consecutive pits in counter-clockwise fashion.\n"
				+ "The game ends when one player no longer has any stones\n" + "   to distribute.\n"
				+ "The goal is to get more stones into your store,\n" + "than your opponent has in their store.";

		message.setHeaderText(helpMessage);
		message.setContentText("Would you like to see this dialog at the start of the next game?");

		ButtonType btnYes = new ButtonType("Yes");
		ButtonType btnNo = new ButtonType("No");
		message.getButtonTypes().setAll(btnYes, btnNo);

		Optional<ButtonType> result = message.showAndWait();

		if (result.get() == btnYes) {
			this.shouldShowHelpDialog = true;
		} else {
			this.shouldShowHelpDialog = false;
		}

		return result.get() == btnYes;
	}

}
