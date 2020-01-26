package ui;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CheckoutWindow extends Stage implements LibWindow {
		public static final CheckoutWindow INSTANCE = new CheckoutWindow();
	    private GridPane rootLayout;

		private boolean isInitialized = false;
		public boolean isInitialized() {
			return isInitialized;
		}
		public void isInitialized(boolean val) {
			isInitialized = val;
		}

		private CheckoutWindow() {}

		public void init() {
			try{
    			FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(CheckoutWindow.class.getResource("/ui/DisplayCheckoutBook.fxml"));
	            rootLayout = (GridPane) loader.load();
	            Scene scene = new Scene(rootLayout);
	            scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
	            getIcons().add(new Image("ui/700x4503.jpg"));		    
             setScene(scene);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}


