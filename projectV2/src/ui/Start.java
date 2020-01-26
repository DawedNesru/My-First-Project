package ui;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Start extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	private static Stage primStage = null;

	public static Stage primStage() {
		return primStage;
	}

	public static class Colors {
		static Color green = Color.web("#034220");
		static Color red = Color.FIREBRICK;
	}

	private static Stage[] allWindows = {
			AddBookWindow.INSTANCE,
			AddCopyWindow.INSTANCE,
			AddMemberWindow.INSTANCE,
			ListMembersWindow.INSTANCE,
			CheckoutWindow.INSTANCE,
			BookStatusWindow.INSTANCE,
			ListAuthorsWindow.INSTANCE,
			ListBooksWindow.INSTANCE,
			LoginWindow.INSTANCE,
			MainMenuWindow.INSTANCE
			};

	public static void hideAllWindows() {
		primStage.hide();
		for (Stage st : allWindows) {
			st.hide();
		}
	}

	@Override
	public void start(Stage primaryStage) {
		
		primStage = primaryStage;
		primaryStage.setTitle("Library Management System");
		
		VBox topContainer = new VBox();
		topContainer.setId("top-container");
		MenuBar mainMenu = new MenuBar();
		VBox imageHolder = new VBox();
		Image image = new Image("ui/700x4502.jpg", 700, 450, false, false);

		ImageView iv = new ImageView();
		iv.setImage(image);
		imageHolder.getChildren().add(iv);
		imageHolder.setAlignment(Pos.CENTER);
		HBox splashBox = new HBox();
		Label splashLabel = new Label("Welcome to the Library System");
		BackgroundFill SplashBackground_fill = new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY); 
		Background SplashBackground = new Background(SplashBackground_fill); 
		  
        // set background 
		splashLabel.setBackground(SplashBackground); 
	    splashLabel.setTextFill(Color.AZURE);
	    splashLabel.setPrefWidth(700);
	    splashLabel.setAlignment(Pos.CENTER);
		splashLabel.setFont(new Font("Verdana", 32));
		splashLabel.setFont(Font.font("Apple Symbols", FontWeight.EXTRA_BOLD, 200));
		splashBox.getChildren().add(splashLabel);
		splashBox.setAlignment(Pos.CENTER);
		
		DropShadow dropShadow = new DropShadow();
		 dropShadow.setRadius(5.0);
		 dropShadow.setOffsetX(3.0);
		 dropShadow.setOffsetY(3.0);
		 dropShadow.setColor(Color.color(0.4, 0.5, 0.5));  
		 DropShadow dropShadow2 = new DropShadow();
		 dropShadow2.setOffsetX(6.0);
		 dropShadow2.setOffsetY(4.0);

				 splashLabel.setEffect(dropShadow);
		//splashBox.setBackground(new Background(new BadkgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
     //splashLabel.setStyle("-fx-border-color:white; -fx-background-color: white;");
				 splashLabel.setStyle("-fx-font-size: 32pt; -fx-font-family: Segoe UI Light; -fx-text-fill: Blue; -fx-opacity: 1;");
					
		topContainer.getChildren().add(mainMenu);
		topContainer.getChildren().add(splashBox);
	
		topContainer.getChildren().add(imageHolder);
	
		Menu optionsMenu = new Menu("Options");
		MenuItem login = new MenuItem("Sign In");
		BackgroundFill menuBackground_fill = new BackgroundFill(Color.ANTIQUEWHITE, CornerRadii.EMPTY, Insets.EMPTY); 
		Background menuBackground = new Background(menuBackground_fill); 
		  mainMenu.setBackground(menuBackground);
		 // splashLabel.setPrefSize( Double.MAX_VALUE, Double.MAX_VALUE );
        // set background 
		//optionsMenu.setGraphic(Color.AQUAMARINE);
		login.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				hideAllWindows();
				if (!LoginWindow.INSTANCE.isInitialized()) {
					LoginWindow.INSTANCE.init();
				}
				LoginWindow.INSTANCE.clear();
				LoginWindow.INSTANCE.show();
			}
		});

		MenuItem listAllBooks = new MenuItem("Display Books");
		listAllBooks.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				hideAllWindows();
				if (!ListBooksWindow.INSTANCE.isInitialized()) {
					ListBooksWindow.INSTANCE.init();
					
				}
				
				ListBooksWindow.INSTANCE.show();
				
             
			
			}
		});

		optionsMenu.getItems().addAll(login, listAllBooks);
		splashLabel.setFont(new Font("Arial", 40));
		mainMenu.getMenus().addAll(optionsMenu);
		Scene scene = new Scene(topContainer, 700, 450);
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("ui/700x4503.jpg"));
	  		scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		primaryStage.show();
	}

}
