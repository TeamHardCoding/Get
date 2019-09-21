package ch.get.util;

import java.util.StringTokenizer;

public class CheckVaildAddress {

	private String msg = "";
	
	private CheckVaildAddress() {}
	
	private static class LazyHolder {
		public static final CheckVaildAddress INSTANCE = new CheckVaildAddress();
	}
	
	public boolean checkAddress(String temp) {
		StringTokenizer st = new StringTokenizer(temp, ".");
		temp = null;
		int value = 0;
		
		try {
			
				while (st.hasMoreElements()) {
					temp = st.nextToken();
					value = Integer.parseInt(temp);
					if((temp.length() < 1) || (temp.length() > 3)) {
						msg = "IP는 정수형 3자리수 입니다.";
					} else if((value < 0) || (value > 255)) {
						msg = "IP는 범위는 0 ~ 255 입니다.";
					} else {
						
					}
				}
		} catch (Exception e) {
			msg = "IP는 정수형태 여야 합니다.";
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
