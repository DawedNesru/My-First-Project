package ui;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ListAuthorsWindow extends Stage implements LibWindow {
		public static final ListAuthorsWindow INSTANCE = new ListAuthorsWindow();
	    private GridPane rootLayout;

		private boolean isInitialized = false;
		public boolean isInitialized() {
			return isInitialized;
		}
		public void isInitialized(boolean val) {
			isInitialized = val;
		}
		
		private ListAuthorsWindow() {}

		public void init() {
			try{
    			FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(ListAuthorsWindow.class.getResource("/ui/ListAuthors.fxml"));
	            rootLayout = (GridPane) loader.load();
	            setAlwaysOnTop(true);
	            Scene scene = new Scene(rootLayout);
	            scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
	            getIcons().add(new Image("ui/700x4503.jpg"));
	            setScene(scene);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}


