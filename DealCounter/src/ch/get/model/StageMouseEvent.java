package ch.get.model;

import ch.get.view.RootLController;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class StageMouseEvent implements EventHandler<MouseEvent>{
			
	public StageMouseEvent() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void handle(MouseEvent event) {
		// TODO Auto-generated method stub
		if(event.getClickCount() == 2)
		{
			RootLController.getInst().doubleClickMain();
		}
	}
}