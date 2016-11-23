package com.hl.common.util;


public class Toolkit {
	
	
	public static int parseIntFromStr(String intStr,int defaultValue) {
		 try {
			 return Integer.parseInt(intStr);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	public static long parseLongFromStr(String str,long defaultValue) {
		 try {
			 return Long.parseLong(str);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	public static double parseDoubleFromStr(String str,double defaultValue) {
		 try {
			 return Double.parseDouble(str);
		} catch (Exception e) {
			return defaultValue;
		}
	}
}
