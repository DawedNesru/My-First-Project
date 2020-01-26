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

public class AddMemberWindow extends Stage implements LibWindow {
	public static final AddMemberWindow INSTANCE = new AddMemberWindow();
	
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
	
	private Label newMemberIdLabel;
	private TextField newMemberIdField;
	
	private Label newMemberFnameLabel;
	private TextField newMemberFnameField;
	
	private Label newMemberLnameLabel;
	private TextField newMemberLnameField;
	
	private Label newMemberStreetLabel;
	private TextField newMemberStreetField;
	
	private Label newMemberCityLabel;
	private TextField newMemberCityField;
	
	private Label newMemberStateLabel;
	private TextField newMemberStateField;
	
	private Label newMemberZipLabel;
	private TextField newMemberZipField;
	
	private Label newMemberPhoneLabel;
	private TextField newMemberPhoneField;
	
	/* This class is a singleton */
	private AddMemberWindow() {}
	
	public void init() {
		GridPane grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Add New Member");
        scenetitle.setFont(Font.font("Harlow Solid Italic", FontWeight.NORMAL, 20)); //Tahoma
        grid.add(scenetitle, 0, 0, 2, 1);

        /*
		ta = new TextArea();
		grid.add(ta, 0,1);	
		*/
        
        newMemberIdLabel = new Label("Member Id : ");
        grid.add(newMemberIdLabel, 0, 2);
    	newMemberIdField = new TextField();
    	grid.add(newMemberIdField, 1, 2);
    	
    	newMemberFnameLabel = new Label("First Name : ");
        grid.add(newMemberFnameLabel, 2, 1);
    	newMemberFnameField = new TextField();
    	grid.add(newMemberFnameField, 3, 1);
    	
    	
    	newMemberLnameLabel = new Label("Last Name : ");
        grid.add(newMemberLnameLabel, 0, 1);
    	newMemberLnameField = new TextField();
    	grid.add(newMemberLnameField, 1, 1);
    	
    	newMemberStreetLabel = new Label("Street : ");
        grid.add(newMemberStreetLabel, 2, 2);
    	newMemberStreetField = new TextField();
    	grid.add(newMemberStreetField, 3, 2);
    	
    	
    	newMemberCityLabel = new Label("City : ");
        grid.add(newMemberCityLabel, 0, 3);
    	newMemberCityField = new TextField();
    	grid.add(newMemberCityField, 1, 3);
    	
    	newMemberStateLabel = new Label("State : ");
        grid.add(newMemberStateLabel, 2, 3);
    	newMemberStateField = new TextField();
    	grid.add(newMemberStateField, 3, 3);
    	
    	
    	newMemberZipLabel = new Label("Zip : ");
        grid.add(newMemberZipLabel, 0, 4);
    	newMemberZipField = new TextField();
    	grid.add(newMemberZipField, 1, 4);
    	
    	newMemberPhoneLabel = new Label("Telephone : ");
        grid.add(newMemberPhoneLabel, 2, 4);
    	newMemberPhoneField = new TextField();
    	grid.add(newMemberPhoneField, 3, 4);
    	
    	
		Button backBtn = new Button("<= Back to Main");
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		
        		Start.hideAllWindows();
        		if(SystemController.currentAuth==null)
        			Start.primStage().show();
        		else {
        			if(!MainMenuWindow.INSTANCE.isInitialized()) {
        				MainMenuWindow.INSTANCE.init();
        			}
        			MainMenuWindow.INSTANCE.show();
        		}
        		
        		//Start.hideAllWindows();
        		//Start.primStage().show();
        	}
        });
		
		Button addBtn = new Button("Add Member");
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		//System.out.println(newMemberIdField.getText()+" Saved Successfuly....");
        		//Start.hideAllWindows();
        		//Start.primStage().show();
        		
        		if(newMemberIdField.getText().trim().isEmpty() || newMemberFnameField.getText().trim().isEmpty() || newMemberLnameField.getText().isEmpty() || newMemberCityField.getText().trim().isEmpty() || newMemberPhoneField.getText().trim().isEmpty() || newMemberStateField.getText().trim().isEmpty() || newMemberStreetField.getText().trim().isEmpty() || newMemberZipField.getText().trim().isEmpty())
        			displayMessage("All fields must be filled!", false);
        		
        		else {
	        		Address newAddress = new Address(newMemberStreetField.getText(), newMemberCityField.getText(), newMemberStateField.getText(), newMemberZipField.getText());
	        		LibraryMember newMember = new LibraryMember(newMemberIdField.getText(), newMemberFnameField.getText(), newMemberLnameField.getText(), newMemberPhoneField.getText(), newAddress);
	        		DataAccess dataAccess = new DataAccessFacade();
	        		boolean response = dataAccess.addToMember(newMember);
	        		if(response) {
	            		displayMessage("Added Successfully!", true);
	            		newMemberIdField.setText("");
	                	newMemberFnameField.setText("");
	                	newMemberLnameField.setText("");
	                	newMemberStreetField.setText("");
	                	newMemberCityField.setText("");
	                	newMemberStateField.setText("");
	                	newMemberZipField.setText("");
	                	newMemberPhoneField.setText("");
	        		}
	        		else
	            		displayMessage("Error Occured!", false);
        		}
        	}
        	public void displayMessage(String message, boolean success) {
        		if(success) {
        			messageBar.setFill(Start.Colors.green);
        			messageBar.setText(message);
        		}
        		else {
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
        messageBox.getChildren().add(messageBar);;
        grid.add(messageBox, 3, 6);
        
        HBox hAdd = new HBox(10);
        hAdd.setAlignment(Pos.BOTTOM_RIGHT);
        hAdd.getChildren().add(addBtn);;
        grid.add(hAdd, 3, 5);
        
        Scene scene = new Scene(grid,700, 450);
		scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		 getIcons().add(new Image("ui/700x4503.jpg"));
        setScene(scene);
        //isInitialized(true);
	}
}
