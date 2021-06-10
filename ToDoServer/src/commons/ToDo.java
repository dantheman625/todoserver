package commons;

import java.io.Serializable;
import java.time.LocalDate;


public class ToDo implements Serializable{
	
	public enum Importance{Low, Medium, High};
	private static final long serialVersionUID = 1L;
	
	
	String title, description, id;
	LocalDate dueDate;
	Importance importance;
	
	public ToDo(String id, String title, String description, Importance importance) {
		this.title = title;
		this.description = description;
		this.importance = importance;
		this.id = id;
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

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Importance getImportance() {
		return importance;
	}

	public void setImportance(Importance importance) {
		this.importance = importance;
	}

	public String getId() {
		return id;
	}
	
	

}
