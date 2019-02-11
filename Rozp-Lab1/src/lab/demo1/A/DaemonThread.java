package lab.demo1.A;

import javafx.scene.control.Slider;

public class DaemonThread extends Thread {

	private boolean type;
	private volatile Slider slider;
	
	public DaemonThread(Slider slider, boolean type){
        this.slider = slider;
        this.type = type;
        setDaemon(true);
	}
	
	@Override
	public void run() {
		while(true) {
			synchronized(slider) {
				if (type == true) { 
					if (slider.getValue() < 90) {
						slider.increment();
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				else {
					if (slider.getValue() > 10) {
						slider.decrement();
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		
	}

}
