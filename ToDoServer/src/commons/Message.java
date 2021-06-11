package commons;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Message implements Serializable{
	private static final long serialVersionUID = 1;
	
	private long id;
	private long timestamp;
	private String client;
	
	private static long messageID = 0;
	
	private static long nextMessageID() {
		return messageID++;
	}
	
	protected Message() {
		this.id = -1;
	}
	 
	public void send(Socket s) {
		if (this.id ==-1) this.id = nextMessageID();
		
		this.timestamp = System.currentTimeMillis();
		
		try {
			ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			out.writeObject(this);
			out.flush();
			s.shutdownOutput();
		} catch (Exception e) {
			
		}
	}
	
	public static Message receive (Socket socket) throws Exception {
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		Message msg = (Message) in.readObject();
		return msg;
	}
	
	@Override
	public String toString() {
		return "Message type " + this.getClass().getSimpleName() + "; ID= " + 
	this.id + ", Client= " + this.client + "'";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}
	
	

}
