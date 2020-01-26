package business;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import ui.MainMenuWindow;
import ui.Start;

public class CheckOutController {
	@SuppressWarnings("unused")
	private Book book;
	@SuppressWarnings("unused")
	private BookCopy bookcopy;
	@SuppressWarnings("unused")
	private BookCopy[] bookin;
	@SuppressWarnings("unused")
	private LibraryMember member;
	@SuppressWarnings("unused")
	private LocalDate returndate;

	@FXML
	private Button back;

	@FXML
	private Button checkBtn;
	@FXML
	private TextField memberIDText;
	@FXML
	private TextField isbnText;
	
	@FXML
	private TextField finesTextField;
	@FXML
	private TableColumn<CheckOutRecord, String> memberCol;
	@FXML
	private TableColumn<CheckOutRecord, String> isbnCol;
	@FXML
	private TableColumn<CheckOutRecord, String> checkoutDateCol;
	@FXML
	private TableColumn<CheckOutRecord, String> dueDateCol;
	@FXML
	private TableView<CheckOutRecord> BookStatusTable;
	@FXML
	private Text statusCheckout;
	@FXML
	TableView<CheckOutRecord> statusTable;

	@FXML
	private void checkoutBookAction(ActionEvent event) throws LibrarySystemException {
		try {
			ControllerInterface c = new SystemController();
			String id = memberIDText.getText();
			String isbn = isbnText.getText();
			List<CheckOutRecord> record = c.checkoutBook(id, isbn);
			final ObservableList<CheckOutRecord> data = FXCollections.observableArrayList(record);
			memberCol.setCellValueFactory(new PropertyValueFactory<CheckOutRecord, String>("memberID"));
			isbnCol.setCellValueFactory(new PropertyValueFactory<CheckOutRecord, String>("isbn"));
			checkoutDateCol.setCellValueFactory(new PropertyValueFactory<CheckOutRecord, String>("checkoutDate"));
			dueDateCol.setCellValueFactory(new PropertyValueFactory<CheckOutRecord, String>("dueDate"));
			BookStatusTable.setItems(data);
			statusCheckout.setText("");
		
			//int len = c.getBookByIsbn(isbn).getCopies().length;
//			BookCopy[] newArr = new BookCopy[len];
//			System.arraycopy(c.getBookByIsbn(isbn).getCopies(), 0, newArr, 0, len);
//			c.getBookByIsbn(isbn).setCopies(newArr);
			
			//c.getBookByIsbn(isbn).setCopy(""+len);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), e.getMessage(), JOptionPane.ERROR_MESSAGE);
			statusCheckout.setText(e.getMessage());
		}
	}

	@FXML
	private void printRecordAction(ActionEvent event) throws LibrarySystemException {
		try {
			ControllerInterface c = new SystemController();
			String id = memberIDText.getText();
			List<CheckOutRecord> record = c.getMemberRecord(id);
			final ObservableList<CheckOutRecord> data = FXCollections.observableArrayList(record);
			isbnCol.setCellValueFactory(new PropertyValueFactory<CheckOutRecord, String>("isbn"));
			checkoutDateCol.setCellValueFactory(new PropertyValueFactory<CheckOutRecord, String>("checkoutDate"));
			dueDateCol.setCellValueFactory(new PropertyValueFactory<CheckOutRecord, String>("dueDate"));

			statusTable.setItems(data);
			
			double fines=0.0;

			System.out.println("Here detail fine that need to paid by member: " + id);
			System.out.format("%s %8s %20s %10s %10s", "|ISBN", "|Date", "|Due Date","|Total Days","|Fine amount ($ USD)|");
			for (CheckOutRecord item : record) {
				long countDayofLate = item.getDueDate().until(LocalDate.now(), ChronoUnit.DAYS);
				if (countDayofLate < 0)
					countDayofLate = 0;
				System.out.println("\n");
				System.out.format("%s %16s %16s %10d %10d", item.getIsbn(), item.getCheckoutDate(), item.getDueDate(),countDayofLate, countDayofLate);
				item.setPaidDate(countDayofLate);
				fines += countDayofLate * 1;
				item.setFineAmount((long) fines);
				
			}
			finesTextField.setText("Total fines that need to paid by member "+ id +" : " + fines + " $USD");
		} catch (Exception e) {
			statusCheckout.setText(e.getMessage());
		}
		
		
	}

	@FXML
	protected void back(ActionEvent evt) {

		Start.hideAllWindows();
		if (SystemController.currentAuth == null)
			Start.primStage().show();
		else {
			if (!MainMenuWindow.INSTANCE.isInitialized()) {
				MainMenuWindow.INSTANCE.init();
			}
			MainMenuWindow.INSTANCE.show();
		}

	}

}
