package commons;

import java.io.Serializable;
import java.time.LocalDate;

import commons.ToDo.Importance;

public class Message_NewTodo extends Message implements Serializable{
	private static final long serialVersionUID = 1;
	private Importance importance;
	private String title, description, todoid ,email, dueDate;
	
	public Message_NewTodo(String email, String todoid, String title, String description, Importance importance) {
		super();
		this.email=email;
		this.title=title;
		this.description=description;
		this.importance=importance;
		this.todoid=todoid;
	}

	public Importance getImportance() {
		return importance;
	}

	public void setImportance(Importance importance) {
		this.importance = importance;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String gettodoId() {
		return todoid;
	}

	public void settodoId(String todoid) {
		this.todoid = todoid;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Message_NewTodo [importance=" + importance + ", title=" + title + ", description=" + description
				+ ", todoid=" + todoid + ", email=" + email + ", dueDate=" + dueDate + "]";
	}

	
	
	
}
