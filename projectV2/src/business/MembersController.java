package business;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ui.EditMemberWindow;
import ui.MainMenuWindow;
import ui.Start;


public class MembersController implements Initializable { // notice this
    @FXML private TableColumn<LibraryMember, String> memberIDCol;
    @FXML private TableColumn<LibraryMember, String> firstNameCol;
    @FXML private TableColumn<LibraryMember, String> lastNameCol;
    @FXML private TableColumn<LibraryMember, String> telephoneCol;
    @FXML private TableColumn<LibraryMember, String> addressCol;
    @FXML private TableView<LibraryMember> statusTable;
    


	@FXML
	protected void back(ActionEvent e) {
		Start.hideAllWindows();
		if(SystemController.currentAuth==null)
			Start.primStage().show();
		else {
			if(!MainMenuWindow.INSTANCE.isInitialized()) {
				MainMenuWindow.INSTANCE.init();
			}
			editableRecords = false;
			MainMenuWindow.INSTANCE.show();
		}
	}

	public static boolean editableRecords;

	@FXML
	private void showAllMembersAction(ActionEvent event) throws LibrarySystemException {
		ControllerInterface c = new SystemController();
		List<LibraryMember> record;
		try {
			record = c.getAllMembers();
	        final ObservableList<LibraryMember> data = FXCollections.observableArrayList(record);
			memberIDCol.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("memberId"));
			firstNameCol.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("firstName"));
			lastNameCol.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("lastName"));
			telephoneCol.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("telephone"));
			addressCol.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("address"));
	        statusTable.setItems(data);
	        
	        if(editableRecords) {
		        statusTable.setRowFactory( tv -> {
		            TableRow<LibraryMember> memberRow = new TableRow<LibraryMember>();
		            memberRow.setOnMouseClicked(rowEvent -> {
		                if (rowEvent.getClickCount() == 2 && (!memberRow.isEmpty()) ) {
		                    LibraryMember rowData = memberRow.getItem();
		                    EditMemberWindow.INSTANCE.setData(rowData); 
		                    System.out.println(rowData);
		                    
		                    Start.hideAllWindows();
		            		if(SystemController.currentAuth==null)
		            			Start.primStage().show();
		            		else {
		            			if(!EditMemberWindow.INSTANCE.isInitialized()) {
		            				EditMemberWindow.INSTANCE.init();
		            			}
		        				EditMemberWindow.INSTANCE.clear();
		            			EditMemberWindow.INSTANCE.show();
		            		}
		                }
		            });
		            return memberRow ;
		        });
	        }
	        
		} catch (LibrarySystemException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getMessage(), JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
	 }
	


    @Override 
    public void initialize(URL url, ResourceBundle rb) {
		// do nothing
    }

}