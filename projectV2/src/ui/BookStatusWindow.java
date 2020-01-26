package ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BookStatusWindow extends Stage implements LibWindow {
	public static final BookStatusWindow INSTANCE = new BookStatusWindow();
	private GridPane rootLayout;
	private boolean isInitialized = false;

	public boolean isInitialized() {
		return isInitialized;
	}

	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	private BookStatusWindow() {
	}

	public void init() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(BookStatusWindow.class.getResource("/ui/DisplayBookStatus.fxml"));
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
