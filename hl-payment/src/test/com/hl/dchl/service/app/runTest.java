package test.com.hl.dchl.service.app;

import java.io.InputStream;


public class runTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	 InputStream in = null;
	 
		try {

			Process pro = Runtime.getRuntime().exec(new String[]{"sh"});
			
			System.out.println("123");

		} catch (Exception e) {

		}
	}

}
