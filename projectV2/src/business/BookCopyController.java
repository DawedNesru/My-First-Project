package business;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ui.Validator;
import ui.MainMenuWindow;
import ui.Start;

public class BookCopyController {

	@FXML
	private Text msg;
	@FXML
	private TextField isbn;

	@FXML
	private TextField copyNum;

	@FXML
	protected void addBookCopy(ActionEvent evt) {
		try {

			Validator.bookCopyValidation(isbn.getText(), copyNum.getText());
			ControllerInterface c = new SystemController();
			Book book = c.getBookByIsbn(isbn.getText());
			if (book == null) {
				JOptionPane.showMessageDialog(null, "ISBN not found!", "ISBN not found!", JOptionPane.ERROR_MESSAGE);
				throw new LibrarySystemException("ISBN not found!");
			}
			c.addCopyofBook(book, Integer.parseInt(copyNum.getText().trim()));
			msg.setFill(Color.GREEN);
			msg.setText(copyNum.getText() + " copies added successfully!");
			clear();
		} catch (LibrarySystemException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), ex.getMessage(), JOptionPane.ERROR_MESSAGE);
			msg.setFill(Color.RED);
			msg.setText(ex.getMessage());
		}
	}

	@FXML
	protected void resetBook(ActionEvent evt) {
		clear();
	}

	private void clear() {
		isbn.setText("");
		copyNum.setText("");
	}
	

	@FXML
	protected void back(ActionEvent evt) {
		
		Start.hideAllWindows();
		if(SystemController.currentAuth==null)
			Start.primStage().show();
		else {
			if(!MainMenuWindow.INSTANCE.isInitialized()) {
				MainMenuWindow.INSTANCE.init();
			}
			MainMenuWindow.INSTANCE.show();
		}
		
	}

}
