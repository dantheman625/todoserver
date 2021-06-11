package commons;

import java.io.Serializable;

public class Message_NewUser extends Message implements Serializable{
	private static final long serialVersionUID = 1;
	private String email;
	private String password;
	private String name;
	
	public Message_NewUser(String email, String password, String name) {
		super();
		this.email=email;
		this.name=name;
		this.password=password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Message_NewUser [email=" + email + ", password=" + password + ", name=" + name + "]";
	}
	
}
