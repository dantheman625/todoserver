package client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import commons.ToDo;
import commons.ToDo.Importance;
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
		view.updateUser.setOnAction(this::accountSettings);
		view.applysort.setOnAction(this::sortList);
	}
	
	public void connect(ActionEvent event) {
		view.getStage().getScene().setRoot(view.loginWindow());
		view.stage.sizeToScene();
	}
	
	public void back (ActionEvent event) {
		backHome();
	}
	
	public void backHome() {		
		view.getStage().getScene().setRoot(view.homeView());
		view.stage.sizeToScene();
	}
	
	public void todoList() {
		view.todoList.getItems().clear();
	}
	
	public void sortList(ActionEvent event) {
		view.sortList(view.sortBox.getValue());
	}
	
	public void registerView(ActionEvent event) {
		view.getStage().getScene().setRoot(view.registerView());
		view.nameField.clear();
		view.emailField.clear();
		view.pwField.clear();
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
	
	public void accountSettings(ActionEvent event) {
		String email = model.currentUser.getEmail();
		String newPassword = null;
		if (!view.pwField.getText().isEmpty()) {
			newPassword = view.pwField.getText();
		}
		String newName = null;
		if (!view.nameField.getText().isEmpty()) {
			newName = view.nameField.getText();
		}
		model.accountSettings(email, newPassword, newName);
		view.pwField.clear();
		view.nameField.clear();
		view.getStage().getScene().setRoot(view.homeView());
		view.stage.sizeToScene();
	}
	
	public void selectTodo(MouseEvent event) {
		view.selectToDo(view.todoList.getSelectionModel().getSelectedItem());
		view.stage.sizeToScene();
	}
	
	public void deleteTodo(ActionEvent event) {
		String todoid = null;
		todoid = view.todoList.getSelectionModel().getSelectedItem().getId();
		if(todoid != null) {
			model.deleteTodo(todoid);
		}
		view.getStage().getScene().setRoot(view.homeView());
		view.stage.sizeToScene();
	}
	
	public void createTodoView(ActionEvent event) {
		view.getStage().getScene().setRoot(view.createTodoView());
		view.stage.sizeToScene();
	}
	
	public void createTodo(ActionEvent event) {
		String id = model.createID();
		if (!id.equals("fail")) {
			String email = model.currentUser.getEmail();
			String title = view.titleField.getText();
			String description = view.descField.getText();
			Importance importance = view.todoComboBox.getValue();
			String dueDate = null;
			if(view.dueDatePicker.getValue()!= null) {
				dueDate = view.dueDatePicker.getValue().format(DateTimeFormatter.ISO_DATE);
			}
			model.newTodo(email, title, description, id, importance, dueDate);
		}
		view.titleField.clear();
		view.descField.clear();
		view.todoComboBox.setValue(null);
		view.dueDatePicker.setValue(null);
		view.getStage().getScene().setRoot(view.homeView());
		view.stage.sizeToScene();
	}
	
	public void register(ActionEvent event) {
		if(model.validateEmail(view.emailField.getText())) {
		if (model.register(view.emailField.getText(), view.pwField.getText(), 
				view.nameField.getText())) {
		view.getStage().getScene().setRoot(view.homeView());
		view.stage.sizeToScene();
		} else {
			view.emailTaken();
		}
		} else {
			view.emailNotCorrect();
		}
	}
	
	public void logout(ActionEvent event) {
		model.logout();
		view.todoList.getItems().clear();
		view.getStage().getScene().setRoot(view.loginWindow());
		view.stage.sizeToScene();
	}
	
}
