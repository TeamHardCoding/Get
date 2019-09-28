package ch.get.model;

import java.io.Serializable;

public class Member implements Serializable {

	private static final long serialVersionUID = 5217905183446137055L; //����� ���� UID �ڵ�
	
	private String name;
	private String passWord;
	private int op_Code; //ADMIN ����
	
	public Member(String name, String passWord, int op_Code) {
		
		this.name = name;
		this.passWord = passWord;
		this.op_Code = op_Code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
}
