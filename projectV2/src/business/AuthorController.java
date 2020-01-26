package business;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import ui.ListAuthorsWindow;

public class AuthorController implements Initializable { 
	@FXML
	private Button checkBtn;
	@FXML
	private TextField memberIDText;
	@FXML
	private TextField isbnText;
	@FXML
	private TableColumn<Author, String> telephone;
	@FXML
	private TableColumn<Author, String> firstnameCol;
	@FXML
	private TableColumn<Author, String> lastnameCol;
	@FXML
	private TableColumn<Author, String> bioCol;
	@FXML
	private TableView<Author> authorsTable;

	@FXML
	public void passAuthors(ActionEvent event) {
		ListAuthorsWindow.INSTANCE.hide();
	}
	

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initTable();
	}

	@FXML
	public void initTable() {
		ControllerInterface c = new SystemController();
		List<Author> authors = c.getAllAuthors();
		final ObservableList<Author> data = FXCollections.observableArrayList(authors);
		telephone.setCellValueFactory(new PropertyValueFactory<Author, String>("telephone"));
		firstnameCol.setCellValueFactory(new PropertyValueFactory<Author, String>("firstName"));
		lastnameCol.setCellValueFactory(new PropertyValueFactory<Author, String>("lastName"));
		bioCol.setCellValueFactory(new PropertyValueFactory<Author, String>("bio"));
		authorsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		authorsTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				BookController.pickedAuthors = authorsTable.getSelectionModel().getSelectedItems();
			}
		});
		authorsTable.setItems(data);
	}

	@FXML
	protected void reload(ActionEvent evt) {
		initTable();
	}
}