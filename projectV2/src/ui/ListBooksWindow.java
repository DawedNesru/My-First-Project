package ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ListBooksWindow extends Stage  /* implements LibWindow */ {
		public static final ListBooksWindow INSTANCE = new ListBooksWindow();
	    private GridPane rootLayout;
		private boolean isInitialized = false;
		public boolean isInitialized() {
			return isInitialized;
		}
		public void isInitialized(boolean val) {
			isInitialized = val;
		}

		private ListBooksWindow() {
			 
		}

		public void init() {
			try{
				FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(ListBooksWindow.class.getResource("/ui/ListBooks.fxml"));
	            rootLayout = (GridPane) loader.load();
	            Scene scene = new Scene(rootLayout);
	            scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
	            getIcons().add(new Image("ui/700x4503.jpg"));
	           
		        setScene(scene);
		       
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

}
