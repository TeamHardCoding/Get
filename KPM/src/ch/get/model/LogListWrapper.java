package ch.get.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/* 연락처 저장 모델클래스 */
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
