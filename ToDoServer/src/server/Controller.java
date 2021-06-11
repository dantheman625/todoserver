package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.stage.WindowEvent;

public class Controller {
	private Model model;
	private View view;
	private Integer port = 50002;
	private final Logger logger = Logger.getLogger("");
	
	final Task<Void> serverTask = new Task<Void>() {
	@Override
	protected Void call() throws Exception{
		ServerSocket listener = null;
		try {
			listener = new ServerSocket(port, 10, null);
			logger.info("Listening on port " + port);
			
			while (true) {
				Socket clientSocket = listener.accept();
				
				Client client = new Client(clientSocket, model);
				client.start();
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (listener != null) listener.close();
		}
		return null;
	}
	};
	
	public Controller(Model model, View view) {
		this.model= model;
		this.view= view;
		view.go.setOnAction(this::connect);
		view.getstage().setOnCloseRequest(this::shutdown);
	}

	public void connect(ActionEvent event) {
		new Thread(serverTask).start();
	}
	
	public void shutdown(WindowEvent event) {
		model.uploadUsers();
		model.uploadTodos();
		Platform.exit();
		System.exit(0);
	}
}
