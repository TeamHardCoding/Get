package ch.get.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/* ����ó ���� ��Ŭ���� */
@XmlRootElement(name = "Logs")
public class LogListWrapper {
	
	private String Log;

	@XmlElement(name = "Log")
	public String getLog() {
		return Log;
	}

	public void setLog(String log) {
		Log = log;
	}
}
