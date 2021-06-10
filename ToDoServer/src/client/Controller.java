package client;

import commons.ToDo;
import commons.User;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class Controller {
	private Model model;
	private View view;
	
	public Controller (Model model, View view) {
		this.model = model;
		this.view = view;
		view.connect.setOnAction(this::connect);
		view.loginButton.setOnAction(this::login);
		view.registerButton.setOnAction(this::register);
		view.toregisButton.setOnAction(this::registerView);
		view.toLoginButton.setOnAction(this::connect);
		view.todoList.setOnMouseClicked(this::selectTodo);
		view.createTodo.setOnAction(this::createTodoView);
		view.addTodo.setOnAction(this::createTodo);
		view.back.setOnAction(this::back);
		view.logout.setOnAction(this::logout);
		view.accountsettings.setOnAction(this::accountSettingsView);
		view.deleteTodo.setOnAction(this::deleteTodo);
	}
	
	public void connect(ActionEvent event) {
		view.getStage().getScene().setRoot(view.loginWindow());
		view.stage.sizeToScene();
	}
	
	public void back (ActionEvent event) {
		backHome();
	}
	
	public void backHome() {
		view.todoList.getItems().clear();
		view.getStage().getScene().setRoot(view.homeView());
		todoList();
		view.stage.sizeToScene();
	}
	
	public void todoList() {
		view.todoList.getItems().addAll(model.userTodo);
	}
	
	public void registerView(ActionEvent event) {
		view.getStage().getScene().setRoot(view.registerView());
		view.stage.sizeToScene();
	}
	
	public void accountSettingsView(ActionEvent event) {
		view.getStage().getScene().setRoot(view.accountSettings());
		view.stage.sizeToScene();
	}
	
	public void login(ActionEvent event) {
		String check = model.checkPassword(view.userField.getText(), view.pwField.getText());
		switch (check) {
		case "pwc":
			model.getContent(view.userField.getText());
			backHome();
			break;
		case "pwf":
			view.wrongPassword();
			break;
		case "nf":
			view.userNotFound();
		}
		
		
	}
	
	public void selectTodo(MouseEvent event) {
		view.selectToDo(view.todoList.getSelectionModel().getSelectedItem());
		view.stage.sizeToScene();
	}
	
	public void deleteTodo(ActionEvent event) {
		ToDo toDo = view.todoList.getSelectionModel().getSelectedItem();
		model.deleteTodo(toDo);
		model.getUserTodos();
		backHome();
	}
	
	public void createTodoView(ActionEvent event) {
		view.getStage().getScene().setRoot(view.createTodoView());
		view.stage.sizeToScene();
	}
	
	public void createTodo(ActionEvent event) {
		String id = model.createID();
		if (!id.equals("fail")) {
			ToDo toDo = new ToDo(id, view.titleField.getText(), view.descField.getText(), view.todoComboBox.getValue());
			if(view.dueDatePicker.getValue() != null) {
				//add duedate
			}
			model.addTodo(toDo);
		}
		backHome();
	}
	
	public void register(ActionEvent event) {
		String email = view.emailField.getText();
		if (model.checkEmail(email)&& !model.existingUser(email)) {
			User user = new User(view.nameField.getText(), email, view.pwField.getText());
			model.addUser(user);
		}
		model.findUser(email);
		model.getUserTodos();
		view.getStage().getScene().setRoot(view.homeView());
		view.stage.sizeToScene();
	}
	
	public void logout(ActionEvent event) {
		model.logout();
		view.todoList.getItems().clear();
		view.getStage().getScene().setRoot(view.loginWindow());
		view.stage.sizeToScene();
	}
	
}
