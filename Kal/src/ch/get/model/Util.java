package ch.get.model;

import java.text.DecimalFormat;

public class Util {
	
	private final static String DF_1000 = "#,##0"; //∆–≈œ
	
	private static Util util = null;
	private static DecimalFormat df = null;
	private String str;
	
	public String changeDatePattern(Integer temp)
	{
		df = getDfomat1000();
		str = df.format(temp);
				
		return str;
	}
	
	public void calResult()
	{
		
	}
	
	//singleton
	public static DecimalFormat getDfomat1000()
	{
		if(df == null)
		{
			synchronized(DecimalFormat.class) 
			{
				df = new DecimalFormat(DF_1000);
			}
		}
		
		return df;
	}
	
	public static Util getInstance()
	{
		if(util == null)
		{
			synchronized (Util.class) {
				util = new Util();
			}
		}
		
		return util;
	}
}
