package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

import commons.Message;
import commons.MessageType;
import commons.Message_Error;
import commons.Message_FindUser;
import commons.Message_Hello;
import commons.Message_LoginSuccess;
import commons.Message_Password;
import commons.Message_SendContent;
import commons.Message_UserNotFound;
import commons.User;

public class Client extends Thread{
	private final Logger logger = Logger.getLogger("");
	private Socket clientSocket;
	private Model model;
	
	public Client(Socket clientSocket, Model model) {
		this.clientSocket=clientSocket;
		this.model=model;
	}	
	
	@Override
	public void run() {
		logger.info("Request from client " + clientSocket.getInetAddress().toString() + " for server " + 
				clientSocket.getLocalAddress().toString());
		
		try {
			Message msgIn = Message.receive(clientSocket);
			Message msgOut = processMessage(msgIn);
			msgOut.send(clientSocket);
			logger.info("Answeret with " + msgOut.toString());
		} catch (Exception e) {
			logger.severe(e.toString());
		} finally {
			try { if(clientSocket != null)  clientSocket.close(); } catch (IOException e) {}
		}
	}
	
	private Message processMessage(Message msgIn) {
		logger.info("Message received from client: " + msgIn.toString());
		String clientName = msgIn.getClient();
		
		Message msgOut = null;
		switch (MessageType.getType(msgIn)) {
		case Hello:
			msgOut = new Message_Hello();
			break;
		case FindUser:
			Message_FindUser fu_msg = (Message_FindUser) msgIn;
			if(model.userFound(fu_msg.getEmail())) {
				msgOut = new Message_Password(model.getPassword(fu_msg.getEmail()));
			} else {
				msgOut = new Message_UserNotFound();
			}
			break;
		case LoginSuccess:
			Message_LoginSuccess lc_msg = (Message_LoginSuccess) msgIn;
			msgOut = new Message_SendContent(model.getCurrentUser(lc_msg.getEmail()),
					model.userTodos(lc_msg.getEmail()));
			break;
		default:
			msgOut = new Message_Error();
		}
		msgOut.setClient(clientName);
		return msgOut;
	}
	
	

}
