package server;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
	private View view;
	private Controller controller;
	private Model model;
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		model = new Model();
		view = new View(primaryStage, model);
		controller = new Controller(model, view);
		view.start();
	}
	
	@Override
	public void stop() {
		if(view!=null)
			view.stop();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
