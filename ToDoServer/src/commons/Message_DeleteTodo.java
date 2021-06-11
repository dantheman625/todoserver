package commons;

import java.io.Serializable;

public class Message_DeleteTodo extends Message implements Serializable{
	private static final long serialVersionUID = 1;
	private String email, todoid;
	
	public Message_DeleteTodo(String email, String todoid) {
		super();
		this.email=email;
		this.todoid=todoid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTodoid() {
		return todoid;
	}

	public void setTodoid(String todoid) {
		this.todoid = todoid;
	}

	@Override
	public String toString() {
		return "Message_DeleteTodo [email=" + email + ", todoid=" + todoid + "]";
	}

}
