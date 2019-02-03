package lab.demo1.A;

import javafx.scene.control.Slider;

public class MyThread extends Thread {

	private boolean type;
	private volatile Slider slider;
	
	public static int count = 0;
	
	public MyThread(Slider slider, boolean type){
        this.slider = slider;
        this.type = type;
	}
	
	@Override
	public void run() {
		while(true) {
			synchronized(slider) {
				if (type == true) { 
					if (slider.getValue() < 90) {
						slider.increment();
						/*if (slider.getValue() == 90)
							count++;*/
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
						/*if (slider.getValue() == 10)
							count--;*/
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
