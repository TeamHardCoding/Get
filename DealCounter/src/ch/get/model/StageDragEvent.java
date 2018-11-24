package ch.get.model;

import java.awt.Toolkit;

import javafx.event.EventHandler;import javafx.scene.input.MouseEvent;

public class StageDragEvent implements EventHandler<MouseEvent>{

	private Toolkit kit;
	
	public StageDragEvent() {
		// TODO Auto-generated constructor stub
		kit = Toolkit.getDefaultToolkit();
	}
	
	@Override
	public void handle(MouseEvent event) {
		// TODO Auto-generated method stub
		if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			System.out.println(event.getScreenX()+" / "+event.getSceneY());
		}
	}
}
