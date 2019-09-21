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
						msg = "IP�� ������ 3�ڸ��� �Դϴ�.";
					} else if((value < 0) || (value > 255)) {
						msg = "IP�� ������ 0 ~ 255 �Դϴ�.";
					} else {
						
					}
				}
		} catch (Exception e) {
			msg = "IP�� �������� ���� �մϴ�.";
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
