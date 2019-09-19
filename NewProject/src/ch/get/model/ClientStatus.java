package ch.get.model;

public enum ClientStatus {
	
	PLUS(1), MINUS(-1);
	
	private int state = 0;
	
	private ClientStatus(int temp) {		
		state = temp;
	}
	
	public int getState() {
		return state;
	}
}
