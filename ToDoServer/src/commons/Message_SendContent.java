package commons;

import java.io.Serializable;
import java.util.ArrayList;

public class Message_SendContent extends Message implements Serializable{
	private static final long serialVersionUID = 1;
	private User user;
	private ArrayList<ToDo> todoList;
	
	public Message_SendContent(User user, ArrayList<ToDo> todoList) {
		super();
		this.user=user;
		this.todoList=todoList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ArrayList<ToDo> getTodoList() {
		return todoList;
	}

	public void setTodoList(ArrayList<ToDo> todoList) {
		this.todoList = todoList;
	}
	
	
	
}
