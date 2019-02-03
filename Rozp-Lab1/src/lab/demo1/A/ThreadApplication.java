package lab.demo1.A;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ThreadApplication extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		VBox vBox = new VBox();
		Button start = new Button("Пуск");
		Slider slider = new Slider(10, 90, 45);
		Button fThreadMinus = new Button("-");
		Button fThreadPlus = new Button("+");
		Button sThreadMinus = new Button("-");
		Button sThreadPlus = new Button("+");
		ProgressBar fPriority = new ProgressBar(0.5);
		ProgressBar sPriority = new ProgressBar(0.5);
		
		Thread fThread = new MyThread(slider, false);
		Thread sThread = new MyThread(slider, true);
		
		fThread.setPriority(5);
		sThread.setPriority(5);
		
		/*
		 * the easiest is to simply create your Threads as daemons, 
		 * which means that when your main program 
		 * ends all daemon threads will terminate too.
		 */
		
		fThread.setDaemon(true);
		sThread.setDaemon(true);
		
		slider.setShowTickLabels(true);
		slider.setBlockIncrement(1);
		
		start.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				fThread.start();
				sThread.start();
				start.setDisable(true);
			}
		});
		
		fThreadMinus.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				double progress;
				if ((progress = fPriority.getProgress()) * 10 > 1) {
					fPriority.setProgress((progress * 10 - 1) / 10);
					fThread.setPriority((int)(progress * 10 - 1));
				}
				
			}
		});
		
		sThreadMinus.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				double progress;
				if ((progress = sPriority.getProgress()) * 10 > 1) {
					sPriority.setProgress((progress * 10 - 1) / 10);
					sThread.setPriority((int)(progress * 10 - 1));
				}
				
			}
		});
		
		fThreadPlus.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				double progress;
				if ((progress = fPriority.getProgress()) * 10 < 10) {
					fPriority.setProgress(((progress * 10 + 1) / 10));
					fThread.setPriority((int) (progress * 10 + 1));
				}
				
			}
		});
		
		sThreadPlus.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				double progress;
				if ((progress = sPriority.getProgress()) * 10 < 10) {
					sPriority.setProgress(((progress * 10 + 1) / 10));
					sThread.setPriority((int) (progress * 10 + 1));
				}
				
			}
		});
		
		vBox.setSpacing(10);
		vBox.getChildren().add(new HBox(10, start, slider));
		vBox.getChildren().add(new HBox(10, new Label("Thread's priority m(-):"), fThreadMinus, fPriority, fThreadPlus));
		vBox.getChildren().add(new HBox(10, new Label("Thread's priority p(+):"), sThreadMinus, sPriority, sThreadPlus));
		
		Scene scene = new Scene(vBox);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
