package ui;

import business.Address;
import business.LibraryMember;
import business.SystemController;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EditMemberWindow extends Stage implements LibWindow {
	public static final EditMemberWindow INSTANCE = new EditMemberWindow();

	private LibraryMember editMemberData;

	private boolean isInitialized = false;

	public boolean isInitialized() {
		return isInitialized;
	}

	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	private Text messageBar = new Text();

	public void clear() {
		messageBar.setText("");
	}

	public void setData(LibraryMember data) {
		editMemberData = data;
	}

	private Label newIdLabel;
	private TextField newIdField;

	private Label newFnameLabel;
	private TextField newFnameField;

	private Label newLnameLabel;
	private TextField newLnameField;

	private Label newStreetLabel;
	private TextField newStreetField;

	private Label newCityLabel;
	private TextField newCityField;

	private Label newStateLabel;
	private TextField newStateField;

	private Label newZipLabel;
	private TextField newZipField;

	private Label newPhoneLabel;
	private TextField newPhoneField;

	/* This class is a singleton */
	private EditMemberWindow() {
	}

	public void init() {
		GridPane grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("Edit Existing Member");
		scenetitle.setFont(Font.font("Harlow Solid Italic", FontWeight.NORMAL, 20)); // Tahoma
		grid.add(scenetitle, 0, 0, 2, 1);

		newIdLabel = new Label("Member Id : ");
		grid.add(newIdLabel, 0, 1);
		newIdField = new TextField();
		newIdField.setText(editMemberData.getMemberId());
		grid.add(newIdField, 1, 1);

		newFnameLabel = new Label("First Name : ");
		grid.add(newFnameLabel, 2, 1);
		newFnameField = new TextField();
		newFnameField.setText(editMemberData.getFirstName());
		grid.add(newFnameField, 3, 1);

		newLnameLabel = new Label("Last Name : ");
		grid.add(newLnameLabel, 0, 2);
		newLnameField = new TextField();
		newLnameField.setText(editMemberData.getLastName());
		grid.add(newLnameField, 1, 2);

		newStreetLabel = new Label("Street : ");
		grid.add(newStreetLabel, 2, 2);
		newStreetField = new TextField();
		newStreetField.setText(editMemberData.getAddress().getStreet());
		grid.add(newStreetField, 3, 2);

		newCityLabel = new Label("City : ");
		grid.add(newCityLabel, 0, 3);
		newCityField = new TextField();
		newCityField.setText(editMemberData.getAddress().getCity());
		grid.add(newCityField, 1, 3);

		newStateLabel = new Label("State : ");
		grid.add(newStateLabel, 2, 3);
		newStateField = new TextField();
		newStateField.setText(editMemberData.getAddress().getState());
		grid.add(newStateField, 3, 3);

		newZipLabel = new Label("Zip : ");
		grid.add(newZipLabel, 0, 4);
		newZipField = new TextField();
		newZipField.setText(editMemberData.getAddress().getZip());
		grid.add(newZipField, 1, 4);

		newPhoneLabel = new Label("Telephone : ");
		grid.add(newPhoneLabel, 2, 4);
		newPhoneField = new TextField();
		newPhoneField.setText(editMemberData.getTelephone());
		grid.add(newPhoneField, 3, 4);

		Button backBtn = new Button("<= Back to Main");
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				Start.hideAllWindows();
				if (SystemController.currentAuth == null)
					Start.primStage().show();
				else {
					if (!ListMembersWindow.INSTANCE.isInitialized()) {
						ListMembersWindow.INSTANCE.init();
					}
					ListMembersWindow.INSTANCE.show();
				}

			}
		});

		Button updateBtn = new Button("Save Changes");
		updateBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				if (newIdField.getText().trim().isEmpty() || newFnameField.getText().trim().isEmpty()
						|| newLnameField.getText().isEmpty() || newCityField.getText().trim().isEmpty()
						|| newPhoneField.getText().trim().isEmpty() || newStateField.getText().trim().isEmpty()
						|| newStreetField.getText().trim().isEmpty() || newZipField.getText().trim().isEmpty())
					displayMessage("All fields must be filled!", false);

				else {
					Address newAddress = new Address(newStreetField.getText(), newCityField.getText(),
							newStateField.getText(), newZipField.getText());
					LibraryMember newMemberData = new LibraryMember(newIdField.getText(), newFnameField.getText(),
							newLnameField.getText(), newPhoneField.getText(), newAddress);
					DataAccess dataAccess = new DataAccessFacade();
					try {
						dataAccess.updateMember(editMemberData.getMemberId(), newMemberData);
						displayMessage("Changes Saved Successfully!", true);
					} catch (Exception exp) {
						displayMessage("Error Occured!", false);
					}
				}

			}

			public void displayMessage(String message, boolean success) {
				if (success) {
					messageBar.setFill(Start.Colors.green);
					messageBar.setText(message);
				} else {
					messageBar.setFill(Start.Colors.red);
					messageBar.setText(message);
				}
			}
		});

		HBox hBack = new HBox(10);
		hBack.setAlignment(Pos.BOTTOM_LEFT);
		hBack.getChildren().add(backBtn);
		grid.add(hBack, 0, 5);

		HBox messageBox = new HBox(10);
		messageBox.setAlignment(Pos.BOTTOM_RIGHT);
		messageBox.getChildren().add(messageBar);
		;
		grid.add(messageBox, 3, 6);

		HBox hSave = new HBox(10);
		hSave.setAlignment(Pos.BOTTOM_RIGHT);
		hSave.getChildren().add(updateBtn);
		;
		grid.add(hSave, 3, 5);

			
		Scene scene = new Scene(grid,700, 450);
		
		scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		 getIcons().add(new Image("ui/700x4503.jpg"));
		setScene(scene);
	}
}
