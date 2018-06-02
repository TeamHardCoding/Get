package ch.get.model;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Result {
	
	private SimpleStringProperty hourly;
	private SimpleStringProperty monthlyNow;
	private SimpleStringProperty monthly;
	private SimpleObjectProperty<LocalDate> startDate;
	private SimpleObjectProperty<LocalDate> purposeDate;
	
	//가공전 result 값
	private Integer pHourly;
	private Integer pTotalWorks;
	private Integer workTime;
	private long period;
	private LocalDate pStartDate;
	private LocalDate pPurposeDate;
	
	//util
	private Util util;
	
	public Result(String h, String tow, String wt, LocalDate strDate, LocalDate puDate) {
		// TODO Auto-generated constructor stub
		util = Util.getInstance();
		
		pHourly = Integer.parseInt(h);
		pTotalWorks = Integer.parseInt(tow);
		workTime = Integer.parseInt(wt);
		
		//period = puDate.toEpochDay() - strDate.toEpochDay();
		//System.out.println(period);
		
		hourly = new SimpleStringProperty(util.changeDatePattern(Integer.parseInt(h))); //util Integer
		monthly = new SimpleStringProperty(util.changeDatePattern((Integer.parseInt(h))*8));
		monthlyNow = new SimpleStringProperty(util.changeDatePattern((Integer.parseInt(h))*4));
		startDate = new SimpleObjectProperty<LocalDate>(strDate);
		purposeDate = new SimpleObjectProperty<LocalDate>(puDate);
	}

	public SimpleStringProperty getHourly() {
		return hourly;
	}

	public void setHourly(SimpleStringProperty hourly) {
		this.hourly = hourly;
	}

	public SimpleStringProperty getMonthlyNow() {
		return monthlyNow;
	}

	public void setMonthlyNow(SimpleStringProperty monthlyNow) {
		this.monthlyNow = monthlyNow;
	}

	public SimpleStringProperty getMonthly() {
		return monthly;
	}

	public void setMonthly(SimpleStringProperty monthly) {
		this.monthly = monthly;
	}

	public SimpleObjectProperty<LocalDate> getStartDate() {
		return startDate;
	}

	public void setStartDate(SimpleObjectProperty<LocalDate> startDate) {
		this.startDate = startDate;
	}

	public SimpleObjectProperty<LocalDate> getPurposeDate() {
		return purposeDate;
	}

	public void setPurposeDate(SimpleObjectProperty<LocalDate> purposeDate) {
		this.purposeDate = purposeDate;
	}
	
	// 프로퍼티 getter
	public StringProperty hourlyProperty() {
		return hourly;
	}
	
	public StringProperty monthlyProperty() {
		return monthly;
	}
	
	public StringProperty monthlyNowProperty() {
		return monthlyNow;
	}	
	
	public ObjectProperty<LocalDate> startDateProperty()
	{
		return startDate;
	}
	
	public ObjectProperty<LocalDate> purposeProperty()
	{
		return purposeDate;
	}
}