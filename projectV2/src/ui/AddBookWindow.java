package ui;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AddBookWindow extends Stage implements LibWindow {
		public static final AddBookWindow INSTANCE = new AddBookWindow();
	    private GridPane rootLayout;
		private boolean isInitialized = false;
		public boolean isInitialized() {
			return isInitialized;
		}
		public void isInitialized(boolean val) {
			isInitialized = val;
		}

		private AddBookWindow() {}

		public void init() {
			try{
				FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(AddBookWindow.class.getResource("/ui/AddBook.fxml"));
	            rootLayout = (GridPane) loader.load();
	            Scene scene = new Scene(rootLayout);
	            scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
	           
		        setScene(scene);
		        getIcons().add(new Image("ui/700x4503.jpg"));
		        
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}


