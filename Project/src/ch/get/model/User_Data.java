package ch.get.model;

import java.io.Serializable;

public class User_Data implements Serializable{
	
	private int number; 
	private String author;
	private String msg;
	
	public User_Data(int temp, String au_tmp, String msg_tmp) {
		// TODO Auto-generated constructor stub
		number = temp;
		author = au_tmp;
		msg = msg_tmp;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
