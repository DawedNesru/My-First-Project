package ui;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import business.MembersController;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import dataaccess.Auth;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javafx.stage.Stage;

public class MainMenuWindow extends Stage implements LibWindow {
	public static final MainMenuWindow INSTANCE = new MainMenuWindow();

	private boolean isInitialized = false;

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	@Override
	public void init() {
		GridPane grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		VBox topContainer = new VBox();
		topContainer.setId("top-container");
		MenuBar mainMenu = new MenuBar();
		VBox imageHolder = new VBox();

		Image image;

		if (SystemController.currentAuth == Auth.ADMIN)
			image = new Image("ui/admin.jpg", 400, 300, false, false);
		else if (SystemController.currentAuth == Auth.LIBRARIAN)
			image = new Image("ui/librarian.jpg", 400, 300, false, false);
		else
			image = new Image("ui/both.jpg", 400, 300, false, false);

		// simply displays in ImageView the image as is
		ImageView iv = new ImageView();
		iv.setImage(image);
		imageHolder.getChildren().add(iv);
		imageHolder.setAlignment(Pos.CENTER);
		HBox splashBox = new HBox();
		Label splashLabel = new Label(SystemController.currentAuth.toString() + " PANEL");
		splashLabel.setFont(Font.font("Trajan Pro", FontWeight.BOLD, 30));
		splashBox.getChildren().add(splashLabel);
		splashBox.setAlignment(Pos.CENTER);

		topContainer.getChildren().add(mainMenu);
		topContainer.getChildren().add(splashBox);
		topContainer.getChildren().add(imageHolder);
		
		if (SystemController.currentAuth == Auth.ADMIN)
		{
			
			image = new Image("ui/admin.jpg", 400, 300, false, false);
		}
		else if (SystemController.currentAuth == Auth.LIBRARIAN)
		{
		
			image = new Image("ui/librarian.jpg", 400, 300, false, false);
		}
		else
		{
			
			image = new Image("ui/both.jpg", 400, 300, false, false);
		}
		
		Menu optionsMenu =  new Menu("Options");
		Label Adminrole = new Label("Administrator");
		MenuItem logout = new MenuItem("Logout");
		logout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				SystemController.currentAuth = null;
				Start.hideAllWindows();
				Start.primStage().show();
			}
		});

		MenuItem addMember = new MenuItem("Add New Member");
		addMember.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Start.hideAllWindows();
				if (!AddMemberWindow.INSTANCE.isInitialized()) {
					AddMemberWindow.INSTANCE.init();
				}
				AddMemberWindow.INSTANCE.clear();
				AddMemberWindow.INSTANCE.show();
			}
		});

		MenuItem editMember = new MenuItem("Edit Existing Member");
		editMember.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Start.hideAllWindows();
				if (!ListMembersWindow.INSTANCE.isInitialized()) {
					ListMembersWindow.INSTANCE.init();
				}
				ControllerInterface ci = new SystemController();
				HashMap<String, LibraryMember> allMembers = ci.getAllMembersHashMap();
				List<LibraryMember> list = new ArrayList<LibraryMember>();

				for (Map.Entry<String, LibraryMember> entry : allMembers.entrySet()) {

					list.add(new LibraryMember(entry.getValue().getMemberId().toString(),
							entry.getValue().getFirstName().toString(), entry.getValue().getLastName().toString(),
							entry.getValue().getTelephone().toString(), entry.getValue().getAddress()));

				}

				MembersController.editableRecords = true;
				
				@SuppressWarnings("rawtypes")
				ObservableList data = FXCollections.observableArrayList(list);

				ListMembersWindow.INSTANCE.setData(data);
				
				ListMembersWindow.INSTANCE.show();

			}
		});

		MenuItem addBook = new MenuItem("Add New Book");

		addBook.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Start.hideAllWindows();
				if (!AddBookWindow.INSTANCE.isInitialized()) {
					AddBookWindow.INSTANCE.init();
				}
				AddBookWindow.INSTANCE.show();

			}
		});

		MenuItem checkoutBook = new MenuItem("Checkout Book");
		checkoutBook.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Start.hideAllWindows();
				Start.hideAllWindows();
				if (!CheckoutWindow.INSTANCE.isInitialized()) {
					CheckoutWindow.INSTANCE.init();
				}

				CheckoutWindow.INSTANCE.show();
			}
		});

		MenuItem listAllBooks = new MenuItem("Show All Books");
		listAllBooks.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Start.hideAllWindows();
				if (!ListBooksWindow.INSTANCE.isInitialized()) {
					ListBooksWindow.INSTANCE.init();
				}

				ListBooksWindow.INSTANCE.show();
			}
		});

		MenuItem copyOfBook = new MenuItem("Add Copy of Book");

		copyOfBook.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Start.hideAllWindows();
				if (!AddCopyWindow.INSTANCE.isInitialized()) {
					AddCopyWindow.INSTANCE.init();
				}
				AddCopyWindow.INSTANCE.show();
			}
		});

		MenuItem showBookStatus = new MenuItem("Book Status (Overdue)");

		showBookStatus.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Start.hideAllWindows();
				if (!BookStatusWindow.INSTANCE.isInitialized()) {
					BookStatusWindow.INSTANCE.init();
				}
				BookStatusWindow.INSTANCE.show();

			}
		});

		MenuItem showAllMembers = new MenuItem("Show All Members");

		showAllMembers.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Start.hideAllWindows();
				if (!ListMembersWindow.INSTANCE.isInitialized()) {
					ListMembersWindow.INSTANCE.init();
				}
				ListMembersWindow.INSTANCE.show();

			}
		});
		
		MenuItem displayMemberRecord = new MenuItem("Display Checkout Record of Member");
		
		
		displayMemberRecord.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Start.hideAllWindows();
				if (!MemberCheckoutWindow.INSTANCE.isInitialized()) {
					MemberCheckoutWindow.INSTANCE.init();
				}
				MemberCheckoutWindow.INSTANCE.show();

			}
		});

		if (SystemController.currentAuth == Auth.LIBRARIAN)
			optionsMenu.getItems().addAll( listAllBooks, showAllMembers, showBookStatus, checkoutBook, displayMemberRecord, logout);
		else if (SystemController.currentAuth == Auth.ADMIN)
			optionsMenu.getItems().addAll(listAllBooks, showAllMembers, addBook, addMember, editMember, copyOfBook, logout);
		else
			optionsMenu.getItems().addAll( listAllBooks, showAllMembers, showBookStatus, checkoutBook, addBook, addMember, editMember, copyOfBook, displayMemberRecord, logout);

		mainMenu.getMenus().addAll(optionsMenu);
		Scene scene = new Scene(topContainer, 700, 450);
		scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		 getIcons().add(new Image("ui/700x4503.jpg"));
		setScene(scene);

	}
}
