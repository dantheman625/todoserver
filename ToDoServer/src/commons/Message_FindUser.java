package commons;

import java.io.Serializable;

public class Message_FindUser extends Message implements Serializable{
	private static final long serialVersionUID = 1;
	private String email;
	
	public Message_FindUser(String email) {
		super();
		this.email=email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return super.toString() + ", user = " + this.email;
	}

}
