package ch.get.model;

import java.util.StringTokenizer;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

public class Preset {

	private StringProperty name;
	private IntegerProperty time;
	private Color color;
	
	private StringBuffer sb;
	
	public Preset() {
		// TODO Auto-generated constructor stub
		this(null, null, null);
	}
	
	public Preset(String name, Integer time, Color color) {
		this.name = new SimpleStringProperty(name);
		this.time = new SimpleIntegerProperty(time);
		this.color = color;
	}

	public StringProperty nameProperty() {
		return name;
	}

	public IntegerProperty timeProperty() {
		return time;
	}

	public String getName() {
		return name.get();
	}

	public Integer getTime() {
		return time.get();
	}
	
	public void setName(StringProperty name) {
		this.name = name;
	}

	public void setTime(IntegerProperty time) {
		this.time = time;
	}

	public String getColor() {
		sb = new StringBuffer(color.toString());
		
		return sb.substring(2);
	}
}
