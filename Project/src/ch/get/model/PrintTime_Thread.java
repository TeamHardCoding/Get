package ch.get.model;

import java.time.LocalDateTime;

import ch.get.view.RootController;
import javafx.scene.control.TextArea;

public class PrintTime_Thread implements Runnable {

	private String name;
	private TextArea textArea;
	private LocalDateTime ldt;
	
	public PrintTime_Thread(String thread_Name) {
		name = thread_Name;
		textArea = RootController.inst.getTextBox();
	}
	
	@Override
	public void run() {
		
		try {
			while(!Thread.currentThread().isInterrupted()) {
					ldt = LocalDateTime.now();
					textArea.appendText(ldt.toString()+"\n");
					Thread.currentThread().sleep(1000);
			}
		} catch (Exception e) {
			
		} finally {
			textArea.appendText("\n"+name+"인터럽트\n");
		}
	}

}
