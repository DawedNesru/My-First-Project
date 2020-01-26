package business;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ui.Validator;
import ui.ListAuthorsWindow;
import ui.MainMenuWindow;
import ui.Start;

public class BookController {

	@FXML
	private Text msg;
	@FXML
	private TextField isbn;

	@FXML
	private TextField title;

	@FXML
	private TextField maxCheckoutLength;

	@FXML
	private TextField authors;
	@FXML
	private TextField copyNum;

	@FXML
	private TableColumn<Book, String> isbnCol;
	@FXML
	private TableColumn<Book, String> titleCol;
	@FXML
	private TableColumn<Book, String> maxDayCol;

	@FXML
	private TableColumn<Book, String> numberCopiesCol;
	@FXML
	private TableColumn<Book, String> authorsCol;
	@FXML
	private TableView<Book> statusTable;

	@FXML
	private Button checkBtn;
	@FXML
	private TextField isbnText;
	@FXML
	private TextField bookISBNText;
	@FXML
	private TextField titleText;
	@FXML
	private TextField copyNumbersText;
	@FXML
	private TableColumn<CheckOutRecord, String> memberCol;
	@FXML
	private TableColumn<CheckOutRecord, String> dueDateCol;
	@FXML
	private TableColumn<CheckOutRecord, String> statusCol;
	@FXML
	private TableView<CheckOutRecord> BookStatusTable;
	@FXML
	private Text statusCheckout;

	public static List<Author> listofAuthors = new ArrayList<>();
	public static List<Author> pickedAuthors;
    
	@FXML
	protected void addBook(ActionEvent evt) {
		try {

			Validator.bookValidation(isbn.getText(), title.getText(), maxCheckoutLength.getText(), copyNum.getText());
			ControllerInterface c = new SystemController();
			if (c.getBookByIsbn(isbn.getText().trim()) != null) {
				JOptionPane.showMessageDialog(null, "ISBN should be unique!", "ISBN should be unique!", JOptionPane.ERROR_MESSAGE);
				throw new LibrarySystemException("ISBN should be unique!");
			}
			if (listofAuthors.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please choose authors!", "Please choose authors!", JOptionPane.ERROR_MESSAGE); 
				throw new LibrarySystemException("Please choose authors!");
			}
			Book book = new Book(isbn.getText(), title.getText(), Integer.parseInt(maxCheckoutLength.getText()),
					listofAuthors);
			for (int i = 0; i < Integer.parseInt(copyNum.getText())-1; i++) {
				book.addCopy();
			}
            
			c.addBook(book);
			resetElements();
			msg.setFill(Color.GREEN);
			msg.setText("New book created successfully!");
		} catch (LibrarySystemException ex) {
			msg.setFill(Color.RED);
			msg.setText(ex.getMessage());
		}
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

	@FXML
	protected void chooseAuthor(ActionEvent evt) {
		try {
			listofAuthors.clear();
			String authorsName = "";
			if (!ListAuthorsWindow.INSTANCE.isInitialized()) {
				ListAuthorsWindow.INSTANCE.init();
			}

			ListAuthorsWindow.INSTANCE.showAndWait();
			Author a;
			if (pickedAuthors == null)
				throw new LibrarySystemException("choose author!");
			for (int i = 0; i < pickedAuthors.size(); i++) {
				a = pickedAuthors.get(i);
				authorsName += a.getFirstName() + " " + a.getLastName();
				if (i < pickedAuthors.size() - 1) {
					authorsName += ", ";
				}
				listofAuthors.add(a);
			}
			authors.setText(authorsName);
		} catch (LibrarySystemException e) {
			msg.setText(e.getMessage());
		}
	}

	@FXML
	protected void resetBook(ActionEvent evt) {
		resetElements();
	}

	private void resetElements() {
		isbn.setText("");
		title.setText("");
		maxCheckoutLength.setText("");
		authors.setText("");
		copyNum.setText("");
	}

	@FXML
	private void showAllBooksAction(ActionEvent event) throws LibrarySystemException {
		ControllerInterface systemController = new SystemController();
		List<Book> record;
		try {
			record = systemController.getAllBooks();
		   final ObservableList<Book> data = FXCollections.observableArrayList(record);
			isbnCol.setCellValueFactory(new PropertyValueFactory<Book, String>("isbn"));
			titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
			maxDayCol.setCellValueFactory(new PropertyValueFactory<Book, String>("maxCheckoutLength"));
			authorsCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
			numberCopiesCol.setCellValueFactory(new PropertyValueFactory<Book, String>("copy"));
			statusTable.setItems(data);
			
		} catch (LibrarySystemException e1) {
			e1.printStackTrace();
		}
	}
	
	
	@FXML
	private void showBookStatusAction(ActionEvent event) throws LibrarySystemException {
		try {
			ControllerInterface c = new SystemController();
			String isbn = isbnText.getText();
			List<CheckOutRecord> record = c.getBookStatus(isbn);
			DataAccess da = new DataAccessFacade();
			Book book = da.readBooksMap().get(isbn);
			bookISBNText.setText(isbn);
			titleText.setText(book.getTitle());
			copyNumbersText.setText(Integer.toString(book.getCopyNums().size()));
			final ObservableList<CheckOutRecord> data = FXCollections.observableArrayList(record);
			memberCol.setCellValueFactory(new PropertyValueFactory<CheckOutRecord, String>("memberID"));
			dueDateCol.setCellValueFactory(new PropertyValueFactory<CheckOutRecord, String>("dueDate"));
			statusCol.setCellValueFactory(new PropertyValueFactory<CheckOutRecord, String>("status"));
			BookStatusTable.setItems(data);
			statusCheckout.setText("");
			if(data.isEmpty())
				statusCheckout.setText("failed to checkout!");
			
		} catch (Exception e) {
			statusCheckout.setText(e.getMessage());
		}
	}

}
