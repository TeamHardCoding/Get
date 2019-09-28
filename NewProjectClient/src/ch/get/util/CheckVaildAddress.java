package ch.get.util;

import java.util.StringTokenizer;

public class CheckVaildAddress {

	private String msg;
	
	private CheckVaildAddress() {}
	
	private static class LazyHolder {
		public static final CheckVaildAddress INSTANCE = new CheckVaildAddress();
	}
	
	public boolean checkAddress(String temp, String port) {
		StringTokenizer st = new StringTokenizer(temp, ".");
		int value = 0;
		msg = "";
		
		try {
			if((temp.length() > 0) && (st.countTokens() == 4)) {
				while (st.hasMoreElements()) {
					temp = st.nextToken();
					value = Integer.parseInt(temp);
					if((temp.length() < 1) || (temp.length() > 3)) {
						msg += "IP는 정수형 3자리수 입니다.";
					} else if((value < 0) || (value > 255)) {
						msg += "IP는 범위는 0 ~ 255 입니다.";
					} else {
						
					}
				}
			} else {
				msg += "IP를 입력 하세요.";
			}
		} catch (Exception e) {
			msg += "IP는 정수형태 여야 합니다.";
		}
		
		try {
			if(port.length() > 0) { Integer.parseInt(port); }
		} catch (Exception e) {
			msg += "포트는 오로지 숫자 여야 합니다.";
		}
		
		if(msg.length() > 0) {
			return false;
		} else {
			return true; 
		}
	}
	
	public String getMsg() {
		return msg;
	}
	
	public static CheckVaildAddress getInstance() {
		return LazyHolder.INSTANCE; 
	}
}
