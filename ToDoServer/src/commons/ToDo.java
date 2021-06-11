package commons;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;


public class ToDo implements Serializable{
	 
	public enum Importance{
		Low("Low", 1),
		Medium("Medium", 2),
		High("High", 3);
		
		private String name;
		private int index;
		
		Importance(String name, int index) {
			this.name=name;
			this.index=index;
		}
		public String getName() {
			return name;
		}
		public int getIndex() {
			return index;
		}
	};
	
	private static final long serialVersionUID = 1L;
	
	
	private String title, description, id;
	private LocalDate dueDate;
	private Importance importance;
	private long timestamp; 
	
	public ToDo(String id, String title, String description, Importance importance) {
		this.title = title;
		this.description = description;
		this.importance = importance;
		this.id = id;
		timestamp = System.currentTimeMillis();
	}
	
	public static Comparator<ToDo> timeStampComparator = new Comparator<ToDo>() {
		public int compare(ToDo t1, ToDo t2) {
			Long t1t = t1.getTimestamp();
			Long t2t = t2.getTimestamp();
			
			return t1t.compareTo(t2t);
		}
	};
	
	public static Comparator<ToDo> titleComparator = new Comparator<ToDo>() {
		public int compare(ToDo t1, ToDo t2) {
			String t1n = t1.getTitle();
			t1n.toUpperCase();
			String t2n = t2.getTitle();
			t2n.toUpperCase();
			
			return t1n.compareTo(t2n);
		}
	};
	
	public static Comparator<ToDo> prioComparator = new Comparator<ToDo>() {
		public int compare(ToDo t1, ToDo t2) {
			Integer t1i = t1.getImportance().getIndex();
			Integer t2i = t2.getImportance().getIndex();
			
			return t2i.compareTo(t1i);
		}
	};

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

	public long getTimestamp() {
		return timestamp;
	}
	
	

}
