package commons;

import java.io.Serializable;

public class Message_NewPassword extends Message implements Serializable{
	private static final long serialVersionUID = 1;
	private String newPassword;
	private String email;
	private String newName;
	
	public Message_NewPassword(String email, String newPassword, String newName) {
		super();
		this.newPassword=newPassword;
		this.email=email;
		this.newName=newName;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	@Override
	public String toString() {
		return "Message_NewPassword [newPassword=" + newPassword + ", email=" + email + "]";
	}
	
}
