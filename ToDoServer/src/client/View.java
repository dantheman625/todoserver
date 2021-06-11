package client;

import java.time.format.DateTimeFormatter;
import commons.ToDo;
import commons.ToDo.Importance;
import commons.User;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class View {
	Stage stage;
	Model model;
	Button connect = new Button("Connect");
	Button back = new Button("Back");
	Button updateUser = new Button("Update");
 //Login Window
	Label userLabel = new Label("User Name: ");
	Label pwLabel = new Label("Password: ");
	
	TextField userField = new TextField();
	PasswordField pwField = new PasswordField();
	
	Button loginButton = new Button("Login");
	Button toregisButton = new Button("Regsiter");
	
	//Register Window
	Label name = new Label("Name: ");
	Label emaiLabel = new Label("E-Mail Address: ");
	
	TextField nameField = new TextField();
	TextField emailField = new TextField();
	
	Button registerButton = new Button("Register");
	Button toLoginButton = new Button("Login");
	
	//Menu Items Home
	MenuBar menuBar = new MenuBar();
	MenuItem createTodo = new MenuItem("Create To-Do");
	MenuItem deleteTodo = new MenuItem("Delete To-Do");
	
	MenuItem logout = new MenuItem("Logout");
	MenuItem accountsettings = new MenuItem("Account Settings");
	
	//Home Pane
	Label helloLabel;
	Label tdTitle;
	Label tdprio;
	Label tdDesc;
	Label tdDuedate;
	Label tdSortby;
	ListView<ToDo> todoList;
	ComboBox<String> sortBox = new ComboBox<String>();
	Button applysort = new Button("Apply");
	
	//Todo
	TextField titleField;
	TextField descField;
	ComboBox<Importance> todoComboBox = new ComboBox<ToDo.Importance>();
	DatePicker dueDatePicker;
	Button addTodo = new Button("Create");
	Label ctdtitle = new Label("Title: ");
	Label ctdprio = new Label("Importance: ");
	Label ctddesc = new Label("Description: ");
	Label ctdduedate = new Label("Due Date: ");
	
	
	
	public View(Stage primaryStage, Model model) {
		stage=primaryStage;
		this.model=model;
		todoList = new ListView<ToDo>();
		todoList.getStyleClass().add("contactlist");
		todoList.setCellFactory(new Callback<ListView<ToDo>, ListCell<ToDo>>() {
	    	
	    	@Override
	    	public ListCell<ToDo> call(ListView<ToDo> param) {
	    		final ListCell<ToDo> cell = new ListCell<ToDo>() {
	    			
	    			@Override
	    			protected void updateItem(ToDo item, boolean empty) {
	    				super.updateItem(item, empty);
	    				if (item != null) {
	    					setText(item.getTitle());
	    				}
	    				else {
	    					setText(null);
	    				}
	    			}
	    		};
	    		return cell;
	    	}});
		sortBox.getItems().addAll(model.sortaplh, model.sorttime, model.sortimp);
		Menu todoMenu = new Menu();
		todoMenu.setText("Manage Todo's");
		todoMenu.getItems().addAll(createTodo, deleteTodo);
		deleteTodo.disableProperty().bind(contactSelected());
		
		Menu accountMenu = new Menu();
		accountMenu.setText("Manage Account");
		accountMenu.getItems().addAll(logout,accountsettings);
		
		menuBar.getMenus().addAll(todoMenu, accountMenu);
		
		Label port = new Label("Port");
		Label ip = new Label("IP-Address");
		
		TextField portField = new TextField();
		TextField ipField = new TextField();
		
		
		HBox portBox = new HBox(port, portField);
		HBox ipBox = new HBox(ip, ipField);
		
		HBox buttonBox = new HBox(connect);
		
		VBox topBox = new VBox(portBox, ipBox, buttonBox);
		topBox.getStyleClass().add("box");
		
		Scene scene = new Scene(topBox);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("Client");
		
	}
	
	public VBox loginWindow() {
		Label login = new Label("Login");
		login.getStyleClass().add("titlelabel");
		HBox loginBox = new HBox(login);
		userField.clear();
		HBox userBox = new HBox(userLabel, userField);
		pwField.clear();
		HBox pwBox = new HBox(pwLabel, pwField);
		
		HBox buttonBox = new HBox(toregisButton, loginButton);
		
		VBox topBox = new VBox(loginBox, userBox, pwBox, buttonBox);
		topBox.getStyleClass().add("box");
		return topBox;
	}
	
	
	public BorderPane homeView() {
		User item = model.currentUser;
		todoList.getItems().clear();
		todoList.getItems().addAll(model.getObsList(""));
		
		BorderPane topPane = new BorderPane();
		topPane.setTop(menuBar);
		
		helloLabel = new Label("Hello " + item.getName());
		helloLabel.getStyleClass().add("titlelabel");
		HBox helloBox = new HBox(helloLabel);
		
		tdSortby = new Label("Sort by: ");
		
		HBox sortHBox = new HBox(tdSortby, sortBox, applysort);
		
		VBox listBox = new VBox(todoList);
		
		tdTitle = new Label(" ");
		HBox titleBox = new HBox(tdTitle);
		
		tdprio = new Label(" ");
		HBox prioBox = new HBox(tdprio);
		
		tdDesc = new Label(" ");
		HBox descBox = new HBox(tdDesc);
		
		tdDuedate = new Label(" ");
		HBox dueBox = new HBox(tdDuedate);
		
		VBox contentBox = new VBox(titleBox, prioBox, descBox, dueBox);
		
		HBox centerBox = new HBox(listBox, contentBox);
		
		VBox topBox = new VBox(helloBox, sortHBox, centerBox);
		topBox.getStyleClass().add("box");
		
		topPane.setCenter(topBox);
		
		topPane.getStyleClass().add("gridpane");
		
		return topPane;
	}
	
	public void sortList(String criteria) {
		todoList.getItems().clear();
		todoList.getItems().addAll(model.getObsList(criteria));
	}
	
	public VBox registerView() {
		Label title = new Label("Register new Account");
		title.getStyleClass().add("titlelabel");
		HBox titleBox = new HBox(title);
		
		HBox nameBox = new HBox(name, nameField);
		
		HBox emailBox = new HBox(emaiLabel, emailField);
		
		HBox pwBox = new HBox(pwLabel, pwField);
		
		HBox buttonBox = new HBox(toLoginButton, registerButton);
		
		VBox topBox = new VBox(titleBox, nameBox,emailBox, pwBox, buttonBox);
		topBox.getStyleClass().add("box");
		
		return topBox;
	}
	
	public VBox accountSettings() {
		User user = model.currentUser;
		Label title = new Label("Account Setting");
		title.getStyleClass().add("titlelabel");
		HBox titleBox = new HBox(title);
		
		nameField.setText(user.getName());
		HBox nameBox = new HBox(name, nameField);
		
		pwField.setText(user.getPassword());
		HBox pwBox = new HBox(pwLabel, pwField);
		
		HBox buttonBox = new HBox(back, updateUser);
		
		VBox topBox = new VBox(titleBox, nameBox, pwBox, buttonBox);
		topBox.getStyleClass().add("box");
		return topBox;
	}
	
	public void selectToDo(ToDo doitem) {
		tdTitle.setText(doitem.getTitle());
		tdDesc.setText(doitem.getDescription());
		tdprio.setText(doitem.getImportance().toString());
		if (doitem.getDueDate()!= null) {
			tdDuedate.setText(doitem.getDueDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
		}
	}
	
	public VBox createTodoView() {
		Label title = new Label("Create Todo");
		title.getStyleClass().add("titlelabel");
		HBox titleBox = new HBox(title);
		
		titleField = new TextField();
		HBox tdtitBox = new HBox(ctdtitle, titleField);
		
		todoComboBox.getItems().setAll(Importance.values());
		todoComboBox.setValue(null);
		HBox prioBox = new HBox(ctdprio, todoComboBox);
		
		HBox desctitle = new HBox(ctddesc);
		
		descField = new TextField();
		HBox descBox = new HBox(descField);
		
		dueDatePicker = new DatePicker();
		HBox duedateBox = new HBox(ctdduedate, dueDatePicker);
		
		HBox buttonBox = new HBox(back, addTodo);
		addTodo.disableProperty().bind(Bindings.isEmpty(titleField.textProperty())
				.or(Bindings.isEmpty(descField.textProperty()).or(Bindings.isNull(todoComboBox.valueProperty()))));
		VBox topBox = new VBox(titleBox, tdtitBox, prioBox, desctitle, descBox, duedateBox, buttonBox);
		topBox.getStyleClass().add("box");
		return topBox;
	}
	
	public BooleanBinding contactSelected() {
		BooleanBinding notselected = todoList.getSelectionModel().selectedItemProperty().isNull();
		return notselected;
	}
	
	public void userNotFound() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setContentText("User not Found!");
		alert.showAndWait();
	}
	
	public void wrongPassword() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setContentText("Wrong Password");
		alert.showAndWait();
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public void start() {
		stage.show();
	}
	
}
