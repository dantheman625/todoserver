package server;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class View {
	protected Stage stage;
	private Model model;
	protected Button go = new Button("Go");
	
	public View(Stage stage, Model model) {
		this.stage = stage;
		this.model = model;
		HBox hBox = new HBox(go);
		Scene scene = new Scene(hBox);
		stage.setTitle("Server");
		stage.setScene(scene);
		
	}
	
	public void start() {
		stage.show();
	}
	
	public void stop() {
		stage.hide();
	}
	
	public Stage getstage() {
		return stage;
	}
}
