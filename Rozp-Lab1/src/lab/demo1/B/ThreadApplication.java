package lab.demo1.B;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ThreadApplication extends Application {

	private static int semaphore = 0;
	private Thread fThread;
	private Thread sThread;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		VBox vBox = new VBox();
		Button fStart = new Button("Пуск 1");
        Button sStart = new Button("Пуск 2");
        Button fStop = new Button("Стоп 1");
        Button sStop = new Button("Стоп 2");
		Slider slider = new Slider(10, 90, 45);
		slider.setDisable(true);
		
		fStop.setDisable(true);
		sStop.setDisable(true);
		
		slider.setShowTickLabels(true);
		slider.setBlockIncrement(5);
		
		fStart.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				try {
					if (isFull()) {
						System.out.println("Boom!!");
						throw new SemaphoreActiveException("Semaphore is active");
					}
					acquire();
					System.out.println("Acquired!");
					fThread = new DaemonThread(slider, false);
					fThread.setPriority(Thread.MIN_PRIORITY);
					fStart.setDisable(true);
					sStart.setDisable(false);
					fStop.setDisable(false);
					sStop.setDisable(true);
				} catch (SemaphoreActiveException e) {
					System.out.println(e);
				}
			}
		});
		
		sStart.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				try {
					if (isFull())
						throw new SemaphoreActiveException("Semaphore is active");
					acquire();
					System.out.println("Acquired!");
					sThread = new DaemonThread(slider, true);
					sThread.setPriority(Thread.MAX_PRIORITY);
					sThread.start();
					fStart.setDisable(false);
					sStart.setDisable(true);
					fStop.setDisable(true);
					sStop.setDisable(false);
				} catch (SemaphoreActiveException e) {
					System.out.println(e);
				}
			}
		});
		
		fStop.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				fThread.interrupt();
				release();
				fStart.setDisable(false);
				sStart.setDisable(false);
				fStop.setDisable(true);
				sStop.setDisable(true);
			}
		});
		
		sStop.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				sThread.interrupt();
				release();
				fStart.setDisable(false);
				sStart.setDisable(false);
				fStop.setDisable(true);
				sStop.setDisable(true);
			}
		});
		
		
		vBox.setSpacing(10);
		vBox.getChildren().add(new HBox(10, fStart, sStart, slider));
		vBox.getChildren().add(new HBox(10, fStop, sStop));
		
		Scene scene = new Scene(vBox);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void acquire() {
		semaphore = 1;
	}
	
	public static void release() {
		semaphore = 0;
	}
	
	public static boolean isFull() {
		return semaphore == 1;
	}

}


class SemaphoreActiveException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg;
	
	SemaphoreActiveException(String message) {
		msg = message;
	}
	
	public String toString() { 
		return ("MyException Occurred: " + msg) ;
	}
}