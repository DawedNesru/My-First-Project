package ui;

import java.io.IOException;

import business.LibraryMember;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ListMembersWindow extends Stage implements LibWindow {
		public static final ListMembersWindow INSTANCE = new ListMembersWindow();
	    private GridPane rootLayout;
		private boolean isInitialized = false;
		public ObservableList<LibraryMember> allMemberDataRecords;
		public boolean isInitialized() {
			return isInitialized;
		}
		public void isInitialized(boolean val) {
			isInitialized = val;
		}
		
		public void setData(String data) {
			//allMemberDataRecords = members;
			//ta.setText(data);
			//memberList.forEach(member -> members.put(member.getMemberId(), member));
			
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" }) 
		public void setData(ObservableList data) {
			//System.out.println(data);
			allMemberDataRecords = data;
			//ta.setText(data);
			//memberList.forEach(member -> members.put(member.getMemberId(), member));
			
		}
		

		private ListMembersWindow() {}

		public void init() {
			try{
				FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(ListMembersWindow.class.getResource("/ui/ListMembers.fxml"));
	            rootLayout = (GridPane) loader.load();

	            // Show the scene containing the root layout.
	            Scene scene = new Scene(rootLayout);
	            scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
	            getIcons().add(new Image("ui/700x4503.jpg"));
	            setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

}
