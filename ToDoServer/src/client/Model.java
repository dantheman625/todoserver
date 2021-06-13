package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import commons.Message;
import commons.MessageType;
import commons.Message_DeleteTodo;
import commons.Message_FindUser;
import commons.Message_LoginSuccess;
import commons.Message_NewPassword;
import commons.Message_NewTodo;
import commons.Message_NewUser;
import commons.Message_Password;
import commons.Message_SendContent;
import commons.ToDo;
import commons.ToDo.Importance;
import commons.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {
	//currentUser is my token
	User currentUser;
	ArrayList<User> userlist = new ArrayList<User>();
	ArrayList<ToDo> userTodo = new ArrayList<ToDo>();
	String sorttime = "Creation Date";
	String sortaplh = "Alphabetically";
	String sortimp = "Importance";
	
	public Model() {
		//getUsers();
		//getToDos();
	}
	
	public void getUsers() {
		try {
		ObjectInputStream in = new ObjectInputStream(connect().getInputStream());
		userlist = (ArrayList<User>) in.readObject();
		
	} catch (Exception e) {
		System.out.println(e);
	}
	}

	public Socket connect() {
		Socket socket = null;
		try {
			socket = new Socket("127.0.0.1", 50002);
		} catch (Exception e) {
			System.out.println(e);
		}
		return socket;
	}

	
	
	public void logout() {
		userTodo.clear();
	}
	
	public boolean validateEmail (String email) {
		boolean valid = false;
		String[] addressParts = email.split("@");
		if (addressParts.length == 2 && !addressParts[0].isEmpty() && !addressParts[1].isEmpty()) {
			
			if (addressParts[1].charAt(addressParts[1].length() - 1) != '.') {
				
				String[] domainParts = addressParts[1].split("\\.");
				if (domainParts.length >= 2) {
					valid = true;
					for (String s : domainParts) {
						if (s.length() < 2) valid = false;
					}
				}
			}
		}
		return valid;
	}
	
	public String checkPassword(String email, String password) {
		String check = null;
		Socket socket = connect();
		if(socket != null) {
			Message msgOut = new Message_FindUser(email);
			try {
				msgOut.send(socket);
				Message msgIn = Message.receive(socket);
				if (MessageType.getType(msgIn) == MessageType.Password) {
					Message_Password pw = (Message_Password) msgIn;
					if (password.equals(pw.getPassword())) {
						check = "pwc";
					} else {
						check = "pwf";
					}
				} else if (MessageType.getType(msgIn) == MessageType.UserNotFound) {
					check = "nf";
				}
			} catch (Exception e) {
				
			}
			try { if (socket != null) socket.close(); } catch (IOException e) {}
		}
		return check;
	}
	
	public void getContent(String email) {
		Socket socket = connect();
		if (socket != null) {
			Message msgout = new Message_LoginSuccess(email);
			try {
				msgout.send(socket);
				Message msgIn = Message.receive(socket);
					Message_SendContent content = (Message_SendContent) msgIn;
					currentUser = content.getUser();
					userTodo = content.getTodoList();
			
		} catch (Exception e) {
			
		}
	}
	}
	
	public ObservableList<ToDo> getObsList(String criteria){
		ObservableList<ToDo> obsList = FXCollections.observableArrayList();
		ArrayList<ToDo> toSort = userTodo;
		switch (criteria) {
		case "Creation Date":
			Collections.sort(toSort, ToDo.timeStampComparator);
			break;
		case "Alphabetically":
			Collections.sort(toSort, ToDo.titleComparator);
			break;
		case "Importance":
			Collections.sort(toSort, ToDo.prioComparator);
		}
		obsList.addAll(toSort);
		return obsList;
	}
	
	public boolean register(String email, String password, String name) {
		Socket socket = connect();
		boolean registered = true;
		//login taken
		if (socket != null) {
			Message msgout = new Message_NewUser(email, password, name);
			try {
				msgout.send(socket);
				Message msgIn = Message.receive(socket);
				if(MessageType.getType(msgIn)==MessageType.SendContent) {
				Message_SendContent content = (Message_SendContent) msgIn;
				currentUser = content.getUser();
				userTodo = content.getTodoList();
				} else if (MessageType.getType(msgIn)==MessageType.EmailTaken) {
					registered = false;
				}
			} catch (Exception e) {
				
			}
		}
		return registered;
	}
	
	public void newTodo(String email, String title, String description, String todoid, Importance importance, String dueDate) {
		Socket socket = connect();
		if (socket != null) {
			Message_NewTodo msgout = new Message_NewTodo(email, todoid, title, description, importance);
			if(dueDate != null)
				msgout.setDueDate(dueDate);
			try {
				msgout.send(socket);
				Message msgIn = Message.receive(socket);
				Message_SendContent content = (Message_SendContent) msgIn;
				currentUser = content.getUser();
				userTodo = content.getTodoList();
			} catch (Exception e) {
				
			}
		}
	}
	
	public void accountSettings(String email, String newPassword, String newName) {
		Socket socket = connect();
		if (socket != null) {
			Message msgout = new Message_NewPassword(email, newPassword, newName);
			try {
				msgout.send(socket);
				Message msgIn = Message.receive(socket);
				Message_SendContent content = (Message_SendContent) msgIn;
				currentUser = content.getUser();
				userTodo = content.getTodoList();
			} catch (Exception e) {
				
			}
		}
		
	}
	
	public void deleteTodo(String todoid) {
		Socket socket = connect();
		if (socket != null) {
			Message msgout = new Message_DeleteTodo(currentUser.getEmail(), todoid);
			try {
				msgout.send(socket);
				Message msgIn = Message.receive(socket);
				Message_SendContent content = (Message_SendContent) msgIn;
				currentUser = content.getUser();
				userTodo = content.getTodoList();
			} catch (Exception e) {
				
			}
		}
		
	}
	
	public String createID() {
		int id = 0;
		String idString ="fail";
		boolean duplicate = false;
		Random rand = new Random();
		while (id == 0 || duplicate == true) {
			duplicate = false;
			id = rand.nextInt(90000) + 10000;
			idString = currentUser.getEmail()+ "," + id;
			for (ToDo toDo : userTodo) {
				if (toDo.getId().equals(idString)){
					duplicate = true;
				}
			}
		}
		return idString;
	}
	
	public boolean checkEmail(String email) {
		return true;
	}
	
	}

