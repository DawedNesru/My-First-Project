package ui;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MemberCheckoutWindow extends Stage implements LibWindow {
		public static final MemberCheckoutWindow INSTANCE = new MemberCheckoutWindow();
	    private GridPane rootLayout;
		private boolean isInitialized = false;
		public boolean isInitialized() {
			return isInitialized;
		}
		public void isInitialized(boolean val) {
			isInitialized = val;
		}

		private MemberCheckoutWindow() {}

		public void init() {
			try{
				FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(MemberCheckoutWindow.class.getResource("/ui/DisplayMemberCheckout.fxml"));
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


