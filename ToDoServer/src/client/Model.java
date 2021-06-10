package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import commons.Message;
import commons.MessageType;
import commons.Message_FindUser;
import commons.Message_LoginSuccess;
import commons.Message_Password;
import commons.Message_SendContent;
import commons.ToDo;
import commons.ToDo.Importance;
import commons.User;

public class Model {
	User currentUser;
	ArrayList<User> userlist = new ArrayList<User>();
	ArrayList<ToDo> wholeList = new ArrayList<ToDo>();
	ArrayList<ToDo> userTodo = new ArrayList<ToDo>();
	
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
	
	public void getToDos() {
		wholeList.add(new ToDo("admin1", "Wäsche", "Tumbler alles", Importance.Low));
		wholeList.add(new ToDo("admin1", "Kkochen", "Spaghetti", Importance.High));
		wholeList.add(new ToDo("admin2", "Rasenmähen", "Mit alles ohne Zwiebel", Importance.Medium));
	}
	
	public void getUserTodos() {
		userTodo.clear();
		for (ToDo todo : wholeList) {
			if (todo.getId().contains(currentUser.getEmail()))
				userTodo.add(todo);
		}
	}
	
	public void logout() {
		userTodo.clear();
	}
	
	
	public boolean loginCheck(String password) {
		if(password.equals(currentUser.getPassword())) {
			return true;
		} else {
			return false;
		}
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
	
	public boolean findUser(String email) {
		boolean userFound=false;
		for (User u : userlist) {
			if(u.getEmail().equals(email)) {
				currentUser=u;
				userFound = true;
				break;
			} 
		}
		return userFound;
	}
	
	public void addTodo(ToDo item) {
		wholeList.add(item);
		getUserTodos();
	}
	
	public void deleteTodo(ToDo item) {
		for(ToDo todo : wholeList) {
			if(todo.getId().equals(item.getId())) {
				wholeList.remove(todo);
				break;
			}
		}
		
	}
	public void addUser(User user) {
		userlist.add(user);
	}
	
	public String createID() {
		int id = 0;
		String idString ="fail";
		boolean duplicate = false;
		Random rand = new Random();
		while (id == 0 || duplicate == true) {
			duplicate = false;
			id = rand.nextInt(90000) + 10000;
			idString = currentUser.getEmail()+id;
			for (ToDo toDo : wholeList) {
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
	
	public boolean existingUser(String email) {
		boolean existing = false; 
		for (User u : userlist) {
			if (u.getEmail().equals(email))
				existing = true;
		}
		return existing;
	}
	
	}

