package server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;

import commons.ToDo;
import commons.User;
import commons.ToDo.Importance;
import javafx.concurrent.Task;

public class Model {
	
	ArrayList<User> userlist = new ArrayList<User>();
	ArrayList<ToDo> todoList = new ArrayList<ToDo>();
	
	public Model() {
		//userlist.add(new User("dan", "dan", "dan"));
		//userlist.add(new User("ben", "ben", "ben"));
		ArrayList<Object> data = new ArrayList<Object>();
		try {
			FileInputStream fis = new FileInputStream("userdb.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			data = (ArrayList<Object>)ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
			return;
		}
		
		userlist = (ArrayList<User>) data.get(0);
		
		ArrayList<Object> tddata = new ArrayList<Object>();
		
		try {
			FileInputStream fis = new FileInputStream("tododb.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			tddata = (ArrayList<Object>)ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
			return;
		}
		
		todoList = (ArrayList<ToDo>) tddata.get(0);
	}
	
	public void userSettings(String email, String newPassword, String newName) {
		User user = getCurrentUser(email);
		if (!user.getName().equals(newName)&&newName!=null) {
			getCurrentUser(email).setName(newName);
		}
		if(!user.getPassword().equals(newPassword)&&newPassword!=null) {
			getCurrentUser(email).setPassword(newPassword);
		}
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
	
	public User createNewUser(String email, String password, String name) {
		User user = new User(name, email, password);
		return user;
	}
	
	public ToDo createTodo(String todoId, String title, String description, Importance importance, LocalDate dueDate) {
		ToDo toDo = new ToDo(todoId, title, description, importance);
		if(dueDate != null)
			toDo.setDueDate(dueDate);
		return toDo;
	}
	
	public ToDo findTodo(String todoid) {
		ToDo toDo = null;
		for (ToDo item : getTodos()) {
			if (item.getId().equals(todoid))
				toDo = item;
		}
		return toDo;
	}
	
	public void deleteTodo(String todoid) {
		ToDo toDo = findTodo(todoid);
		if (toDo != null) {
			for (ToDo item : getTodos()) {
				if (item.getId().equals(toDo.getId())) {
					getTodos().remove(item);
				}
			}
		}
	}
	
	public void addTodo(ToDo todo) {
		todoList.add(todo);
	}
	
	public void addUser(User user){
		userlist.add(user);
	}
	
	public ArrayList<User> getUsers (){
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
		return todoList;
	}
	
	public ArrayList<ToDo> userTodos(String email){
		ArrayList<ToDo> userTodo = new ArrayList<ToDo>();
		for (ToDo todo : getTodos()) {
			if (todo.getId().contains(email))
				userTodo.add(todo);
		}
		return userTodo;
	}
	
	public void uploadUsers() {
		try {
			ArrayList<Object> data = new ArrayList<Object>();
			data.add(userlist);
			FileOutputStream fos = new FileOutputStream("userdb.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(data);
			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();;
		}
	}
	
	public void uploadTodos() {
		try {
			ArrayList<Object> data = new ArrayList<Object>();
			data.add(todoList);
			FileOutputStream fos = new FileOutputStream("tododb.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(data);
			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();;
		}
	}
	

}
