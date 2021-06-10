package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

import commons.ToDo;
import commons.User;
import commons.ToDo.Importance;
import javafx.concurrent.Task;

public class Model {
	
	public Model() {
		
	}
	
	public boolean userFound(String email) {
		boolean found = false;
		for (User user : getUsers()) {
			if (user.getEmail().equals(email)) {
				found = true;
				break;
			}
		}
		return found;
	}
	
	public String getPassword(String email) {
		String password = "";
		for (User user : getUsers()) {
			if (user.getEmail().equals(email)) {
				password = user.getPassword();
				break;
			}
		}
		return password;
	}
	
	public ArrayList<User> getUsers (){
		ArrayList<User> userlist = new ArrayList<User>();
		userlist.add(new User("dan", "dan", "dan"));
		userlist.add(new User("ben", "ben", "ben"));
		return userlist;
	}
	
	public User getCurrentUser(String email) {
		User user = null;
		for (User item : getUsers()) {
			if(email.equals(item.getEmail())) {
				user = item;
				break;
			}
		}
		return user;
	}
	
	public ArrayList<ToDo> getTodos(){
		ArrayList<ToDo> wholeList = new ArrayList<ToDo>();
		wholeList.add(new ToDo("admin1", "Wäsche", "Tumbler alles", Importance.Low));
		wholeList.add(new ToDo("admin1", "Kkochen", "Spaghetti", Importance.High));
		wholeList.add(new ToDo("admin2", "Rasenmähen", "Mit alles ohne Zwiebel", Importance.Medium));
		return wholeList;
	}
	
	public ArrayList<ToDo> userTodos(String email){
		ArrayList<ToDo> userTodo = new ArrayList<ToDo>();
		for (ToDo todo : getTodos()) {
			if (todo.getId().contains(email))
				userTodo.add(todo);
		}
		return userTodo;
	}
	
	

}
