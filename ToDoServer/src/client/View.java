package client;

import commons.ToDo;
import commons.ToDo.Importance;
import commons.User;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
	TextField pwField = new TextField();
	
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
	ListView<ToDo> todoList = new ListView<ToDo>();
	
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
		Label port = new Label("Port");
		Label ip = new Label("IP-Address");
		
		TextField portField = new TextField();
		TextField ipField = new TextField();
		
		
		HBox portBox = new HBox(port, portField);
		HBox ipBox = new HBox(ip, ipField);
		
		HBox buttonBox = new HBox(connect);
		
		VBox topBox = new VBox(portBox, ipBox, buttonBox);
		
		Scene scene = new Scene(topBox);
		stage.setScene(scene);
		stage.setTitle("Client");
		
		Menu todoMenu = new Menu();
		todoMenu.setText("Manage Todo's");
		todoMenu.getItems().addAll(createTodo, deleteTodo);
		
		Menu accountMenu = new Menu();
		accountMenu.setText("Manage Account");
		accountMenu.getItems().addAll(logout,accountsettings);
		
		menuBar.getMenus().addAll(todoMenu, accountMenu);
	}
	
	public VBox loginWindow() {
		Label login = new Label("Login");
		HBox loginBox = new HBox(login);
		userField.clear();
		HBox userBox = new HBox(userLabel, userField);
		pwField.clear();
		HBox pwBox = new HBox(pwLabel, pwField);
		
		HBox buttonBox = new HBox(toregisButton, loginButton);
		
		VBox topBox = new VBox(loginBox, userBox, pwBox, buttonBox);
		return topBox;
	}
	
	
	public BorderPane homeView() {
		User item = model.currentUser;
		todoList.getItems().addAll(model.userTodo);
		
		BorderPane topPane = new BorderPane();
		topPane.setTop(menuBar);
		
		helloLabel = new Label("Hello " + item.getName());
		HBox helloBox = new HBox(helloLabel);
		
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
		
		topPane.setCenter(centerBox);
		
		return topPane;
	}
	
	public VBox registerView() {
		Label title = new Label("Register new Account");
		HBox titleBox = new HBox(title);
		
		HBox nameBox = new HBox(name, nameField);
		
		HBox emailBox = new HBox(emaiLabel, emailField);
		
		HBox pwBox = new HBox(pwLabel, pwField);
		
		HBox buttonBox = new HBox(toLoginButton, registerButton);
		
		VBox topBox = new VBox(titleBox, nameBox,emailBox, pwBox, buttonBox);
		return topBox;
	}
	
	public VBox accountSettings() {
		User user = model.currentUser;
		Label title = new Label("Account Setting");
		HBox titleBox = new HBox(title);
		
		nameField.setText(user.getName());
		HBox nameBox = new HBox(name, nameField);
		
		pwField.setText(user.getPassword());
		HBox pwBox = new HBox(pwLabel, pwField);
		
		HBox buttonBox = new HBox(back, updateUser);
		
		VBox topBox = new VBox(titleBox, nameBox, pwBox, buttonBox);
		return topBox;
	}
	
	public void selectToDo(ToDo doitem) {
		tdTitle.setText(doitem.getTitle());
		tdDesc.setText(doitem.getDescription());
		tdprio.setText(doitem.getImportance().toString());
		if (doitem.getDueDate()!= null) {
			tdDuedate.setText(doitem.getDueDate().toString());
		}
	}
	
	public VBox createTodoView() {
		Label title = new Label("Create Todo");
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
		
		VBox topBox = new VBox(titleBox, tdtitBox, prioBox, desctitle, descBox, duedateBox, buttonBox);
		return topBox;
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
