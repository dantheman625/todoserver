package commons;

import java.io.Serializable;

public class Message_Error extends Message implements Serializable{
	private static final long serialVersionUID = 1;
	private static final String ELEMENT_INFO = "info";
	
	private String info;
	
	public Message_Error() {
		super();
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	

}
