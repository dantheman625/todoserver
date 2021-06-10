package commons;

import java.io.Serializable;

public class Message_Password extends Message implements Serializable{
	private static final long serialVersionUID = 1;
	private String password;
	
	public Message_Password(String password) {
		super();
		this.password=password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	

}
